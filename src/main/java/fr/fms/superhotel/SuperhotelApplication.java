package fr.fms.superhotel;

import fr.fms.superhotel.dao.CityRepository;
import fr.fms.superhotel.dao.HotelRepository;
import fr.fms.superhotel.entities.AppRole;
import fr.fms.superhotel.entities.AppUser;
import fr.fms.superhotel.entities.City;
import fr.fms.superhotel.entities.Hotel;
import fr.fms.superhotel.service.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;

@SpringBootApplication
public class SuperhotelApplication implements CommandLineRunner {

	@Autowired
	private HotelRepository hotelRepository;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	AccountServiceImpl accountService;

	public static void main(String[] args) {
		SpringApplication.run(SuperhotelApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//generateData();
		generateUserRoles();
	}

	private void generateData(){
		City newYork = new City(null, "New-York", null);
		City paris = new City(null, "Paris", null);
		City dubai = new City(null, "Dubaï", null);
		City saintBrevin = new City(null, "Saint-Brevin-les-Pins", null);
		City toulouse = new City(null, "Toulouse", null);
		City londres = new City(null, "Londres", null);
		City marrakech = new City(null, "Marrakech", null);

		Hotel hilton = new Hotel(null, "Hilton club The quin", "101 West 57th Street at Sixth Avenue New York", "+1 212 245 7846", 5, 200, 211, "hiltonclub.jpg",newYork);
		Hotel luma = new Hotel(null, "Luma Hotel", "120 West 41st Street, New York, NY 10036", "+1 212 730 0099", 4, 130, 79, "lumahotel.jpg", newYork);
		Hotel renwick = new Hotel(null, "The Renwick", "118 East 40th Street, New York, NY 10016", "+1 212 687 4875", 4, 173, 584, "renwick.jpg", newYork);
		Hotel ymca = new Hotel(null, "West Side Ymca", "5 West 63rd Street, New York, NY 10023", "+1 212 912 2600", 2, 370, 115, "ymca.jpg", newYork);
		Hotel molitor = new Hotel(null, "Molitor Hôtel & Spa", "13 rue Nungesser et Coli 75016 Paris", "+33 1 56 07 08 50", 5, 117, 492, "molitor.jpg", paris);
		Hotel ferney = new Hotel(null, "Hotel Ferney République", "10 Boulevard Voltaire 75011 Paris", "+33 1 47 00 21 47", 1, 55, 86, "ferney.jpg", paris);
		Hotel novotel = new Hotel(null, "Novotel Paris Centre Bercy", "85 rue de Bercy 75012 Paris", "+33 1 43 42 30 00", 4, 151, 296, "novotel.jpg", paris);
		Hotel citizen = new Hotel(null, "citizenM Tower of London", "40 Trinity Square, Londres, EC3N 4DJ", "+44 203 519 4830", 4, 370, 446, "citizenM.jpg", londres);
		Hotel radisson = new Hotel(null, "Radisson Blu Edwardian Hampshire Hotel", "31-36 Leicester Square, Londres, WC2H 7LH", "+44 207 839 9399", 5, 127, 671, "radisson.jpg", londres);
		Hotel central = new Hotel(null, "Central Park Hotel", "49/67 Queensborough Terrace, Londres, W2 3SS", "+44 207 229 2424", 3, 317, 166, "centralpark.jpg", londres);
		Hotel timeAsma = new Hotel(null, "Time Asma Hotel", "Al Barsha 1, Dubaï", "+971 4437 7800", 4, 232, 46, "timeasma.png", dubai);
		Hotel palm = new Hotel(null, "W Dubai - The Palm", "West Crescent, Palm Jumeirah, P.O. Box 117992, Dubai, P.O Box 117992, Dubaï", "+971 4245 5555", 5, 349, 210, "thepalm.jpg", dubai);
		Hotel mercure = new Hotel(null, "Hôtel Mercure Toulouse Sud", "3, Avenue Irene Joliot Curie, 31100 Toulouse", "+33561403030", 4, 90, 107, "mercurehotel.jpg", toulouse);
		Hotel jaalRiad = new Hotel(null, "Jaal Riad Resort", "Avenue Mohamed VI, Marrakech 40000", "+212 524 459 500", 5, 220, 148, "jaalriad.jpg", marrakech);
		cityRepository.save(newYork);
		cityRepository.save(londres);
		cityRepository.save(paris);
		cityRepository.save(marrakech);
		cityRepository.save(toulouse);
		cityRepository.save(saintBrevin);
		cityRepository.save(dubai);
		hotelRepository.save(hilton);
		hotelRepository.save(palm);
		hotelRepository.save(mercure);
		hotelRepository.save(timeAsma);
		hotelRepository.save(ymca);
		hotelRepository.save(ferney);
		hotelRepository.save(central);
		hotelRepository.save(renwick);
		hotelRepository.save(citizen);
		hotelRepository.save(radisson);
		hotelRepository.save(jaalRiad);
		hotelRepository.save(novotel);
		hotelRepository.save(molitor);
		hotelRepository.save(luma);
	}

	private void generateUserRoles(){
		accountService.saveUser(new AppUser(null, "fred2024", "fmsacademy", new ArrayList<>()));
		accountService.saveUser(new AppUser(null, "alejandra", "1234", new ArrayList<>()));
		accountService.saveRole(new AppRole(null, "ADMIN"));
		accountService.saveRole(new AppRole(null, "MANAGER"));
		accountService.addRoleToUser("fred2024", "ADMIN");
		accountService.addRoleToUser("fred2024", "MANAGER");
		accountService.addRoleToUser("alejandra", "MANAGER");
	}
}
