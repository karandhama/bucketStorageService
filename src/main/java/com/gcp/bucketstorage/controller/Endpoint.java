package com.gcp.bucketstorage.controller;

import com.gcp.bucketstorage.exception.BucketStorageException;
import com.gcp.bucketstorage.service.EndpointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class Endpoint {

    private static final String APPLICATION_JSON_VALUE = MediaType.APPLICATION_JSON_VALUE;
    private static final String PATH = "/store/{fileName}";

    @Autowired
    private EndpointService endpointService;

    @PostMapping(path = PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> store(@RequestBody final String request,
                                        @PathVariable final String fileName) {
        try {
            endpointService.storeMessage(request, fileName);
        } catch (BucketStorageException ex) {
            return ResponseEntity.unprocessableEntity().body(request);
        }
        return ResponseEntity.ok(request);
    }
}
