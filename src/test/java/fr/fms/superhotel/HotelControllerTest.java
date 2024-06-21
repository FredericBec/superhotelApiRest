package fr.fms.superhotel;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.fms.superhotel.dao.CityRepository;
import fr.fms.superhotel.dao.HotelRepository;
import fr.fms.superhotel.dao.RoleRepository;
import fr.fms.superhotel.dao.UserRepository;
import fr.fms.superhotel.dto.HotelDto;
import fr.fms.superhotel.entities.Hotel;
import fr.fms.superhotel.mapper.HotelMapper;
import fr.fms.superhotel.service.AccountServiceImpl;
import fr.fms.superhotel.service.ImplHotelService;
import fr.fms.superhotel.service.UserDetailsServiceImpl;
import fr.fms.superhotel.web.HotelController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(controllers = HotelController.class)
class HotelControllerTest {

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
    private HotelMapper hotelMapper;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleRepository roleRepository;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void testGetHotels() throws Exception{
        mvc.perform(get("/api/hotels"))
                .andExpect(status().isOk());
    }

    @Test
    void testSaveHotel() throws Exception{
        ObjectMapper objectMapper = new ObjectMapper();

        HotelDto hotelDto = new HotelDto("The palm", "Dubai", "0123456789", 5, 360, 345, "dubai.png", null, null);
        Hotel hotel = new Hotel(null, "The palm", "Dubai", "0123456789", 5, 360, 345, "dubai.png", null, null);

        String requestContent = objectMapper.writeValueAsString(hotelDto);

        when(hotelMapper.mapToEntity(any(HotelDto.class))).thenReturn(hotel);
        when(implHotelService.saveHotel(any(Hotel.class))).thenReturn(hotel);

        MvcResult result = mvc.perform(post("/api/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isCreated())
                .andReturn();

        String jsonResponse = result.getResponse().getContentAsString();
        Hotel responseHotel = objectMapper.readValue(jsonResponse, Hotel.class);
        assertEquals("The palm", responseHotel.getName());
        assertEquals("Dubai", responseHotel.getAddress());
    }

    @Test
    void testDeleteHotel() throws Exception{
        Long hotelId = 1L;
        when(implHotelService.readHotel(hotelId)).thenReturn(Optional.empty());
        mvc.perform(get("/api/hotels/{id}", hotelId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
