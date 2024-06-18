package fr.fms.superhotel.mapper;

import fr.fms.superhotel.dto.HotelDto;
import fr.fms.superhotel.entities.Hotel;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {

    public Hotel mapToEntity(HotelDto hotelDto){

        Hotel hotel = Hotel.builder()
                .name(hotelDto.getName())
                .address(hotelDto.getAddress())
                .phone(hotelDto.getPhone())
                .star(hotelDto.getStar())
                .room(hotelDto.getRoom())
                .price(hotelDto.getPrice())
                .photo(hotelDto.getPhoto())
                .city(hotelDto.getCity())
                .build();

        return hotel;
    }
}
