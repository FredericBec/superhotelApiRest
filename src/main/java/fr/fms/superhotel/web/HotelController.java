package fr.fms.superhotel.web;

import fr.fms.superhotel.dto.HotelDto;
import fr.fms.superhotel.entities.Hotel;
import fr.fms.superhotel.exception.RecordNotFoundException;
import fr.fms.superhotel.mapper.HotelMapper;
import fr.fms.superhotel.service.ImplHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @GetMapping("/hotels/hotelManager/{id}")
    public List<Hotel> hotelsByHotelManager(@PathVariable("id") Long id){
        return implHotelService.getHotelsByHotelManager(id);
    }

    @PostMapping("/hotels")
    public ResponseEntity<Hotel> saveHotel(@RequestBody HotelDto hotelDto){
        Hotel hotel = implHotelService.saveHotel(hotelMapper.mapToEntity(hotelDto));
        if(Objects.isNull(hotel)) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(hotel.getId())
                .toUri();

        return ResponseEntity.created(location).body(hotel);
    }

    @PostMapping("/hotels/{id}")
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
            implHotelService.saveHotel(hotel);
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

    @GetMapping(path = "/photo/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<?> getPhoto(@PathVariable("id") Long id) throws IOException{
        byte[] file = null;
        try{
            Hotel hotel = implHotelService.readHotel(id).get();
            if(hotel.getPhoto() == null) hotel.setPhoto("unknown.png");
            file = Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Pictures/hotels/" + hotel.getPhoto()));
        }catch (Exception e){
            //log.error("problème avec download de l'image correspondant à l'hotel d'id : {}", id);
            return ResponseEntity.internalServerError().body(e.getCause());
        }
        return ResponseEntity.ok().body(file);
    }

    @PostMapping(path = "/photo/{id}")
    public ResponseEntity<?> uploadPhoto(MultipartFile file, @PathVariable Long id) throws Exception{
        try {
            Hotel hotel = implHotelService.readHotel(id).get();
            hotel.setPhoto(file.getOriginalFilename());
            Files.write(Paths.get(System.getProperty("user.home") + "/Pictures/hotels/" + hotel.getPhoto()), file.getBytes());
            implHotelService.saveHotel(hotel);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body(e.getCause());
        }
        return ResponseEntity.ok().build();
    }
}
