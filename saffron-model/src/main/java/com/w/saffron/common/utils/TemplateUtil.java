package com.w.saffron.common.utils;

import cn.hutool.extra.spring.SpringUtil;
import lombok.experimental.UtilityClass;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * @author w
 * @since 2023/3/30
 */
@UtilityClass
public class TemplateUtil {
    private final TemplateEngine templateEngine = SpringUtil.getBean(TemplateEngine.class);

    public String getMailCodeHtml(String code,String title){
        Context context = new Context();
        context.setVariable("code", code);
        context.setVariable("title", title);
        return templateEngine.process("email-code", context);
    }




}
