package com.w.saffron.video.dao;


import com.w.saffron.video.constant.Source;
import com.w.saffron.video.constant.Status;
import com.w.saffron.video.domain.Video;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author w
 * @since 2023/5/20
 */
@Repository
public interface VideoDao extends JpaRepository<Video,Long> {

    Optional<Video> findByUuidAndSource(String uuid, Source source);

    List<Video> findByStatus(Status status, Pageable page);

}
