package com.w.saffron.exception;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.MessageFormat;

@Controller
public class ErrorHandleController implements ErrorController {

    private final static String ERROR_PATH = "/error";

    @RequestMapping(ERROR_PATH)
    public String errorHandle(HttpServletRequest request,HttpServletResponse response){
        ResultCode resultCode = ResultCode.of(response.getStatus());
//        if (resultCode==ResultCode.NOT_FOUND){
//            return MessageFormat.format("{0}.html", StrUtil.toString(response.getStatus()));
//        }
        throw new OprException(resultCode);
    }


}
