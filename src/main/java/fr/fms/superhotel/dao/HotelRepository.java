package fr.fms.superhotel.dao;

import fr.fms.superhotel.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    List<Hotel> findAllByOrderByCityName();
    List<Hotel> findByCityId(Long id);
    List<Hotel> findByHotelManagerId(Long id);
}
