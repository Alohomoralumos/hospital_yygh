package com.fufu.easyexcel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestWrite {
    public static void main(String[] args) {

        List<UserData> list = new ArrayList<>();
        for(int i = 0; i < 10; i++)
        {
            UserData data = new UserData();
            data.setUid(i);
            data.setUsername("lucy" + i);
        }
        //设置excel文件路径和文件名称
        String filename="E:\\excel_fufu\\01.xlsx";

        EasyExcel.write(filename, UserData.class).sheet("用户信息")
                .doWrite(list);
    }
}
