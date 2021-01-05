package bdd;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.utility.PetTimedCache;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class PetServiceFeatureSteps {

	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	@Autowired
	PetRepository petRepository;

	@Autowired
	PetTypeRepository petTypeRepository;

	@Autowired
	PetTimedCache petTimedCache;

	Logger logger = Mockito.mock(Logger.class);

	private Owner moh;
	private Owner foundOwner;
	private PetType petType;
	private Pet foundPet;

	@Before("@petService_annotation")
	public void setup() {
		petTimedCache = new PetTimedCache(petRepository);
		petService = new PetService(petTimedCache, ownerRepository, logger);
		moh = new Owner();
		petType = new PetType();
		petType.setId(1);
		petType.setName("a pet type");
	}

	@Given("There is a pet called {string}")
	public void thereIsAPetCalled(String petName) {
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName(petName);
		pet.setBirthDate(LocalDate.parse("2020-10-11"));
		pet.setType(petType);
		petTimedCache.save(pet);
	}

	@When("The find pet service is called on the pet")
	public void findPetServiceIsCalledOnThePet() {
		foundPet = petService.findPet(1);
	}

	@Then("The pet is returned successfully")
	public void petIsReturned() {
		assertNotNull(foundPet);
	}

	@Given("There is an owner called {string}")
	public void thereIsAnOwnerCalled(String ownerName) {
		moh.setId(1);
		moh.setFirstName("Moh");
		moh.setLastName("Az");
		moh.setAddress("Najibie - Kooche shahid abbas alavi");
		moh.setCity("Tehran");
		moh.setTelephone("09191919223");
		ownerRepository.save(moh);
	}

	@When("The find owner service is called on the owner")
	public void findOwnerServiceIsCalledOnThePet() {
		foundOwner = petService.findOwner(1);
	}

	@Then("The owner is returned successfully")
	public void ownerIsReturned() {
		assertNotNull(foundOwner);
		assertEquals(java.util.Optional.ofNullable(1), java.util.Optional.ofNullable(foundOwner.getId()));
	}

	@When("The new pet service is called on the owner")
	public void newPetServiceIsCalledOnThePet() {
		foundPet = petService.newPet(moh);
	}

	@Then("The pet is added and returned successfully")
	public void petIsAddedAndReturned() {
		assertNotNull(foundPet);
		assertEquals(moh, foundPet.getOwner());
	}

	@When("He performs save pet service to add a predefined pet to his list")
	public void hePerformsSavePredefinedPetService() {
		Pet pet = new Pet();
		pet.setId(2);
		pet.setName("Gav");
		pet.setBirthDate(LocalDate.parse("2019-02-01"));
		pet.setType(petType);
		moh = new Owner();
		moh.setId(2);
		moh.setFirstName("Msoh");
		moh.setLastName("Asz");
		moh.setAddress("Nasjibie - Kooche shahid abbas alavi");
		moh.setCity("Tehrsan");
		moh.setTelephone("04191919223");
		ownerRepository.save(moh);
		petService.savePet(pet, moh);
	}

	@Then("The added pet is saved successfully")
	public void addedPetIsSaved() {
		moh = ownerRepository.findById(2);
		assertNotNull(petService.findPet(2));
		assertEquals(1, moh.getPets().size());
	}
}
