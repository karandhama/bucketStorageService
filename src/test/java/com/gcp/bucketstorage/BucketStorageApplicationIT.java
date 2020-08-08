package com.gcp.bucketstorage;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageException;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BucketStorageServiceApplication.class)
@TestPropertySource("classpath:application-test.properties")
@AutoConfigureMockMvc
public class BucketStorageApplicationIT {

    private static final String PATH = "/store/{fileName}";
    private static final String REQUEST = "request";
    private static final String FILE_NAME = "fileName";
    private static final String BUCKET = "my_bucket";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Storage storage;

    @Test
    @Ignore
    public void shouldStoreRequest() throws Exception {
        //given
        Mockito.doNothing().when(storage).create(buildBucketInfo(BUCKET, FILE_NAME), REQUEST.getBytes(StandardCharsets.UTF_8));
        //when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(REQUEST))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        //then
        String actualResponse = mvcResult.getResponse().getContentAsString();
        Assert.assertThat(actualResponse, Matchers.is(Matchers.notNullValue()));
        Assert.assertThat(actualResponse, Matchers.is(Matchers.equalTo(REQUEST)));
        Assert.assertThat(mvcResult.getResponse().getStatus(), Matchers.is(HttpStatus.OK));
    }

    private BlobInfo buildBucketInfo(final String bucket, final String fileName) {
        return BlobInfo.newBuilder(BlobId.of(bucket, fileName)).build();
    }
}
