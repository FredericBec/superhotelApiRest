package fr.fms.superhotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.fms.superhotel.dao.CityRepository;
import fr.fms.superhotel.dao.HotelRepository;
import fr.fms.superhotel.dao.RoleRepository;
import fr.fms.superhotel.dao.UserRepository;
import fr.fms.superhotel.dto.CityDto;
import fr.fms.superhotel.entities.City;
import fr.fms.superhotel.mapper.CityMapper;
import fr.fms.superhotel.service.AccountServiceImpl;
import fr.fms.superhotel.service.ImplHotelService;
import fr.fms.superhotel.service.UserDetailsServiceImpl;
import fr.fms.superhotel.web.CityController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(controllers = CityController.class)
public class CityControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ImplHotelService implHotelService;

    @MockBean
    private AccountServiceImpl accountService;

    @MockBean
    private HotelRepository hotelRepository;

    @MockBean
    private CityRepository cityRepository;

    @MockBean
    private CityMapper cityMapper;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void testGetCities() throws Exception{
        mvc.perform(get("/api/cities"))
                .andExpect(status().isOk());
    }

    @Test
    void testSaveCity() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();
        CityDto cityDto = new CityDto("Aix en provence");
        City city = new City(null, "Aix en provence", null);

        String requestContent = objectMapper.writeValueAsString(cityDto);

        when(cityMapper.mapToEntity(any(CityDto.class))).thenReturn(city);
        when(implHotelService.saveCity(any(City.class))).thenReturn(city);

        MvcResult result = mvc.perform(post("/api/cities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void testDeleteHotel() throws Exception{
        Long cityId = 1L;
        when(implHotelService.readHotel(cityId)).thenReturn(Optional.empty());
        mvc.perform(get("/api/cities/{id}", cityId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
