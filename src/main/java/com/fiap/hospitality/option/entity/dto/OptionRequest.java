package com.fiap.hospitality.option.entity.dto;

import com.fiap.hospitality.option.entity.Option;
import com.fiap.hospitality.option.entity.OptionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(title = "OptionRequest", description = "Object that represents a option request")
public record OptionRequest(

    @NotNull(message = "Option is mandatory")
    OptionEnum optionEnum,

    @NotBlank(message = "option's name is mandatory")
    @Schema(description = "option's name", example = "Breakfast")
    String name,

    @NotBlank(message = "option's value is mandatory")
    @Schema(description = "option's value", example = "4,50")
    String value) {

    public Option returnEntityUpdated(Option option) {
        option.setName(name);
        option.setValue(value);
        return option;
    }

    public String getName() {
        return name;
    }

    public OptionEnum getOption() {
        return optionEnum;
    }
}
