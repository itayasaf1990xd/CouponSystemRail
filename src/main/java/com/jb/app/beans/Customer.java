package com.jb.app.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, updatable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Singular
    @ManyToMany(cascade = {CascadeType.MERGE},fetch = FetchType.EAGER)
    @ToString.Exclude
    @JsonIgnore
    private List<Coupon> coupons = new ArrayList<>();
}
