package com.w.saffron.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author w
 * @since 2023/3/30
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class MailMessageDto implements Serializable {
    private static final long serialVersionUID = 7470226623374955171L;
    String subject;
    String content;
    String to;

}
