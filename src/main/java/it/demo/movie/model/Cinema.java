package it.demo.movie.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode
@NotNull
@NoArgsConstructor @AllArgsConstructor
@Document("cinemas")
public class Cinema implements Serializable {

    @Id
    private String id;

    @NotBlank(message = "Cinema name cannot be empty or null")
    private String name;

    @NotBlank(message = "Cinema city cannot be empty or null")
    private String city;

    private List<Projection> projections = new ArrayList<>();

    public Cinema(String name, String city){
        this.name = name;
        this.city = city;
    }

    public Cinema(String name, String city, List<Projection> projections){
        this.name =  name;
        this.city = city;
        this.projections = projections;
    }

}
