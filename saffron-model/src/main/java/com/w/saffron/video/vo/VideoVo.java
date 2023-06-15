package com.w.saffron.video.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author w
 * @since 2023/6/15
 */
@Getter
@Setter
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class VideoVo {
    private Long id;
    private String uuid;
    private String title;
    private String author;
    private String playUrl;
    private String cover;
    private String videoType;
    private String source;
    private String category;
    private String status;
    private Date createTime;


}
