package com.gcp.bucketstorage.exception;

public class BucketStorageException extends RuntimeException {

    public BucketStorageException(String message) {
        super(message);
    }

    public BucketStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
