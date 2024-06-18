package fr.fms.superhotel.service;

import fr.fms.superhotel.dao.CityRepository;
import fr.fms.superhotel.dao.HotelRepository;
import fr.fms.superhotel.entities.City;
import fr.fms.superhotel.entities.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImplHotelService implements IHotelService{

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<Hotel> getHotels() {
        return hotelRepository.findAllByOrderByCityName();
    }

    @Override
    public List<Hotel> getHotelsByCity(Long cityId) {
        return hotelRepository.findByCityId(cityId);
    }

    @Override
    public Hotel saveHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Override
    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }

    @Override
    public Optional<Hotel> readHotel(Long id) {
        return hotelRepository.findById(id);
    }

    @Override
    public List<City> getCities() {
        return cityRepository.findAll();
    }

    @Override
    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public Optional<City> getCityById(Long id) {
        return Optional.empty();
    }
}