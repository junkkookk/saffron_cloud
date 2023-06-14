package com.w.saffron.video.service;


import com.w.saffron.common.PageParam;
import com.w.saffron.common.PageResult;
import com.w.saffron.common.exception.OprException;
import com.w.saffron.common.utils.BeanUtil;
import com.w.saffron.video.bean.VideoRequest;
import com.w.saffron.video.dao.VideoDao;
import com.w.saffron.video.domain.Video;
import com.w.saffron.video.dto.VideoDto;
import io.github.linpeilie.Converter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

    public PageResult<Video> search(VideoDto videoDto) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return PageResult.of(
                videoDao.findAll(
                        Example.of(converter.convert(videoDto,Video.class), matcher.withMatcher("title",
                                ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING))),
                        PageParam.of(videoDto)
                ));
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteByIds(List<Long> ids) {
        ids.forEach(videoDao::deleteById);
    }
}
