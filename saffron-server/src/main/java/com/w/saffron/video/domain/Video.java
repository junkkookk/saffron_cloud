package com.w.saffron.video.domain;


import com.w.saffron.common.BaseEntity;
import com.w.saffron.video.constant.Category;
import com.w.saffron.video.constant.Source;
import com.w.saffron.video.constant.Status;
import com.w.saffron.video.constant.Type;
import com.w.saffron.video.vo.VideoVo;
import io.github.linpeilie.annotations.AutoMapper;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author w
 * @since 2023/5/20
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Accessors(chain = true)
@Table(name="vid_video")
@AutoMapper(target = VideoVo.class)
public class Video extends BaseEntity {

    private String uuid;

    private String title;

    private String author;

    private String playUrl;

    private String cover;

    @Convert(converter = Type.Converter.class)
    private Type videoType;

    @Convert(converter = Source.Converter.class)
    private Source source;

    @Convert(converter = Category.Converter.class)
    private Category category;

    @Convert(converter = Status.Converter.class)
    private Status status;
}
