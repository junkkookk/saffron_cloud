package com.w.saffron.crawler.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.w.saffron.crawler.interfaces.ZmqApi;
import com.w.saffron.exception.OprException;
import com.w.saffron.video.constant.Category;
import com.w.saffron.video.constant.Source;
import com.w.saffron.video.constant.Status;
import com.w.saffron.video.constant.Type;
import com.w.saffron.video.constant.ZmqCons;
import com.w.saffron.video.dto.VideoDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author w
 * @since 2023/6/6
 */
@Service
@Slf4j
public class ZmqService {

    @Autowired
    ZmqApi zmqApi;

    public Optional<String> findPlayUrlByUUid(String uuid) {
        String body = zmqApi.get(ZmqCons.USER_ID, uuid);
        JSONObject jsonObject = JSONUtil.parseObj(body);
        String location = jsonObject.getByPath("data.v.l",String.class);
        Integer code = jsonObject.getByPath("code",Integer.class);
        if (code==1){
            log.info("请求失败" );
            return Optional.empty();
        }
        String url = jsonObject.getByPath("data.v.url",String.class);
        String vipExpired = jsonObject.getByPath("data.u.vip_expired_at",String.class);
        String playUrl = "https://ns" + location + ".zmq71.site/" + url;
        Date date = new Date();
        DateTime dateTime = DateUtil.parseDateTime(vipExpired);
        if (dateTime.toJdkDate().before(date)) {
            log.error("vip已过期");
            return Optional.empty();
        }
        return Optional.of(playUrl);
    }


    public List<VideoDto> findByPage(Integer page) {
        if (page == null) {
            throw new OprException("请传递分页参数");
        }
        List<VideoDto> videoRequests = new ArrayList<>();
        try {
            String body = zmqApi.home(ZmqCons.USER_ID,page);
            JSONObject jsonObject = JSONUtil.parseObj(body);
            JSONArray jsonArray = jsonObject.getByPath("data.videoList", JSONArray.class);
            for (JSONObject video : jsonArray.jsonIter()) {
                String id = video.getStr("i");
                String title = video.getStr("t");
                String url = video.getStr("url");
                String author = video.getByPath("o.n", String.class);
                String t = url.substring(0, url.length() - 41);
                String cover = "https://cnfemdomtube.com/" + t + id + "thumb_0002.jpg";
                videoRequests.add(VideoDto.builder()
                        .source(Source.ZMQ)
                        .category(Category.LIVE)
                        .videoType(Type.M3U8)
                        .author(author)
                        .cover(cover)
                        .title(title)
                        .status(Status.DEFAULT)
                        .uuid(id)
                        .build());
            }
        } catch (Exception e) {
            log.error("采集失败 第:"+page+"页",e);
        }
        return videoRequests;
    }




}
