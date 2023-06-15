package com.w.saffron.video.controller;


import com.w.saffron.common.R;
import com.w.saffron.common.constant.Insert;
import com.w.saffron.common.constant.Update;
import com.w.saffron.video.domain.Video;
import com.w.saffron.video.dto.VideoDto;
import com.w.saffron.video.dto.VideoSearchDto;
import com.w.saffron.video.service.VideoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author w
 * @since 2023/5/20
 */
@RestController
@RequestMapping("video")
@Valid
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
    public R<?> put(@RequestBody @Validated(Insert.class) VideoDto req){
        videoService.saveOrUpdate(req);
        return R.ok();
    }

    @PatchMapping
    public R<?> patch(@RequestBody @Validated(Update.class) VideoDto req){
        videoService.saveOrUpdate(req);
        return R.ok();
    }

    @PostMapping("search")
    public R<?> search(@RequestBody VideoSearchDto videoDto){
        return R.ok(videoService.search(videoDto));
    }

    @DeleteMapping
    public R<?> deleteByIds(@RequestBody List<Long> ids){
        videoService.deleteByIds(ids);
        return R.ok();
    }


}
