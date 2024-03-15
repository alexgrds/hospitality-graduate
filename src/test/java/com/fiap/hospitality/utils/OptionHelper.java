package com.fiap.hospitality.utils;

import com.fiap.hospitality.option.entity.Option;
import com.fiap.hospitality.option.entity.OptionEnum;
import com.fiap.hospitality.option.entity.dto.OptionRequest;

import java.math.BigDecimal;

public abstract class OptionHelper {
    public static Option createOptions() {
        return Option.builder().id("1232")
            .value(BigDecimal.valueOf(4.50))
            .name("Teste")
            .option(OptionEnum.valueOf("SERVICES")).build();
    }

    public static OptionRequest createOptionRequest() {
        return new OptionRequest(OptionEnum.valueOf("SERVICES")
            , "Breakfast"
            , BigDecimal.valueOf(4.90)
        );
    }
}
