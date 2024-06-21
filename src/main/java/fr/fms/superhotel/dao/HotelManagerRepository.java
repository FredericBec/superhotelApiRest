package fr.fms.superhotel.dao;

import fr.fms.superhotel.entities.HotelManager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelManagerRepository extends JpaRepository<HotelManager, Long> {
}
