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
@EqualsAndHashCode(exclude = "id") // TODO: 7/29/2022 if there are problems with id testing check after commenting this out
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Singular
    @JsonIgnore
    @OneToMany(cascade = {CascadeType.ALL},mappedBy = "company", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Coupon> coupons = new ArrayList<>();




}
