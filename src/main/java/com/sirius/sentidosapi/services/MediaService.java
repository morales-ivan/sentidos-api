package com.sirius.sentidosapi.services;

import com.sirius.sentidosapi.model.media.Media;
import com.sirius.sentidosapi.model.media.MediaEditingDTO;
import com.sirius.sentidosapi.model.media.MediaUploadDTO;
import org.springframework.data.domain.Page;

public interface MediaService {
    Page<Media> getAllMedia(Integer pageSize, Integer pageNumber);

    Media getMediaById(String id);

    Media save(MediaUploadDTO requestedMedia);

    void updateMedia(String id, MediaEditingDTO requestedMedia);

    void deleteMedia(String id);
}
