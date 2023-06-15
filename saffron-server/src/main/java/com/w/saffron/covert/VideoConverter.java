package com.w.saffron.covert;

import com.w.saffron.video.domain.Video;
import com.w.saffron.video.dto.VideoDto;
import com.w.saffron.video.dto.VideoSearchDto;
import com.w.saffron.video.vo.VideoVo;

/**
 * @author w
 * @since 2023/6/15
 */
public class VideoConverter {

    public static Video toVideo(VideoDto videoDto){
        if (videoDto == null) {
            return null;
        }
        Video video = new Video();
        video.setUuid(videoDto.getUuid());
        video.setTitle(videoDto.getTitle());
        video.setAuthor(videoDto.getAuthor());
        video.setPlayUrl(videoDto.getPlayUrl());
        video.setCover(videoDto.getCover());
        video.setVideoType(videoDto.getVideoType());
        video.setSource(videoDto.getSource());
        video.setCategory(videoDto.getCategory());
        video.setStatus(videoDto.getStatus());
        video.setId(videoDto.getId());
        return video;
    };

    public static Video toVideo(VideoSearchDto videoDto){

        if (videoDto == null) {
            return null;
        }
        Video video = new Video();
        video.setTitle(videoDto.getTitle());
        video.setAuthor(videoDto.getAuthor());
        video.setStatus(videoDto.getStatus());
        return video;
    };

    public static VideoVo toVideoVo(Video video){

        if (video == null) {
            return null;
        }
        VideoVo videoVo = new VideoVo();
        videoVo.setId(video.getId());
        videoVo.setUuid(video.getUuid());
        videoVo.setTitle(video.getTitle());
        videoVo.setAuthor(video.getAuthor());
        videoVo.setPlayUrl(video.getPlayUrl());
        videoVo.setCover(video.getCover());
        videoVo.setVideoType(video.getVideoType().getDesc());
        videoVo.setSource(video.getSource().getDesc());
        videoVo.setCategory(video.getCategory().getDesc());
        videoVo.setStatus(video.getStatus().getDesc());
        videoVo.setCreateTime(video.getCreateTime());
        return videoVo;
    };


}
