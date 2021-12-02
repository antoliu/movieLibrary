package it.demo.movie.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@EqualsAndHashCode
@AllArgsConstructor @NoArgsConstructor
public class Projection {

    private Movie movie;
    private LocalDate startDate;
    private LocalDate endDate;

}
