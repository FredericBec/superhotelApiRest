package fr.fms.superhotel.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @ToString
@Builder @Getter @Setter
public class Hotel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;
    private String phone;
    private int star;
    private int room;
    private double price;
    private String photo;

    @ManyToOne
    @JsonIgnoreProperties(value = "hotels")
    private City city;
}
