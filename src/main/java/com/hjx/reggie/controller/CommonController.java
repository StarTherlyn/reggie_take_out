package com.hjx.reggie.controller;

import com.hjx.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/common")
@Slf4j
public class CommonController {
    @Value("${reggie.path}")
    private String basePath;

    @PostMapping("/upload")
    //MultipartFile文件名必须与浏览器提供的Content-Disposition: form-data; name="file";中的name一致
    public R<String> update(MultipartFile file) throws IOException {
        log.info("文件上传");
        String oriname = file.getOriginalFilename();
        String suffix = oriname.substring(oriname.lastIndexOf("."));
        String filename = UUID.randomUUID().toString()+suffix;

        File dir = new File(basePath);
        if(!dir.exists()){
            dir.mkdir();
        }

        file.transferTo(new File(basePath+filename));
        return R.success(filename);
    }

    /**
     * 文件下载
     * @param name
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("image/jpeg");

        byte[] bytes = new byte[1024];
        int len=0;
        while ((len=fileInputStream.read(bytes))!=-1){
            outputStream.write(bytes,0,len);
            outputStream.flush();
        }

        fileInputStream.close();
        outputStream.close();
    }
}
