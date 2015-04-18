/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shelter.Comraz.svc;

import Shelter.Comraz.core.Animal;
import Shelter.Comraz.core.Shelter;
import Shelter.Comraz.core.TempOwner;
import Shelter.Comraz.core.Type_Animal;
import Shelter.Comraz.core.Type_AnimalHelper;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author DAT
 */
public interface MyService {

    Collection<Animal> animals();

    Collection<Type_Animal> type_animal();

    Collection<TempOwner> temp_owners();

    Collection<Shelter> shelters();

    Animal animal(Long id);

    TempOwner temp_owner(Long id);

    Shelter shelter(Long id);

    public Animal addAnimal(Long pk_type_animal, String name, String type, int gender, String color, String health_status, float weight, String breed, String relationship_with_human, int age, String image, String description, int sterilized);

    public Type_Animal findAnimalPK(String type_animal);

    public void filterGender();

    public void filterSterilized();

    void deleteAnimal(Long pk_animal);

    void updateAnimal(Long id, Long pk_type_animaln, String name, String type, int gender, String color, String health_status, float weight, String breed, String relationship_with_human, int age, String image, String description, int sterilized);

    public TempOwner addTempOwner(String name, int telephone, String address, int amount_of_animal, String image);

    void deleteTempOwner(Long pk_temp_owner);

    public void updateTempOwner(Long pk_temp_owner, String name, int telephone, String address, int amount_of_animal, String image);

    public Shelter addShelter(String name, int telephone, String address, int seat, int free_seat, String site, String email, String description, String image);

    void deleteShelter(Long pk_shelter);

    public void updateShelter(Long pk_shelter, String name, int telephone, String address, int seat, int free_seat, String site, String email, String description, String image);

    public Collection<Animal> filterAll(Long v1, int v2, int v3);

    public Collection<Animal> filterAll2(Long v1);

    public Collection<Animal> filterAllGender(Integer v2);

    public ArrayList<Type_AnimalHelper> getListHelper(Long v1, Integer v2, Integer v3);
}
