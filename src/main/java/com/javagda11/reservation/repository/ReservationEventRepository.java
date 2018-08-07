package com.javagda11.reservation.repository;

import com.javagda11.reservation.model.ReservationEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationEventRepository extends JpaRepository<ReservationEvent, Long> {
}
