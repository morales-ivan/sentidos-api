package com.sirius.sentidosapi.model.media;

import lombok.Data;

@Data
public class MediaUploadDTO {
    private String customerId;
    private String imageUrl;
    private String audioUrl;
}
