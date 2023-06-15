package com.w.saffron.rpc.server.v100;


import com.w.saffron.common.R;
import com.w.saffron.rpc.FeignInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author w
 * @since 2023/6/13
 */
@FeignClient(
        name = "saffron-server",
        contextId = "user",
        configuration = FeignInterceptor.class
)
public interface UserInterface {

    @GetMapping("/saffron-server/user/find-by-id")
    R<?> findById(@RequestParam("userId")Long id);

}
