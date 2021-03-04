package com.epam.esm.entity;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import javax.validation.constraints.NotBlank;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Relation(itemRelation = "tag", collectionRelation = "tags")
public class TagDto extends RepresentationModel<TagDto> {
    private long id;

    @NotBlank
    private String nameTag;
}
