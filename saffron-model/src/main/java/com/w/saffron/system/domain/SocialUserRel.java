package com.w.saffron.application.sys.domain;

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
@Table(name = "sys_social_rel")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@ToString
@Builder
@Accessors(chain = true)
public class SocialUserRel extends BaseEntity {

    private Long userId;

    private Long socialUserId;

}
