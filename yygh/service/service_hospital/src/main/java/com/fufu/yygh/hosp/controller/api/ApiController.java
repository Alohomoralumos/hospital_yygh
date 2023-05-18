package com.fufu.yygh.hosp.controller.api;


import com.fufu.yygh.common.exception.YyghException;
import com.fufu.yygh.common.helper.HttpRequestHelper;
import com.fufu.yygh.common.result.Result;
import com.fufu.yygh.common.result.ResultCodeEnum;
import com.fufu.yygh.common.utils.MD5;
import com.fufu.yygh.hosp.service.DepartmentService;
import com.fufu.yygh.hosp.service.HospitalService;
import com.fufu.yygh.hosp.service.HospitalSetService;
import com.fufu.yygh.hosp.service.ScheduleService;
import com.fufu.yygh.model.hosp.Department;
import com.fufu.yygh.model.hosp.Hospital;
import com.fufu.yygh.model.hosp.Schedule;
import com.fufu.yygh.vo.hosp.DepartmentQueryVo;
import com.fufu.yygh.vo.hosp.ScheduleQueryVo;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    //mogo
    private HospitalService hospitalService;

    @Autowired
    //mysql
    private HospitalSetService hospitalSetService;

    @Autowired
    //mogo
    private DepartmentService departmentService;
    @Autowired
    private ScheduleService scheduleService;
    //删除排班接口
    @PostMapping("schedule/remove")
    public Result remove(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        String hoscode = (String) paramMap.get("hoscode");
        String hosScheduleId = (String) paramMap.get("hosScheduleId");

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }
    //查询排班接口
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //医院编号
        String hoscode = (String)paramMap.get("hoscode");
        //科室编号
        String depcode = (String)paramMap.get("depcode");

        int page = StringUtils.isEmptyOrWhitespaceOnly((String) paramMap.get("page")) ? 1: Integer.parseInt((String) paramMap.get("page"));
        int limit = StringUtils.isEmptyOrWhitespaceOnly((String) paramMap.get("limit")) ? 1: Integer.parseInt((String) paramMap.get("limit"));

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);

        Page<Schedule> pageModel = scheduleService.findPageSchedule(page,limit,scheduleQueryVo);
        return Result.ok(pageModel);
    }
    //上传排班接口
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {
        Map<String,String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        scheduleService.save(paramMap);
        return Result.ok();
    }
    //查询科室接口
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        String hoscode = (String) paramMap.get("hoscode");
        //当前页和每页记录数
        int page = StringUtils.isEmptyOrWhitespaceOnly((String) paramMap.get("page"))? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmptyOrWhitespaceOnly((String) paramMap.get("limit"))? 1 : Integer.parseInt((String)paramMap.get("limit"));
        //签名校验
        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);

        //调用service方法
        Page<Department> pageModel = departmentService.findPageDepartment(page,limit,departmentQueryVo);
        return Result.ok(pageModel);
    }
    //上传科室接口
    @PostMapping("saveDeparment")
    public Result saveDepartment(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //1.获取医院系统传递过来的签名
        String hospSign = (String) paramMap.get("sign");
        //2.根据传递过来的医院编号，查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");

        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMd5 = MD5.encrypt(signKey);

        if(!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        departmentService.save(paramMap);
        return Result.ok();
    }
    //删除科室接口
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //医院编号和科室编号
        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String) paramMap.get("depcode");
        departmentService.remove(hoscode,depcode);
        return Result.ok();
    }
    //查询医院的接口
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //1.获取医院系统传递过来的签名
        String hospSign = (String) paramMap.get("sign");
        //2.根据传递过来的医院编号，查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");

        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMd5 = MD5.encrypt(signKey);

        if(!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        Hospital hospital = hospitalService.getByHoscode(hoscode);
        return Result.ok(hospital);
    }
    //上传医院接口
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //1.获取医院系统传递过来的签名
        String hospSign = (String) paramMap.get("sign");
        //2.根据传递过来的医院编号，查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");

        String signKey = hospitalSetService.getSignKey(hoscode);
        String signKeyMd5 = MD5.encrypt(signKey);

        if(!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        String logoData = (String) paramMap.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        paramMap.put("logoData", logoData);

        hospitalService.save(paramMap);
        return Result.ok();
    }
}
