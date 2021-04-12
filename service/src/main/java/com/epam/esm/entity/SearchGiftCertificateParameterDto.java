package com.epam.esm.entity;

import java.util.List;
import java.util.Objects;

import com.epam.esm.utils.OrderType;
import com.epam.esm.utils.SortParameter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchGiftCertificateParameterDto {

    private String name;
    private String description;
    private List<String> tags;
    private SortParameter sortBy;
    private OrderType order;

    public boolean isEmpty() {
        return (Objects.isNull(name) || name.isEmpty()) && (Objects.isNull(description) || description.isEmpty()) && (
                Objects.isNull(tags) || tags.isEmpty()) && Objects.isNull(sortBy) && Objects.isNull(order);
    }
}

