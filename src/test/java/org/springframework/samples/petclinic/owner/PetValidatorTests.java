package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class PetValidatorTests {

	private static final String REQUIRED = "required";

	private PetValidator petValidator = new PetValidator();

	private Errors errors = mock(Errors.class);

	@Test
	void validateShouldNotSetErrorWhenPetIsOK() {
		Pet pet = new Pet();
		pet.setName("Name");
		pet.setType(new PetType());
		pet.setBirthDate(LocalDate.now());

		petValidator.validate(pet, errors);

		verify(errors, times(0)).rejectValue("name", REQUIRED, REQUIRED);
		verify(errors, times(0)).rejectValue("type", REQUIRED, REQUIRED);
		verify(errors, times(0)).rejectValue("birthDate", REQUIRED, REQUIRED);
	}

	@Test
	void validateShouldSetNameErrorWhenPetHasEmptyName() {
		Pet pet = new Pet();
		pet.setType(new PetType());
		pet.setBirthDate(LocalDate.now());

		petValidator.validate(pet, errors);

		verify(errors, times(1)).rejectValue("name", REQUIRED, REQUIRED);
		verify(errors, times(0)).rejectValue("type", REQUIRED, REQUIRED);
		verify(errors, times(0)).rejectValue("birthDate", REQUIRED, REQUIRED);
	}

	@Test
	void validateShouldSetTypeErrorWhenPetHasNoTypeAndIsNew() {
		Pet pet = new Pet();
		pet.setName("Name");
		pet.setBirthDate(LocalDate.now());

		petValidator.validate(pet, errors);

		verify(errors, times(0)).rejectValue("name", REQUIRED, REQUIRED);
		verify(errors, times(1)).rejectValue("type", REQUIRED, REQUIRED);
		verify(errors, times(0)).rejectValue("birthDate", REQUIRED, REQUIRED);
	}

	@Test
	void validateShouldNotSetTypeErrorWhenPetHasNoTypeAndIsNotNew() {
		Pet pet = new Pet();
		pet.setName("Name");
		pet.setId(1);
		pet.setBirthDate(LocalDate.now());

		petValidator.validate(pet, errors);

		verify(errors, times(0)).rejectValue("name", REQUIRED, REQUIRED);
		verify(errors, times(0)).rejectValue("type", REQUIRED, REQUIRED);
		verify(errors, times(0)).rejectValue("birthDate", REQUIRED, REQUIRED);
	}

	@Test
	void validateShouldSetBirthDateErrorWhenPetHasNoBirthDate() {
		Pet pet = new Pet();
		pet.setName("Name");
		pet.setType(new PetType());

		petValidator.validate(pet, errors);

		verify(errors, times(0)).rejectValue("name", REQUIRED, REQUIRED);
		verify(errors, times(0)).rejectValue("type", REQUIRED, REQUIRED);
		verify(errors, times(1)).rejectValue("birthDate", REQUIRED, REQUIRED);
	}

	@Test
	void supportsShouldReturnTrueWhenInputTypeIsPet() {
		boolean result = petValidator.supports(Pet.class);

		assertTrue(result);
	}

	@Test
	void supportsShouldReturnFalseWhenInputTypeIsNotPet() {
		boolean result = petValidator.supports(Validator.class);

		assertFalse(result);
	}
}
