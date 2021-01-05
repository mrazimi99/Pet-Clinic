@petService_annotation
Feature: PetService Feature

  Scenario: First Scenario findPet
    There is a pet called "Khar"
    When The find pet service is called on the pet
    Then The pet is returned successfully

  Scenario: Second Scenario findOwner
    There is an owner called "Moh Az"
    When The find owner service is called on the owner
    Then The owner is returned successfully

  Scenario: Third Scenario newPet
    There is an owner called "Moh Az"
    When The new pet service is called on the owner
    Then The pet is added and returned successfully

  Scenario: Forth Scenario savePet
    There is an owner called "Moh Az"
    When He performs save pet service to add a predefined pet to his list
    Then The added pet is saved successfully
