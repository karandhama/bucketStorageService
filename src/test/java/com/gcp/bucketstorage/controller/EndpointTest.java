package com.gcp.bucketstorage.controller;

import com.gcp.bucketstorage.exception.BucketStorageException;
import com.gcp.bucketstorage.service.EndpointService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class EndpointTest {

    private static final String REQUEST = "request";
    private static final String FILE_NAME = "fileName";

    @InjectMocks
    private Endpoint endpoint;

    @Mock
    private EndpointService endpointService;

    @Test
    public void shouldStoreRequest() {
        //given
        Mockito.doNothing().when(endpointService).storeMessage(REQUEST, FILE_NAME);
        //when
        ResponseEntity<String> responseEntity = endpoint.store(REQUEST, FILE_NAME);
        //then
        Assert.assertThat(responseEntity.getBody(), Matchers.is(Matchers.notNullValue()));
        Assert.assertThat(responseEntity.getBody(), Matchers.is(Matchers.equalTo(REQUEST)));
        Assert.assertThat(responseEntity.getStatusCode(), Matchers.is(Matchers.equalTo(HttpStatus.OK)));
    }

    @Test
    public void shouldSendUnprocessableEntity() {
        //given
        Mockito.doThrow(BucketStorageException.class).when(endpointService).storeMessage(REQUEST, FILE_NAME);
        //when
        ResponseEntity<String> responseEntity = endpoint.store(REQUEST, FILE_NAME);
        //then
        Assert.assertThat(responseEntity.getBody(), Matchers.is(Matchers.notNullValue()));
        Assert.assertThat(responseEntity.getBody(), Matchers.is(Matchers.equalTo(REQUEST)));
        Assert.assertThat(responseEntity.getStatusCode(), Matchers.is(Matchers.equalTo(HttpStatus.UNPROCESSABLE_ENTITY)));
    }
}