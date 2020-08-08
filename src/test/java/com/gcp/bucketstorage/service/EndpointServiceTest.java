package com.gcp.bucketstorage.service;

import com.gcp.bucketstorage.exception.BucketStorageException;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.nio.charset.StandardCharsets;

@RunWith(MockitoJUnitRunner.class)
public class EndpointServiceTest {

    private static final String REQUEST = "request";
    private static final String FILE_NAME = "file_name";
    private static final String BUCKET = "bucket";

    @InjectMocks
    private EndpointService endpointService;

    @Mock
    private Storage storage;

    @Before
    public void init() {
        ReflectionTestUtils.setField(endpointService, "bucket", BUCKET);
    }

    @Test
    public void shouldStoreMessage() {
        //given
        //when
        endpointService.storeMessage(REQUEST, FILE_NAME);
        //then
    }

    @Test(expected = BucketStorageException.class)
    public void shouldThrowException() {
        //given
        Mockito.doThrow(StorageException.class).when(storage).create(buildBucketInfo(BUCKET, FILE_NAME), REQUEST.getBytes(StandardCharsets.UTF_8));
        //when
        endpointService.storeMessage(REQUEST, FILE_NAME);
    }

    private BlobInfo buildBucketInfo(final String bucket, final String fileName) {
        return BlobInfo.newBuilder(BlobId.of(bucket, fileName)).build();
    }
}