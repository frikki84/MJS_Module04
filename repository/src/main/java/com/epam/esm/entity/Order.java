package com.epam.esm.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "users_order")
@Data
@NoArgsConstructor

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime date;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST
    })
    @JoinTable(
            name = "users_order_has_certificate",
            joinColumns = @JoinColumn(name = "order_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "certificate_id", nullable = false))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<GiftCertificate> giftCertificateList;

}
