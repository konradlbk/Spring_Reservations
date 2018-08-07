package com.javagda11.reservation.service;

import com.javagda11.reservation.model.CreateReservationDTO;
import com.javagda11.reservation.model.Reservation;
import com.javagda11.reservation.model.ReservationEvent;
import com.javagda11.reservation.model.ReservationStatus;
import com.javagda11.reservation.repository.ReservationEventRepository;
import com.javagda11.reservation.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

@Autowired
    private ReservationRepository reservationRepository;

@Autowired
private ReservationEventService reservationEventService;
@Autowired
private ReservationEventRepository reservationEventRepository;

public void addReservation(CreateReservationDTO createReservationDTO){
    Optional<ReservationEvent> eventOptional = reservationEventService.find(createReservationDTO.getEventId());
    if (eventOptional.isPresent() && reservationEventRepository.findById(eventOptional.get().getId()).get().getReservationsLimit()>0){
        ReservationEvent event = eventOptional.get();

        int counter=reservationEventRepository.findById(event.getId()).get().getReservationsLimit();

        reservationEventRepository.findById(event.getId()).get().setReservationsLimit(--counter);

        Reservation reservation = new Reservation(null, createReservationDTO.getParticipantName(),
                event, ReservationStatus.UNCONFIRMED);
        saveReservation(reservation);
    }
    else {
        System.out.println("Brak miejsc");
    }

}

public void saveReservation(Reservation reservation){
    reservationRepository.save(reservation);

}

    public List<Reservation> getReservations() {

    return reservationRepository.findAll();
    }


    public void removeReservation(Long id) {
        reservationRepository.deleteById(id);
    }

    public Optional<Reservation> find(Long id) {
        return reservationRepository.findById(id);
    }

}
