package com.w.saffron.video.dto;

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
import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author w
 * @since 2023/6/13
 */
@Getter
@Setter
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {
    @NotNull(message = "id不能为空",groups = Update.class)
    private Long id;
    @NotBlank(message = "uuid不能为空",groups = Insert.class)
    private String uuid;
    @NotBlank(message = "title不能为空",groups = Insert.class)
    private String title;
    @NotBlank(message = "author不能为空",groups = Insert.class)
    private String author;
    @NotBlank(message = "playUrl不能为空",groups = Insert.class)
    private String playUrl;
    private String cover;
    @NotNull(message = "videoType不能为空",groups = Insert.class)
    @JsonDeserialize(using = CodeToEnumDeserializer.class)
    private Type videoType;
    @NotNull(message = "source不能为空",groups = {Insert.class})
    @JsonDeserialize(using = CodeToEnumDeserializer.class)
    private Source source;
    @NotNull(message = "category不能为空",groups = Insert.class)
    @JsonDeserialize(using = CodeToEnumDeserializer.class)
    private Category category;
    @NotNull(message = "status不能为空",groups = Insert.class)
    @JsonDeserialize(using = CodeToEnumDeserializer.class)
    private Status status;

}
