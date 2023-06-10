DROP TABLE IF EXISTS sys_task;
CREATE TABLE sys_task(
                         id BIGINT NOT NULL AUTO_INCREMENT  COMMENT 'ID;主键ID' ,
                         task_id VARCHAR(255)    COMMENT '任务id' ,
                         task_name VARCHAR(255)    COMMENT '任务名称' ,
                         task_group VARCHAR(255)    COMMENT '任务组' ,
                         last_time DATETIME    COMMENT '上次运行时间' ,
                         status INT    COMMENT '任务状态 (0未运行 1正在运行 2已暂停 3已停止)' ,
                         create_time DATETIME NOT NULL   COMMENT '创建时间;创建时间' ,
                         update_time DATETIME    COMMENT '修改时间' ,
                         PRIMARY KEY (id)
)  COMMENT = '任务表';

DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user(
                         id BIGINT NOT NULL AUTO_INCREMENT  COMMENT 'ID;主键ID' ,
                         username VARCHAR(255) NOT NULL   COMMENT '用户名' ,
                         password VARCHAR(255) NOT NULL   COMMENT '密码' ,
                         email VARCHAR(255)    COMMENT '邮箱' ,
                         avatar VARCHAR(255)    COMMENT '头像' ,
                         gender INT    COMMENT '性别' ,
                         status INT NOT NULL   COMMENT '状态（0 未激活 1已启用 2已封停）' ,
                         birthday DATE    COMMENT '出生日期' ,
                         last_time DATETIME    COMMENT '上次登录时间' ,
                         create_time DATETIME NOT NULL   COMMENT '创建时间' ,
                         update_time DATETIME NOT NULL   COMMENT '修改时间' ,
                         PRIMARY KEY (id)
)  COMMENT = '用户表';

DROP TABLE IF EXISTS sys_social_user;
CREATE TABLE sys_social_user(
                                id BIGINT NOT NULL AUTO_INCREMENT  COMMENT 'ID;主键ID' ,
                                uuid VARCHAR(255) NOT NULL   COMMENT 'uuid' ,
                                source VARCHAR(255) NOT NULL   COMMENT '第三方来源' ,
                                access_token VARCHAR(255) NOT NULL   COMMENT '授权令牌' ,
                                create_time DATETIME NOT NULL   COMMENT '创建时间;创建时间' ,
                                update_time DATETIME NOT NULL   COMMENT '修改时间' ,
                                PRIMARY KEY (id)
)  COMMENT = '第三方用户表';

DROP TABLE IF EXISTS sys_social_rel;
CREATE TABLE sys_social_rel(
                               id BIGINT NOT NULL AUTO_INCREMENT  COMMENT 'ID;主键ID' ,
                               user_id BIGINT NOT NULL   COMMENT '用户id' ,
                               social_user_id BIGINT NOT NULL   COMMENT '第三方用户id' ,
                               create_time DATETIME NOT NULL   COMMENT '创建时间;创建时间' ,
                               update_time DATETIME NOT NULL   COMMENT '修改时间' ,
                               PRIMARY KEY (id)
)  COMMENT = '第三方用户关系表';

DROP TABLE IF EXISTS sys_message;
CREATE TABLE sys_message(
                            id BIGINT NOT NULL AUTO_INCREMENT  COMMENT 'ID;主键ID' ,
                            sender VARCHAR(255)    COMMENT '消息发送者' ,
                            receive_id BIGINT    COMMENT '消息接收者id' ,
                            target_id VARCHAR(255)    COMMENT '对象id' ,
                            type VARCHAR(255)    COMMENT '消息类型' ,
                            subject VARCHAR(255)    COMMENT '主题' ,
                            content VARCHAR(255)    COMMENT '消息内容' ,
                            send_time DATETIME    COMMENT '发送时间' ,
                            status VARCHAR(255)    COMMENT '状态( 0 未读 1已读 )' ,
                            create_time DATETIME NOT NULL   COMMENT '创建时间;创建时间' ,
                            update_time DATETIME NOT NULL   COMMENT '修改时间' ,
                            PRIMARY KEY (id)
)  COMMENT = '站内消息表';

