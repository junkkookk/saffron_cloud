package com.w.saffron.video.dto;

import com.w.saffron.common.BaseDto;
import com.w.saffron.video.constant.Status;
import com.w.saffron.video.domain.Video;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author w
 * @since 2023/6/13
 */
@Getter
@Setter
@AutoMapper(target = Video.class)
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto extends BaseDto {

    private String title;
    private Status status;

}
