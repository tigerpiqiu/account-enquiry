package au.com.anz.service.accountenquiry.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MultiValueMap;

import java.text.SimpleDateFormat;

import static com.google.common.collect.Lists.newArrayList;
import static au.com.anz.service.accountenquiry.config.jackson.DateTimeFormats.DMY_DATE_FORMAT;

@SuppressWarnings("unused")
public abstract class ControllerUnitTestBase {

    protected MockMvc mockMvc;

    protected <T> void initializeController(T testedController) {
        mockMvc = MockMvcBuilders.standaloneSetup(testedController)
                .setMessageConverters(messageConverters())
                .build();
    }

    protected RequestBuilder onGet(String url, String... urlParameters) {
        return MockMvcRequestBuilders.get(url, (Object[]) urlParameters);
    }

    protected RequestBuilder onGetRequest(String url, String... urlParameters) {
        return MockMvcRequestBuilders.get(url, (Object[]) urlParameters);
    }

    protected RequestBuilder onGetRequestWithQueryParams(String url,
                                                         MediaType acceptMediaType,
                                                         MultiValueMap<String, String> queryParams) {
        return MockMvcRequestBuilders.get(url)
                .params(queryParams)
                .accept(acceptMediaType);
    }

    protected ResultMatcher internalServerErrorResponse() {
        return MockMvcResultMatchers.status().isInternalServerError();
    }
    protected void performSuccessfulRequest(RequestBuilder requestBuilder, String expectedPayload) throws Exception {
        assertSuccessfulRequestWithExpectedResponse(requestBuilder, HttpStatus.OK.value(), expectedPayload);
    }

    protected void performSuccessfulPutRequest(RequestBuilder requestBuilder, String expectedPayload) throws Exception {
        assertSuccessfulRequestWithExpectedResponse(requestBuilder, HttpStatus.ACCEPTED.value(), expectedPayload);
    }

    protected void assertSuccessfulRequestWithExpectedResponse(RequestBuilder requestBuilder,
                                                               int statusCode,
                                                               String expectedPayload) throws Exception {
        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string(expectedPayload))
                .andExpect(MockMvcResultMatchers.status().is(statusCode));
    }

    protected void performFailure(RequestBuilder requestBuilder, ResultMatcher expectedStatus) throws Exception {
        mockMvc.perform(requestBuilder)
                .andExpect(expectedStatus);
    }

    protected String writeModelAsJsonString(Object o) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        setMapperConfig(mapper);
        return mapper.writeValueAsString(o);
    }

    private HttpMessageConverter<?>[] messageConverters() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(objectMapper());
        return newArrayList(messageConverter).toArray(new HttpMessageConverter[1]);
    }

    private ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        setMapperConfig(mapper);
        return mapper;
    }

    private void setMapperConfig(ObjectMapper mapper) {
        mapper.setDateFormat(new SimpleDateFormat(DMY_DATE_FORMAT));
    }

}
