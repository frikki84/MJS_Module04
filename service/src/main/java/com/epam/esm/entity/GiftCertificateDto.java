package com.epam.esm.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class GiftCertificateDto {
    public static final String ERROR_MESSAGE_NAME = "Name's size must be between  3 and 32 symbols";
    public static final String ERROR_MESSAGE_DESCRIPTION = "Decsription's size must be between  5 and 100 symbols";
    public static final String ERROR_MESSAGE_PRICE = "Price must be equal or more than 0 rub";
    public static final String ERROR_MESSAGE_DURATION = "Duration must be equal or more than 1 day";
    public static final String ERROR_MESSAGE_TAGS = "Gift certificate must contain at least 1 tag";

    private long id;

    @Size(min = 3, max = 32, message = ERROR_MESSAGE_NAME)
    private String name;

    @Size(min = 5, max = 100, message = ERROR_MESSAGE_DESCRIPTION)
    private String description;

    @Min(value = 0, message = ERROR_MESSAGE_PRICE)
    private BigDecimal price;

    @Min(value = 1, message = ERROR_MESSAGE_DURATION)
    private Integer duration;

    //@NotEmpty(message = ERROR_MESSAGE_TAGS)
    List<Tag> tagList;

}
