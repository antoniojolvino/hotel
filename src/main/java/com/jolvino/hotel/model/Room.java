package com.jolvino.hotel.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "room")
@Table
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Room {
    @Id
    @Column(name = "room_number")
    private Integer roomNumber;

    @OneToMany(mappedBy = "room",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Booking> bookings;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Room room = (Room) o;
        return roomNumber != null && Objects.equals(roomNumber, room.roomNumber);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
