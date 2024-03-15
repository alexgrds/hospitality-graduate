package com.fiap.hospitality.option.repository;

import com.fiap.hospitality.option.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, String> {

    Option findByName(String name);
}
