package com.jolvino.hotel.core.repository;

import com.jolvino.hotel.core.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("select b from Booking b where b.room.number = :number order by b.startDate")
    List<Booking> findByRoomNumber(@Param("number") Integer number);

    /**
     * Verifies if there are no conflicts with the schedule
     *
     * @param number    room number
     * @param startDate scheduling start date
     * @param endDate   scheduling end date
     * @return returns if there are conflicts in scheduling
     */
    @Query("select (count(b) > 0) from Booking b " +
            "where " +
            "b.room.number = :number " +
            "and (" +
            "b.startDate between :startDate and :endDate " +
            "or b.endDate between :startDate and :endDate " +
            "or :startDate between b.startDate and b.endDate " +
            "or :endDate between b.startDate and b.endDate) ")
    boolean existsScheduleConflicts(@Param("number") Integer number,
                                    @Param("startDate") LocalDate startDate,
                                    @Param("endDate") LocalDate endDate);


}
