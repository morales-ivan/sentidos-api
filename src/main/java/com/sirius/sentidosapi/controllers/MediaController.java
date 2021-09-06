package com.sirius.sentidosapi.controllers;

import com.sirius.sentidosapi.model.media.Media;
import com.sirius.sentidosapi.model.media.MediaEditingDTO;
import com.sirius.sentidosapi.model.media.MediaUploadDTO;
import com.sirius.sentidosapi.services.MediaService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/media")
public class MediaController {
    final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @GetMapping("/get-all/{pageNumber}")
    public Page<Media> getAllMedia(@RequestParam(required = false) Integer pageSize,
                                    @PathVariable Integer pageNumber) {
        if (pageSize == null) pageSize = 10;
        return mediaService.getAllMedia(pageSize, pageNumber);
    }

    @GetMapping("/{mediaId}")
    public Media getMedia(@PathVariable String mediaId) {
        return mediaService.getMediaById(mediaId);
    }

    @PostMapping
    public Media saveMedia(@RequestBody MediaUploadDTO mediaUploadDto) {
        return mediaService.save(mediaUploadDto);
    }

    @PutMapping("/{mediaId}")
    public Media updateMedia(@PathVariable String mediaId,
                             @RequestBody MediaEditingDTO mediaEditingDto) {
        mediaService.updateMedia(mediaId, mediaEditingDto);
        return mediaService.getMediaById(mediaId);
    }

    @DeleteMapping("/{mediaId}")
    public ResponseEntity<Media> deleteMedia(@PathVariable String mediaId) {
        mediaService.deleteMedia(mediaId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
