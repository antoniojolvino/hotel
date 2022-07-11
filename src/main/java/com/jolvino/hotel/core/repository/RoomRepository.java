package com.jolvino.hotel.core.repository;

import com.jolvino.hotel.core.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {
}