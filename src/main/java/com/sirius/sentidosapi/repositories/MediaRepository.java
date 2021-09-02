package com.sirius.sentidosapi.repositories;

import com.sirius.sentidosapi.model.media.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface MediaRepository extends CrudRepository<Media, String> {
    Page<Media> findAll(Pageable pageRequest);

    /* default Set<Media> findAllByIds(Set<String> mediaIds) {
        return mediaIds.stream().map(mediaId -> findById(mediaId).orElseThrow(
                () -> new IllegalArgumentException("Media not found!")
        )).collect(Collectors.toSet());
    }; */

    Set<Media> findAllByIdIn(Set<String> ids);
}