package fr.fms.superhotel.dto;


import fr.fms.superhotel.entities.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class HotelDto {

    private String name;
    private String address;
    private String phone;
    private int star;
    private int room;
    private double price;
    private String photo;
    private City city;
}
