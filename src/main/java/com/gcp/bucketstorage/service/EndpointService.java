package com.gcp.bucketstorage.service;

import com.gcp.bucketstorage.exception.BucketStorageException;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EndpointService {

    @Value("${request.store.bucket.name")
    public String bucket;

    @Autowired
    private Storage storage;

    public void storeMessage(String request, String fileName) {
        try {
            System.out.println(bucket);
            storage.create(buildBucketInfo(bucket, fileName), request.getBytes(StandardCharsets.UTF_8));
        } catch (StorageException ex) {
            throw new BucketStorageException(ex.getMessage());
        }
    }

    private BlobInfo buildBucketInfo(final String bucket, final String fileName) {
        return BlobInfo.newBuilder(BlobId.of(bucket, fileName)).build();
    }
}
