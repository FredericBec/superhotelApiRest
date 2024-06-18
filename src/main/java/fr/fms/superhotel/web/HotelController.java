package fr.fms.superhotel.web;

import fr.fms.superhotel.dto.HotelDto;
import fr.fms.superhotel.entities.Hotel;
import fr.fms.superhotel.exception.RecordNotFoundException;
import fr.fms.superhotel.mapper.HotelMapper;
import fr.fms.superhotel.service.ImplHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class HotelController {
    @Autowired
    private ImplHotelService implHotelService;

    @Autowired
    private HotelMapper hotelMapper;

    @GetMapping("/hotels")
    public List<Hotel> allHotels(){
        return implHotelService.getHotels();
    }

    @GetMapping("/hotels/city/{id}")
    public List<Hotel> hotelsByCity(@PathVariable("id") Long id){
        return implHotelService.getHotelsByCity(id);
    }

    @PostMapping("/hotels")
    public ResponseEntity<Hotel> saveHotel(@RequestBody HotelDto hotelDto){
        Hotel hotel = implHotelService.saveHotel(hotelMapper.mapToEntity(hotelDto));
        if(Objects.isNull(hotel)) return ResponseEntity.noContent().build();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand()
                .toUri();

        return ResponseEntity.created(location).body(hotel);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Hotel> updateHotel(@PathVariable("id") Long id, @RequestBody HotelDto hotelDto){
        Optional<Hotel> optional = implHotelService.readHotel(id);
        if(optional.isPresent()){
            Hotel hotel = optional.get();
            hotel.setName(hotelDto.getName());
            hotel.setAddress(hotelDto.getAddress());
            hotel.setPhone(hotelDto.getPhone());
            hotel.setStar(hotelDto.getStar());
            hotel.setRoom(hotelDto.getRoom());
            hotel.setPrice(hotelDto.getPrice());
            hotel.setPhoto(hotelDto.getPhoto());
            hotel.setCity(hotelDto.getCity());

            return ResponseEntity.ok(hotel);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/hotels/{id}")
    public ResponseEntity<Void> deleteHotel(@PathVariable("id") Long id){
        implHotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hotels/{id}")
    public Hotel getHotelById(@PathVariable("id") Long id){
        return implHotelService.readHotel(id)
                .orElseThrow(() -> new RecordNotFoundException("Id de l'hôtel " + id + " n'existe pas"));
    }
}
