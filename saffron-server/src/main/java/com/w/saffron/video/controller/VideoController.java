package com.w.saffron.video.controller;


import com.w.saffron.common.R;
import com.w.saffron.common.constant.Insert;
import com.w.saffron.common.constant.Update;
import com.w.saffron.video.bean.VideoRequest;
import com.w.saffron.video.domain.Video;
import com.w.saffron.video.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author w
 * @since 2023/5/20
 */
@RestController
@RequestMapping("video")
public class VideoController {

    private final VideoService videoService;

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }

    @GetMapping("find-by-id")
    public R<Video> findById(Long id){
        return R.ok(videoService.findById(id));
    }

    @PutMapping
    public R<?> put(@RequestBody @Validated(Insert.class) VideoRequest.SaveOrUpdate req){
        videoService.saveOrUpdate(req);
        return R.ok();
    }

    @PatchMapping
    public R<?> patch(@RequestBody @Validated(Update.class) VideoRequest.SaveOrUpdate req){
        videoService.saveOrUpdate(req);
        return R.ok();
    }




}
