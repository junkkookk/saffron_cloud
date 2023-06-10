package com.w.saffron.video.service;


import com.w.saffron.common.ResultCode;
import com.w.saffron.common.exception.OprException;
import com.w.saffron.common.utils.BeanUtil;
import com.w.saffron.video.bean.VideoRequest;
import com.w.saffron.video.constant.Status;
import com.w.saffron.video.dao.VideoDao;
import com.w.saffron.video.domain.Video;
import io.github.linpeilie.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author w
 * @since 2023/5/20
 */
@Service
public class VideoService {


    private final VideoDao videoDao;
    private final Converter converter;

    @Autowired
    public VideoService(VideoDao videoDao, Converter converter) {
        this.videoDao = videoDao;
        this.converter = converter;
    }

    /**
     * 添加视频
     */
    public void saveOrUpdate(VideoRequest.SaveOrUpdate req){
       if (req.getId()!=null){
           videoDao.findById(req.getId()).ifPresentOrElse(video -> {
               BeanUtil.copy(req,video);
               videoDao.save(video);
           },()->{
               throw new OprException(ResultCode.NOT_FOUND);
           });
           return;
       }
        videoDao.findByUuidAndSource(req.getUuid(),req.getSource()).ifPresent(video -> {
            throw new OprException(MessageFormat.format("uuid:{0} 已存在-{1}",req.getUuid(),req.getTitle()));
        });
        Video video = converter.convert(req,Video.class);
        videoDao.save(video);
    }

    public Video findById(Long id) {
        return videoDao.findById(id).orElseThrow();
    }

    public List<Video> findByStatus(Status status, PageRequest page) {
        return videoDao.findByStatus(status,page);
    }
}
