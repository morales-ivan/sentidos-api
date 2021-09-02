package com.sirius.sentidosapi.services;

import com.sirius.sentidosapi.model.media.Media;
import com.sirius.sentidosapi.model.media.MediaEditingDTO;
import com.sirius.sentidosapi.model.media.MediaUploadDTO;
import com.sirius.sentidosapi.repositories.MediaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MediaServiceImpl implements MediaService{
    private final MediaRepository mediaRepository;
    private final CustomerService customerService;

    public MediaServiceImpl(MediaRepository mediaRepository, CustomerService customerService) {
        this.mediaRepository = mediaRepository;
        this.customerService = customerService;
    }

    @Override
    public Page<Media> getAllMedia(Integer pageSize, Integer pageNumber) {
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize);
        return mediaRepository.findAll(pageRequest);
    }

    @Override
    public Media getMediaById(String id) {
        return safeGetMedia(id);
    }

    @Override
    public Media save(MediaUploadDTO requestedMedia) {
        Media media = new Media();

        media.setImageUrl(requestedMedia.getImageUrl());
        media.setAudioUrl(requestedMedia.getAudioUrl());

        Media savedMedia = mediaRepository.save(media);

        customerService.addMedia(requestedMedia.getCustomerId(), savedMedia);

        return savedMedia;
    }

    @Override
    public void updateMedia(String id, MediaEditingDTO requestedMedia) {
        Media mediaFromDB = safeGetMedia(id);

        mediaFromDB.setImageUrl(requestedMedia.getImageUrl());
        mediaFromDB.setAudioUrl(requestedMedia.getAudioUrl());

        mediaRepository.save(mediaFromDB);
    }

    @Override
    public void deleteMedia(String mediaId) {
        Media media = safeGetMedia(mediaId);
        customerService.removeMedia(media);
        mediaRepository.deleteById(mediaId);
    }

    private Media safeGetMedia(String id) {
        return mediaRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Media not found!"));
    }
}
