package fr.fms.superhotel.mapper;

import fr.fms.superhotel.dto.CityDto;
import fr.fms.superhotel.entities.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {

    public City mapToEntity(CityDto cityDto){
        City city = City.builder()
                .name(cityDto.getName())
                .build();
        return city;
    }
}
