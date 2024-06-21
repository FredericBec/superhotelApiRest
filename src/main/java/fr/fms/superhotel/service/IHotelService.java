package fr.fms.superhotel.service;

import fr.fms.superhotel.entities.City;
import fr.fms.superhotel.entities.Hotel;
import fr.fms.superhotel.entities.HotelManager;

import java.util.List;
import java.util.Optional;

public interface IHotelService {
    List<Hotel> getHotels();
    List<Hotel> getHotelsByCity(Long cityId);
    List<Hotel> getHotelsByHotelManager(Long hotelManagerId);
    Hotel saveHotel(Hotel hotel);
    void deleteHotel(Long id);
    Optional<Hotel> readHotel(Long id);
    List<City> getCities();
    City saveCity(City city);
    void deleteCity(Long id);
    Optional<City> getCityById(Long id);
    List<HotelManager> getHotelManagers();
    HotelManager saveHotelManager(HotelManager hotelManager);
    void deleteHotelManager(Long id);
    Optional<HotelManager> readHotelManager(Long id);
}
