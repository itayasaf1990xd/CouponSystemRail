package com.jb.app.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Date;

//TODO: create DTO with companyId instead of Company instance.

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    //@JsonIgnore //15/08 changed
    private Company company;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private int amount;

    @Column(nullable = false)
    private float price;

    @Column(nullable = false)
    private String img;
}
