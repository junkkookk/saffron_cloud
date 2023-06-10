package com.w.saffron.video.interfaces;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

/**
 * @author w
 * @since 2023/6/9
 */
@HttpExchange
public interface ZmqApi {

    @GetExchange("/home")
    String home(@RequestParam(name = "u") String userId,@RequestParam Integer page);

    @GetExchange("/get")
    String get(@RequestParam(name = "u") String u,@RequestParam(name = "v") String videoUuid);

}
