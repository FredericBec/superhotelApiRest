package fr.fms.superhotel.web;

import fr.fms.superhotel.entities.Hotel;
import fr.fms.superhotel.entities.HotelManager;
import fr.fms.superhotel.exception.RecordNotFoundException;
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
public class HotelManagerController {

    @Autowired
    private ImplHotelService hotelService;

    @GetMapping("/hotelManagers")
    public List<HotelManager> allHotelManagers(){
        return hotelService.getHotelManagers();
    }

    @PostMapping("/hotelManagers")
    public ResponseEntity<HotelManager> saveHotelManager(@RequestBody HotelManager hotelManager){
        HotelManager manager = hotelService.saveHotelManager(hotelManager);
        if(Objects.isNull(manager)) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(manager.getId())
                .toUri();

        return ResponseEntity.created(location).body(manager);
    }

    @PutMapping("/hotelManagers/{id}")
    public ResponseEntity<HotelManager> updateHotelManager(@PathVariable("id") Long id, @RequestBody HotelManager hotelManager){
        Optional<HotelManager> optional = hotelService.readHotelManager(id);
        if(optional.isPresent()){
            HotelManager manager = optional.get();
            manager.setName(hotelManager.getName());
            manager.setFirstName(hotelManager.getFirstName());
            hotelService.saveHotelManager(manager);
            return ResponseEntity.ok(manager);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/hotelManagers/{id}")
    public ResponseEntity<Void> deleteHotelManager(@PathVariable("id") Long id){
        hotelService.deleteHotelManager(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/hotelManagers/{id}")
    public HotelManager getHotelManagerById(@PathVariable("id") Long id){
        return hotelService.readHotelManager(id)
                .orElseThrow(() -> new RecordNotFoundException("Id de l'hôtel " + id + " n'existe pas"));
    }
}
