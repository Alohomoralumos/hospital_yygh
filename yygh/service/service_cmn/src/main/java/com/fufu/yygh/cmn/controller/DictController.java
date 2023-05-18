package com.fufu.yygh.cmn.controller;

import com.fufu.yygh.cmn.service.DictService;
import com.fufu.yygh.common.result.Result;
import com.fufu.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api(value="数据字典接口")
@RestController
@RequestMapping(value = "/admin/cmn/dict")
@CrossOrigin
public class DictController {

    @Autowired
    private DictService dictService;
    //导入数据字典
    @PostMapping("importData")
    public Result importDict(MultipartFile file) {
        dictService.importDictData(file);
        return Result.ok();
    }
    //导出数据字典的接口
    @GetMapping("exportData")
    public Result exportDict(HttpServletResponse response) throws IOException {
        dictService.exportDictData(response);
        return Result.ok();

    }

    //根据dictCode获取下级节点
    @ApiOperation(value = "根据dictCode获取下级节点")
    @GetMapping("findBYDictCode/{dictCode}")
    public Result findByDictCode(@PathVariable String dictCode) {
        List<Dict> list = dictService.findByDictCode(dictCode);
        return Result.ok(list);
    }

    //查询id下面的子数据
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable long id) {
        List<Dict> list = dictService.findChildData(id);
        return Result.ok(list);
    }

    //根据DictCode和value查询
    @GetMapping("getName/{dictCode}/{value}")
    public String getName(@PathVariable String dictCode,
                          @PathVariable String value) {
        String dictName = dictService.getDictName(dictCode,value);
        return dictName;
    }

    //根据value查询
    @GetMapping("getName/{value}")
    public String getName(@PathVariable String value) {
        String dictName = dictService.getDictName("",value);
        return dictName;
    }

}
