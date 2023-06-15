package com.w.saffron.rpc.server.v100;

import com.w.saffron.common.PageResult;
import com.w.saffron.common.R;
import com.w.saffron.rpc.FeignInterceptor;
import com.w.saffron.video.dto.VideoDto;
import com.w.saffron.video.dto.VideoSearchDto;
import com.w.saffron.video.vo.VideoVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author w
 * @since 2023/6/13
 */
@FeignClient(
        name = "saffron-server",
        contextId = "video",
        configuration = FeignInterceptor.class
)
public interface VideoInterface {

    @GetMapping("/saffron-server/video/find-by-id")
    R<VideoVo> findById(@RequestParam("id") Long id);

    @PostMapping("/saffron-server/video/search")
    R<PageResult<VideoVo>> search(@RequestBody VideoSearchDto videoDto);
    @PutMapping("/saffron-server/video")
    R<?> addVideo(@RequestBody VideoDto video);

    @PatchMapping("/saffron-server/video")
    R<?> updateVideo(@RequestBody VideoDto video);

}
