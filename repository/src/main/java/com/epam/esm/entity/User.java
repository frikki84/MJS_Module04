package com.epam.esm.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Order> orderList;

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name='" + name + '\'' + ", email='" + email + '\'' + ", password='" + password
                + '\'' + ", role=" + role + "}";
    }
}
