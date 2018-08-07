package com.javagda11.reservation.controller;

import com.javagda11.reservation.model.CreateReservationDTO;
import com.javagda11.reservation.model.Reservation;
import com.javagda11.reservation.model.ReservationEvent;
import com.javagda11.reservation.service.ReservationEventService;
import com.javagda11.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/reservation")
public class ReservationController {

    @Autowired
    private ReservationEventService reservationEventService;

    @Autowired
    private ReservationService reservationService;

    @GetMapping(path = "/add")
    public String add(Model model){
        CreateReservationDTO reservation = new CreateReservationDTO();

        List<ReservationEvent> events = reservationEventService.getAllEvents();

        model.addAttribute("reservation", reservation);
        model.addAttribute("events", events);

        return "reservationAdd";

    }

    @PostMapping(path = "/add")
    public String add(CreateReservationDTO reservation){
        if (reservationService.getReservations().get(reservation.getEventId().intValue()).getEvent().getReservationsLimit()>0){
        reservationService.addReservation(reservation);
        return "redirect:/event/add";

        }
        return "";
    }

    @GetMapping(path = "/list")
    public String list(Model model){
        List<Reservation> reservations = reservationService.getReservations();
        model.addAttribute("reservationList", reservations);

        return "reservationsList";
    }

    @GetMapping(path = "/remove/{id}")
    public String remove(@PathVariable(name = "id") Long id) {
        reservationService.removeReservation(id);

        return "redirect:/reservation/list";
    }

    @GetMapping(path = "/details/{id}")
    public String details(Model model, @PathVariable(name = "id") Long id) {
        Optional<Reservation> reservationOptional = reservationService.find(id);
        if (reservationOptional.isPresent()) {
            model.addAttribute("reservationToDelete", reservationOptional.get());
            return "reservationDetails";
        }
        return "error";
    }

    @PostMapping(path = "/save")
    public String save(Reservation reservation){
        reservationService.saveReservation(reservation);
        return "redirect:/reservation/list";
    }
}
