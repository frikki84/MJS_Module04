package com.epam.esm.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class OrderCreationParameter {

    private long userId = 0;
    private List<CertificateForOrder> certificates;
}