DROP TABLE IF EXISTS vid_raw_video;
CREATE TABLE vid_raw_video(
                              id BIGINT NOT NULL AUTO_INCREMENT  COMMENT 'ID' ,
                              uuid VARCHAR(255)    COMMENT '视频uuid' ,
                              title VARCHAR(255)    COMMENT '视频标题' ,
                              source VARCHAR(255)    COMMENT '视频来源' ,
                              mistress_name VARCHAR(255)    COMMENT '女主名称' ,
                              status INT    COMMENT '状态;0 未处理 1 已采集' ,
                              error_reason VARCHAR(255)    COMMENT '错误原因' ,
                              m3u8_preview VARCHAR(255)    COMMENT '预览m3u8地址' ,
                              m3u8_real VARCHAR(255)    COMMENT '真实m3u8地址' ,
                              thumb_pic VARCHAR(255)    COMMENT '预览图片;真实路径' ,
                              create_time DATETIME NOT NULL   COMMENT '创建时间' ,
                              update_time DATETIME    COMMENT '修改时间' ,
                              PRIMARY KEY (id)
)  COMMENT = '原始视频信息表';

DROP TABLE IF EXISTS vid_video;
CREATE TABLE vid_video(
                          id BIGINT NOT NULL AUTO_INCREMENT  COMMENT 'ID' ,
                          category_id BIGINT    COMMENT '分类id' ,
                          title VARCHAR(255)    COMMENT '视频标题' ,
                          play_url VARCHAR(255)    COMMENT '播放地址' ,
                          status INT    COMMENT '视频状态（0 未发布 1 已发布）' ,
                          thumb_pic VARCHAR(255)    COMMENT '视频缩略图' ,
                          author VARCHAR(255)    COMMENT '视频作者' ,
                          likes INT    COMMENT '喜欢数' ,
                          views INT    COMMENT '点击数' ,
                          create_time DATETIME NOT NULL   COMMENT '创建时间' ,
                          update_time DATETIME NOT NULL   COMMENT '修改时间' ,
                          PRIMARY KEY (id)
)  COMMENT = '视频表';

DROP TABLE IF EXISTS vid_tag;
CREATE TABLE vid_tag(
                        id BIGINT NOT NULL AUTO_INCREMENT  COMMENT 'ID' ,
                        name VARCHAR(255)    COMMENT '标签名称' ,
                        status INT    COMMENT '状态（0关闭 1开启）' ,
                        create_time DATETIME NOT NULL   COMMENT '创建时间' ,
                        update_time DATETIME NOT NULL   COMMENT '修改时间' ,
                        PRIMARY KEY (id)
)  COMMENT = '标签表';

DROP TABLE IF EXISTS vid_category;
CREATE TABLE vid_category(
                             id BIGINT NOT NULL AUTO_INCREMENT  COMMENT 'ID' ,
                             name VARCHAR(255)    COMMENT '分类名称' ,
                             status VARCHAR(255)    COMMENT '状态（0 关闭 1开启）' ,
                             create_time DATETIME NOT NULL   COMMENT '创建时间' ,
                             update_time DATETIME NOT NULL   COMMENT '修改时间' ,
                             PRIMARY KEY (id)
)  COMMENT = '分类表';

DROP TABLE IF EXISTS vid_history;
CREATE TABLE vid_history(
                            id BIGINT NOT NULL AUTO_INCREMENT  COMMENT 'ID' ,
                            video_id BIGINT    COMMENT '视频id' ,
                            user_id BIGINT    COMMENT '用户id' ,
                            lastTime INT    COMMENT '上次播放时间' ,
                            create_time DATETIME NOT NULL   COMMENT '创建时间' ,
                            update_time DATETIME NOT NULL   COMMENT '修改时间' ,
                            PRIMARY KEY (id)
)  COMMENT = '历史记录表';

DROP TABLE IF EXISTS vid_tag_rel;
CREATE TABLE vid_tag_rel(
                            id BIGINT NOT NULL AUTO_INCREMENT  COMMENT 'ID' ,
                            video_id BIGINT    COMMENT '视频id' ,
                            tag_id BIGINT    COMMENT '标签id' ,
                            create_time DATETIME NOT NULL   COMMENT '创建时间' ,
                            update_time DATETIME NOT NULL   COMMENT '修改时间' ,
                            PRIMARY KEY (id)
)  COMMENT = '标签关系表';

DROP TABLE IF EXISTS vid_like_rel;
CREATE TABLE vid_like_rel(
                             id BIGINT NOT NULL AUTO_INCREMENT  COMMENT 'ID' ,
                             user_id BIGINT    COMMENT '用户id' ,
                             video_id BIGINT    COMMENT '视频id' ,
                             create_time DATETIME NOT NULL   COMMENT '创建时间' ,
                             update_time DATETIME NOT NULL   COMMENT '修改时间' ,
                             PRIMARY KEY (id)
)  COMMENT = '视频喜欢关系表';

