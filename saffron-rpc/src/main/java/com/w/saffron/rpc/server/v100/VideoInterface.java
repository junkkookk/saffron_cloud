package com.w.saffron.rpc.server.v100;

import com.w.saffron.common.PageResult;
import com.w.saffron.common.R;
import com.w.saffron.video.bean.VideoRequest;
import com.w.saffron.video.domain.Video;
import com.w.saffron.video.dto.VideoDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author w
 * @since 2023/6/13
 */
@FeignClient(name = "saffron-server",contextId = "video")
public interface VideoInterface {

    @GetMapping("/saffron-server/video/find-by-id")
    R<Video> findById(@RequestParam("id") Long id);

    @PostMapping("/saffron-server/video/search")
    R<PageResult<Video>> search(@RequestBody VideoDto videoDto);
    @PutMapping("/saffron-server/video")
    R<?> addVideo(@RequestBody VideoRequest.SaveOrUpdate video);

    @PatchMapping("/saffron-server/video")
    R<?> updateVideo(@RequestBody VideoRequest.SaveOrUpdate video);

}
