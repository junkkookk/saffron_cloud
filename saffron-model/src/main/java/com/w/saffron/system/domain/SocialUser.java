package com.w.saffron.system.domain;

import com.w.saffron.common.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * @author w
 * @since 2023/3/23
 */
@Entity
@Table(name = "sys_social_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@ToString
@Builder
@Accessors(chain = true)
public class SocialUser extends BaseEntity {

    private String uuid;

    private String source;

    private String accessToken;

}
