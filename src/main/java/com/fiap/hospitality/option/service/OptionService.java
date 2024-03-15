package com.fiap.hospitality.option.service;


import java.util.List;

import org.springframework.stereotype.Service;

import com.fiap.hospitality.exception.NotFoundException;
import com.fiap.hospitality.option.entity.Option;
import com.fiap.hospitality.option.entity.dto.OptionRequest;
import com.fiap.hospitality.option.repository.OptionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@SuppressWarnings("null")
public class OptionService {

    private final OptionRepository repository;

    public List<Option> findAll() {
        return repository.findAll();
    }

    public Option findById(String optionId) {
        return repository
            .findById(optionId)
            .orElseThrow(() -> new NotFoundException("Could not find any Option given id: " + optionId));
    }

    public void save(OptionRequest optionRequest) {

        Option optionName = repository.findByName(optionRequest.getName());
        if (optionName != null) {
            throw new IllegalArgumentException("Option with Name " + optionRequest.getName() + " already exists");
        }

        Option option = new Option(optionRequest);
        repository.save(option);
    }

    public Option update(String id, OptionRequest updatedOptionRequest) {
        Option option = findById(id);
        Option updatedOption = updatedOptionRequest.returnEntityUpdated(option);
        return repository.save(updatedOption);
    }

    public void deleteById(String id) {
        findById(id);
        repository.deleteById(id);
    }

}
