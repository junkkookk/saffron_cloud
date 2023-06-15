package com.w.saffron.system.controller;

import cn.hutool.core.io.file.FileNameUtil;
import cn.hutool.core.util.StrUtil;
import com.w.saffron.common.R;
import com.w.saffron.exception.OprException;
import com.w.saffron.minio.MinioManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
        try {
            response.setContentType(MediaTypeFactory.getMediaType(fullName).orElse(MediaType.APPLICATION_OCTET_STREAM).toString());
            response.setHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(FileNameUtil.getName(fullName), StandardCharsets.UTF_8));
            MinioManager.preview(fullName,response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            throw new OprException(e.getMessage());
        }
    }


}
