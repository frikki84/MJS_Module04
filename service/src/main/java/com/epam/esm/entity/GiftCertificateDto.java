package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.Column;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Relation(itemRelation = "certificate", collectionRelation = "certificates")
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {

    private long id;

    @Size(min = 3, max = 32)
    private String name;

    @Size(min = 5, max = 100)
    private String description;

    @Min(value = 0)
    private BigDecimal price;

    @Min(value = 1)
    private Integer duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime lastUpdateDate;

    @NotEmpty
    List<TagDto> tagList;

    }
