package au.com.anz.service.accountenquiry.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.text.SimpleDateFormat;

import static com.google.common.collect.Lists.newArrayList;
import static au.com.anz.service.accountenquiry.config.jackson.DateTimeFormats.DMY_DATE_FORMAT;

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

    protected void assertSuccessfulRequestWithExpectedResponse(RequestBuilder requestBuilder,
                                                               int statusCode,
                                                               String expectedPayload) throws Exception {
        mockMvc.perform(requestBuilder)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.content().string(expectedPayload))
                .andExpect(MockMvcResultMatchers.status().is(statusCode));
    }

    private HttpMessageConverter<?>[] messageConverters() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(objectMapper());
        return newArrayList(messageConverter).toArray(new HttpMessageConverter[1]);
    }

    private ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat(DMY_DATE_FORMAT));
        return mapper;
    }

}
