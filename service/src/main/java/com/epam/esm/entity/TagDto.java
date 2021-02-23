package com.epam.esm.entity;

import lombok.*;
import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@Getter
@Setter
public class TagDto {
    public static final String ERROR_MESSAGE_NAME = "Name's size mustn't be empty or consist of spaces";
    private int id;

    @NotBlank(message = ERROR_MESSAGE_NAME)
    private String nameTag;
}
