package com.epam.esm.entity;

import java.util.List;
import java.util.Objects;

import com.epam.esm.utils.OrderType;
import com.epam.esm.utils.SortParameter;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchGiftCertificateParameter {

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