package com.fiap.hospitality.option.entity;

import com.fiap.hospitality.option.entity.dto.OptionRequest;
import jakarta.persistence.*;
import lombok.*;

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
    private String value;

    public Option(OptionRequest optionRequest) {
        this.option = optionRequest.getOption();
        this.name = optionRequest.name();
        this.value = optionRequest.value();
    }
}
