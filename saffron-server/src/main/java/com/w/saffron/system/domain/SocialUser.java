package com.w.saffron.system.domain;

import com.w.saffron.common.BaseEntity;
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
 * @since 2023/3/23
 */
@Entity
@Table(name = "sys_social_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Accessors(chain = true)
public class SocialUser extends BaseEntity {

    private String uuid;

    private String source;

    private String accessToken;

}
