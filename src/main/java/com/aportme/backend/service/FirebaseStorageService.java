package com.aportme.backend.service;

import com.google.cloud.storage.*;
import com.google.firebase.cloud.StorageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FirebaseStorageService {

    private static final String METADATA_ACCESS_TOKEN = "firebaseStorageDownloadTokens";

    private final StorageOptions storageOptions;

    public String upload(String fileName, String contentType, byte[] content) {
        Storage storage = storageOptions.getService();

        Bucket bucket = getStorageBucket();
        BlobId blobId = BlobId.of(bucket.getName(), fileName);

        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(contentType)
                .setMetadata(Map.of(METADATA_ACCESS_TOKEN, UUID.randomUUID().toString()))
                .build();

        Blob blob = storage.create(blobInfo, content);
        return blob.getMediaLink();
    }

    public boolean delete(String resourceName) {
        Bucket bucket = getStorageBucket();
        return bucket.get(resourceName).delete();
    }

    private Bucket getStorageBucket() {
        return StorageClient.getInstance().bucket();
    }
}
