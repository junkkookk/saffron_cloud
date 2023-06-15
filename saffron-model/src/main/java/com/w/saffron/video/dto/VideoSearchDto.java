package com.w.saffron.video.dto;

import com.w.saffron.common.BaseDto;
import com.w.saffron.video.constant.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

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
public class VideoSearchDto extends BaseDto {

    private String title;
    private String author;

    private Status status;

}
