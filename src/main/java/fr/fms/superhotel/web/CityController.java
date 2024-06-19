package fr.fms.superhotel.web;

import fr.fms.superhotel.dto.CityDto;
import fr.fms.superhotel.entities.City;
import fr.fms.superhotel.exception.RecordNotFoundException;
import fr.fms.superhotel.mapper.CityMapper;
import fr.fms.superhotel.service.ImplHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class CityController {

    @Autowired
    ImplHotelService hotelService;

    @Autowired
    CityMapper cityMapper;

    @GetMapping("/cities")
    public List<City> getCities(){
        return hotelService.getCities();
    }

    @PostMapping("/cities")
    public ResponseEntity<City> saveCity(@RequestBody CityDto cityDto){
        City city = hotelService.saveCity(cityMapper.mapToEntity(cityDto));
        if(Objects.isNull(city)){
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(city.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/cities/{id}")
    public void deleteCity(@PathVariable("id") Long id){
        hotelService.deleteCity(id);
    }

    @GetMapping("/cities/{id}")
    public City getCityById(@PathVariable("id") Long id)
    {
        return hotelService.getCityById(id)
                .orElseThrow(() -> new RecordNotFoundException("Id de la ville " + id + " n'existe pas"));
    }
}
