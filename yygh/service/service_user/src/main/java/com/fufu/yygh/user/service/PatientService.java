package com.fufu.yygh.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fufu.yygh.model.user.Patient;

import java.util.List;

public interface PatientService extends IService<Patient> {
    List<Patient> findAllUserId(Long userId);

    Patient getPatientId(Long id);
}
