package com.jolvino.hotel.service.impl;

import com.jolvino.hotel.controller.dto.BookingDTO;
import com.jolvino.hotel.controller.dto.mapper.BookingMapper;
import com.jolvino.hotel.exception.*;
import com.jolvino.hotel.model.Booking;
import com.jolvino.hotel.model.repository.BookingRepository;
import com.jolvino.hotel.service.BookingService;
import com.jolvino.hotel.service.enums.ScheduleValidationEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookingServiceImpl implements BookingService {
    private final BookingRepository repository;

    private final BookingMapper mapper;

    public List<BookingDTO> findBookingsByRoomNumber(Integer roomNumber) {
        return Optional.of(
                        repository.findByRoomNumber(roomNumber)
                ).filter(list -> !list.isEmpty())
                .map(booking -> {
                    log.info("There are bookings room number: {}", roomNumber);
                    return mapper.modelToDto(booking);
                }).orElseThrow(EmptyResultException::new);
    }

    public BookingDTO findBookingById(Long bookingID) {
        BookingDTO bookingDTO = repository.findById(bookingID)
                .map(mapper::modelToDto)
                .orElseThrow(NotFoundException::new);
        log.info("There is a booking for the id: {}", bookingID);
        return bookingDTO;
    }

    public BookingDTO createBooking(BookingDTO bookingDTO) {
        try {
            return Optional.ofNullable(bookingDTO)
                    .map(mapper::dtoToModel)
                    .map(this::validateSchedule)
                    .map(repository::save)
                    .map(booking -> {
                        log.info("Booking was saved with the new ID: {}", booking.getId());
                        return mapper.modelToDto(booking);
                    }).orElseThrow(() -> new CreateBookingException("There was a problem creating booking"));
        } catch (JpaObjectRetrievalFailureException ex) {
            throw new CreateBookingException(ex.getCause().getMessage());
        }
    }

    public void deleteBooking(Long bookingID) {
        try {
            repository.deleteById(bookingID);
            log.info("Booking with the ID {}, was deleted", bookingID);
        } catch (DataAccessException ex) {
            throw new NotFoundException();
        }
    }

    public BookingDTO updateBooking(Long bookingID, BookingDTO bookingDTO) {
        BookingDTO response =
                repository.findById(bookingID).map(
                                booking -> {
                                    log.info("Validating booking relationships integrity");
                                    return Optional.of(booking).filter(filterBkn ->
                                                    filterBkn.getRoom().getRoomNumber().equals(
                                                            bookingDTO.getRoom().getRoomNumber())
                                                            && filterBkn.getCustomer().getIdentificationDocument().equals(
                                                            bookingDTO.getCustomer().getIdentificationDocument()))
                                            .orElseThrow(BookingUpdateException::new);
                                })
                        .map(dto -> mapper.dtoToModel(dto, bookingDTO))
                        .map(this::validateSchedule)
                        .map(repository::save)
                        .map(mapper::modelToDto)
                        .orElseThrow(NotFoundException::new);

        log.info("Booking with the id {}, was updated", bookingID);
        return response;
    }

    private Booking validateSchedule(Booking booking) {
        log.info("Starting schedule validation for placed dates {} and {}", booking.getStartDate(), booking.getEndDate());
        ScheduleValidationEnum scheduleValidationEnum = ScheduleValidationEnum.validateBooking(booking);

        if (scheduleValidationEnum != null) {
            throw scheduleValidationEnum.getException();
        } else if (repository.existsScheduleConflicts(booking)) {
            throw new SchedulingException("Placed schedule is conflicting with another one");
        }
        return booking;
    }
}
