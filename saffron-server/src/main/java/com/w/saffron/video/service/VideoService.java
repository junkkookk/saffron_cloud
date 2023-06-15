package com.w.saffron.video.service;

import com.w.saffron.common.PageParam;
import com.w.saffron.common.PageResult;
import com.w.saffron.covert.VideoConverter;
import com.w.saffron.exception.OprException;
import com.w.saffron.exception.ResultCode;
import com.w.saffron.utils.BeanUtil;
import com.w.saffron.video.dao.VideoDao;
import com.w.saffron.video.domain.Video;
import com.w.saffron.video.dto.VideoDto;
import com.w.saffron.video.dto.VideoSearchDto;
import com.w.saffron.video.vo.VideoVo;
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

    @Autowired
    public VideoService(VideoDao videoDao) {
        this.videoDao = videoDao;
    }

    /**
     * 添加视频
     */
    public void saveOrUpdate(VideoDto videoDto){
        if (videoDto.getId()!=null){
           videoDao.findById(videoDto.getId()).ifPresentOrElse(video -> {
               BeanUtil.copy(videoDto,video);
               videoDao.save(video);
           },()->{
               throw new OprException(ResultCode.NOT_FOUND);
           });
           return;
        }
        videoDao.findByUuidAndSource(videoDto.getUuid(),videoDto.getSource()).ifPresent(video -> {
            throw new OprException(MessageFormat.format("uuid:{0} 已存在-{1}",videoDto.getUuid(),videoDto.getTitle()));
        });
        Video video = VideoConverter.toVideo(videoDto);
        videoDao.save(video);
    }

    public Video findById(Long id) {
        return videoDao.findById(id).orElseThrow(()->new OprException(ResultCode.NOT_FOUND));
    }

    public PageResult<VideoVo> search(VideoSearchDto searchDto) {
        ExampleMatcher matcher = ExampleMatcher.matching();
        return PageResult.of(videoDao.findAll(
                    Example.of(VideoConverter.toVideo(searchDto), matcher.withMatcher("title",
                    ExampleMatcher.GenericPropertyMatcher.of(ExampleMatcher.StringMatcher.CONTAINING))),
                    PageParam.of(searchDto))).map(VideoConverter::toVideoVo);
    }

    @Transactional(rollbackOn = Exception.class)
    public void deleteByIds(List<Long> ids) {
        ids.forEach(videoDao::deleteById);
    }

}
