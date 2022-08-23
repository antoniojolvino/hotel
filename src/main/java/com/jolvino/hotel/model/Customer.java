package com.jolvino.hotel.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "customer")
@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Customer {

    @Id
    private String identificationDocument;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "customer",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Booking> bookings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Customer customer = (Customer) o;
        return identificationDocument != null && Objects.equals(identificationDocument, customer.identificationDocument);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
