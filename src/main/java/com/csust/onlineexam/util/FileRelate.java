package com.csust.onlineexam.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ：Lenovo
 * @date ：Created in 2020/4/3 18:52
 * @description : 一些文件相关的公共方法
 * @modified By：
 */
public class FileRelate {

    public static ResponseEntity<byte[]> getTemplate(String templatePath, String template) throws IOException {
        String fileName = templatePath + template;
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println(file.createNewFile());
        }
        byte[] body;
        InputStream is = new FileInputStream(file);
        body = new byte[is.available()];
        System.out.println(is.read(body));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attchement;filename=" + file.getName());
        HttpStatus statusCode = HttpStatus.OK;
        return new ResponseEntity<>(body, headers, statusCode);
    }
}
