package com.w.saffron.system.controller;

import cn.hutool.core.util.StrUtil;
import com.w.saffron.minio.MinioManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author w
 * @since 2023/3/8
 */
@RestController
@RequestMapping("file")
public class FileController {

    @PostMapping("upload")
    public R<?> uploadFile(MultipartFile file, @RequestHeader(value = "minio-prefix",required = false) String prefix){
        if (StrUtil.isNotEmpty(prefix)){
            return R.ok(MinioManager.uploadFile(file,prefix));
        }
        return R.ok(MinioManager.uploadFile(file,prefix));
    }

    @GetMapping("list-files")
    public R<?> listFiles(String prefix){
        return R.ok(MinioManager.listFiles(prefix));
    }

    @GetMapping("get-preview-url")
    public R<?> getInfo(String fullName){
        return R.ok(MinioManager.getPreview(fullName));
    }

    @GetMapping(value = "/preview/**")
    public void preview(HttpServletRequest request,HttpServletResponse response){
        String requestUrl = request.getRequestURI();
        String fullName = requestUrl.split("/preview")[1];
        fullName = URLDecoder.decode(fullName, StandardCharsets.UTF_8);
        MinioManager.preview(fullName,response);
    }


}
