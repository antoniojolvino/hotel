package com.jolvino.hotel.model.repository;

import com.jolvino.hotel.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("select b from booking b where b.room.roomNumber = :roomNumber order by b.startDate")
    List<Booking> findByRoomNumber(@Param("roomNumber") Integer roomNumber);

    /**
     * Verifies if there are no conflicts with the schedule
     *
     * @param booking booking object
     * @return returns if there are conflicts in scheduling
     */
    @Query("select (count(b) > 0) from booking b " +
            "where " +
            "b.room.roomNumber = :#{#booking.room.roomNumber} " +
            "and (:#{#booking.id} is null or b.id <> :#{#booking.id}) " +
            "and (" +
            "b.startDate between :#{#booking.startDate} and :#{#booking.endDate} " +
            "or b.endDate between :#{#booking.startDate} and :#{#booking.endDate} " +
            "or :#{#booking.startDate} between b.startDate and b.endDate " +
            "or :#{#booking.endDate} between b.startDate and b.endDate) ")
    boolean existsScheduleConflicts(@Param("booking") Booking booking);

}
