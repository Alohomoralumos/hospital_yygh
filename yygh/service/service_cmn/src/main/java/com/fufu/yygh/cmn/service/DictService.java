package com.fufu.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fufu.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface DictService extends IService<Dict> {

    List<Dict> findChildData(long id);

    void exportDictData(HttpServletResponse response) throws IOException;

    String getDictName(String s, String value);

    List<Dict> findByDictCode(String dictCode);

    void importDictData(MultipartFile file);
}
