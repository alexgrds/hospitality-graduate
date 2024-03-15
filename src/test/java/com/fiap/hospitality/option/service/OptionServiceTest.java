package com.fiap.hospitality.option.service;


import com.fiap.hospitality.option.entity.Option;
import com.fiap.hospitality.option.entity.dto.OptionRequest;
import com.fiap.hospitality.option.repository.OptionRepository;
import com.fiap.hospitality.option.service.OptionService;
import com.fiap.hospitality.utils.OptionHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class OptionServiceTest {

    private OptionService optionService;

    @Mock
    private OptionRepository repository;

    AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);
        optionService = new OptionService(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Nested
    class findOptions {

        @Test
        void shouldFindAllOptions() {

            List<Option> Options = List.of(OptionHelper.createOptions());

            when(repository.findAll())
                .thenReturn(Options);

            var OptionList = optionService.findAll();

            assertThat(OptionList).hasSize(1);

            verify(repository, times(1)).findAll();

        }

        @Test
        void shouldFindById() {

            Option Option = OptionHelper.createOptions();

            when(repository.findById(anyString()))
                .thenReturn(Optional.ofNullable(Option));

            var OptionReturn = optionService.findById(Option.getId());

            verify(repository, times(1))
                .findById(Option.getId());
            assertThat(OptionReturn.getId())
                .isEqualTo(Option.getId());

        }
    }

    @Nested
    class createOption {

        @Test
        void shouldCreateOption() {

            Option option = OptionHelper.createOptions();
            option.setId(null);
            OptionRequest optionRequest = OptionHelper.createOptionRequest();

            when(repository.save(any(Option.class)))
                .thenReturn(option);

            when(repository.findByName(anyString()))
                .thenReturn(null);

            optionService.save(optionRequest);

            verify(repository, times(1))
                .save(option);
        }
    }

    @Nested
    class updateOption {

        @Test
        void shouldUpdateOption() {

            Option option = OptionHelper.createOptions();
            OptionRequest optionRequest = OptionHelper.createOptionRequest();

            when(repository.save(any(Option.class)))
                .thenReturn(option);

            when(repository.findById(anyString()))
                .thenReturn(Optional.of(option));

            Option optionSaved = optionService.update(option.getId(), optionRequest);

            assertThat(optionSaved)
                .isNotNull();
            assertThat(optionSaved.getName())
                .isEqualTo(optionRequest.getName());

            verify(repository, times(1))
                .save(option);
        }
    }

    @Nested
    class deleteOption {

        @Test
        void shouldDeleteOption() {

            Option Option = OptionHelper.createOptions();

            when(repository.findById(anyString()))
                .thenReturn(Optional.of(Option));

            doNothing().when(repository).deleteById(anyString());

            optionService.deleteById(Option.getId());

            verify(repository, times(1))
                .deleteById(Option.getId());
        }
    }
}
