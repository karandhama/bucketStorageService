package com.gcp.bucketstorage.exception;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class BucketStorageExceptionTest {

    @Test
    public void shouldCreateAnInstance() {
        BucketStorageException bucketStorageException = new BucketStorageException("msg");
        Assert.assertThat(bucketStorageException, Matchers.is(Matchers.notNullValue()));
    }

}