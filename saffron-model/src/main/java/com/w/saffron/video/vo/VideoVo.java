package com.w.saffron.video.vo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.w.saffron.common.constant.Insert;
import com.w.saffron.common.constant.Update;
import com.w.saffron.common.enumrate.CodeToEnumDeserializer;
import com.w.saffron.video.constant.Category;
import com.w.saffron.video.constant.Source;
import com.w.saffron.video.constant.Status;
import com.w.saffron.video.constant.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
