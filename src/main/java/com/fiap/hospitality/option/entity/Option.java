package com.fiap.hospitality.option.entity;

import com.fiap.hospitality.option.entity.dto.OptionRequest;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Table(name = "options")
@Entity(name = "options")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Option {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    private OptionEnum option;

    private String name;
    private BigDecimal value;

    public Option(OptionRequest optionRequest) {
        this.option = optionRequest.getOption();
        this.name = optionRequest.name();
        this.value = optionRequest.value();
    }
}
