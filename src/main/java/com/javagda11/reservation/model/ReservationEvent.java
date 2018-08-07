package com.javagda11.reservation.model;

import com.sun.xml.internal.bind.v2.model.core.ID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    private Integer reservationsLimit;
    private String description;

    @OneToMany(mappedBy = "event")
    private List<Reservation> reservations;


}
