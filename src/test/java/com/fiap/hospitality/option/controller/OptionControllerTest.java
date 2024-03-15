package com.fiap.hospitality.option.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.hospitality.option.entity.Option;
import com.fiap.hospitality.option.entity.dto.OptionRequest;
import com.fiap.hospitality.option.service.OptionService;
import com.fiap.hospitality.utils.OptionHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class OptionControllerTest {
    private MockMvc mockMvc;

    @Mock
    private OptionService optionService;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        OptionController optionController = new OptionController(optionService);
        mockMvc = MockMvcBuilders.standaloneSetup(optionController)
            .addFilter((request, response, chain) -> {
                response.setCharacterEncoding("UTF-8");
                chain.doFilter(request, response);
            }, "/*")
            .build();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class ListOptions {

        @Test
        void shouldListOptions() throws Exception {
            var option = OptionHelper.createOptions();
            List<Option> options = List.of(option);

            when(optionService.findAll())
                .thenReturn(options);

            mockMvc.perform(get("/options")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

            verify(optionService, times(1))
                .findAll();
        }

        @Test
        void shouldAllowFindAllWhenEmptyList() throws Exception {
            List<Option> options = Collections.emptyList();

            when(optionService.findAll())
                .thenReturn(options);

            mockMvc.perform(get("/options")
                    .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

            verify(optionService, times(1))
                .findAll();
        }
    }

    @Nested
    class FindOption {

        @Test
        void shouldAllowFindOptionByID() throws Exception {
            var option = OptionHelper.createOptions();

            when(optionService.findById(any(String.class)))
                .thenReturn(option);

            mockMvc.perform(get("/options/{id}", option.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

            verify(optionService, times(1))
                .findById(any(String.class));
        }
    }

    @Nested
    class CreateOption {

        @Test
        void shouldAllowCreateNewOption() throws Exception {
            var optionRequest = OptionHelper.createOptionRequest();

            doNothing().when(optionService).save(any(OptionRequest.class));

            mockMvc.perform(post("/options")
                    .content(asJsonString(optionRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

            verify(optionService, times(1))
                .save(any(OptionRequest.class));
        }
    }

    @Nested
    class UpdateOption {

        @Test
        void shouldAllowUpdateOption() throws Exception {
            var optionRequest = OptionHelper.createOptionRequest();
            var option = OptionHelper.createOptions();

            when(optionService.update(anyString(), any(OptionRequest.class)))
                .thenReturn(option);

            mockMvc.perform(put("/options/{id}", 1)
                    .content(asJsonString(optionRequest))
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

            verify(optionService, times(1))
                .update(anyString(), any(OptionRequest.class));
        }
    }

    @Nested
    class DeleteOption {

        @Test
        void shouldAllowDeleteOption() throws Exception {

            doNothing().when(optionService).deleteById(anyString());

            mockMvc.perform(delete("/options/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

            verify(optionService, times(1))
                .deleteById(anyString());
        }
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
