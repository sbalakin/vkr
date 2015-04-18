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
import Shelter.Comraz.repo.AnimalRepository;
import Shelter.Comraz.repo.ShelterRepository;
import Shelter.Comraz.repo.TempOwnerRepository;
import Shelter.Comraz.repo.TypeAnimalRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author DAT
 */
@Service
public class MyServiceImp implements MyService {

    @Autowired
    private AnimalRepository animals;

    @Autowired
    private TempOwnerRepository temp_owners;

    @Autowired
    private TypeAnimalRepository type_animals;

    @Autowired
    private ShelterRepository shelters;

    protected final Log log = LogFactory.getLog(getClass());

    @Override
    public Animal animal(Long id) {
        return animals.getOne(id);
    }

    @Override
    public Collection<Animal> animals() {
        return animals.findAll();
    }

    @Override
    public Animal addAnimal(Long pk_type_animal, String name, String type, int gender, String color, String health_status, float weight, String breed, String relationship_with_human, int age, String image, String description, int sterilized) {
        Type_Animal type_animal = type_animals.getOne(pk_type_animal);
        return animals.save(new Animal(type_animal, name, type, gender, color, health_status, weight, breed, relationship_with_human, age, image, description, sterilized));
    }

    @Override
    public Collection<Type_Animal> type_animal() {
        return type_animals.findAll();
    }

    @Override
    public Type_Animal findAnimalPK(String type_animal) {
        for (Type_Animal t : type_animals.findAll()) {
            if (type_animal.equals(t.getTitle())) {
                return t;
            }
        }
        return null;
    }

    @Override
    public void filterGender() {

        for (Animal animal : animals.findAll()) {
            if (animal.getGender() == 1) {
                animal.setShowGender("Мальчик");
            } else if (animal.getGender() == 2) {
                animal.setShowGender("Девочка");
            }
        }
    }

    @Override
    public void filterSterilized() {
        for (Animal animal : animals.findAll()) {
            if (animal.getSterilized() == 1) {
                animal.setSterilized_status("Да");
            } else if (animal.getSterilized() == 0) {
                animal.setSterilized_status("Нет");
            }
        }
    }

    @Override
    public void deleteAnimal(Long pk_animal) {
        animals.delete(pk_animal);
    }

    @Override
    public void updateAnimal(Long pk_animal, Long pk_type_animal, String name, String type, int gender, String color, String health_status, float weight, String breed, String relationship_with_human, int age, String image, String description, int sterilized) {
        Animal animal = animal(pk_animal);
        Type_Animal type_animal = type_animals.getOne(pk_type_animal);
        animal.updateAnimal(type_animal, name, type, gender, color, health_status, weight, breed, relationship_with_human, age, image, description, sterilized);
        animals.save(animal);
    }

    @Override
    public Collection<TempOwner> temp_owners() {
        return temp_owners.findAll();
    }

    @Override
    public TempOwner temp_owner(Long id) {
        return temp_owners.getOne(id);
    }

    @Override
    public TempOwner addTempOwner(String name, int telephone, String address, int amount_of_animal, String image) {
        return temp_owners.save(new TempOwner(name, telephone, address, amount_of_animal, image));
    }

    @Override
    public void updateTempOwner(Long pk_temp_owner, String name, int telephone, String address, int amount_of_animal, String image) {
        TempOwner tOwner = temp_owner(pk_temp_owner);
        tOwner.updateTempOwner(name, telephone, address, amount_of_animal, image);
        temp_owners.save(tOwner);
    }

    @Override
    public void deleteTempOwner(Long pk_temp_owner) {
        temp_owners.delete(pk_temp_owner);
    }

    @Override
    public Collection<Shelter> shelters() {
        return shelters.findAll();
    }

    @Override
    public Shelter addShelter(String name, int telephone, String address, int seat, int free_seat, String site, String email, String description, String image) {
        return shelters.save(new Shelter(name, telephone, address, seat, free_seat, site, email, description, image));
    }

    @Override
    public Shelter shelter(Long id) {
        return shelters.getOne(id);
    }

    @Override
    public void deleteShelter(Long pk_shelter) {
        shelters.delete(pk_shelter);
    }

    @Override
    public void updateShelter(Long pk_shelter, String name, int telephone, String address, int seat, int free_seat, String site, String email, String description, String image) {
        Shelter shelter = shelters.getOne(pk_shelter);
        shelter.updateShelter(name, telephone, address, seat, free_seat, site, email, description, image);
        shelters.save(shelter);
    }

    @Override
    public Collection<Animal> filterAll(Long v1, int v2, int v3) {
        if (v1 == 0 && v2 == 0 && v3 == 0) {
            return animals.findAll();
        } else {
            return filteredAnimals(v1, v2, v3);
        }

    }

    private Collection<Animal> filteredAnimals(Long v1, int v2, int v3) {
        Type_Animal type_animal = type_animals.getOne(v1);
        return animals.findByTypeanimalAndGenderAndSterilized(type_animal, v2, v3);

    }

    @Override
    public Collection<Animal> filterAll2(Long v1) {
        if (v1 == 0) {
            return animals.findAll();
        } else {
            return filteredAnimals(v1);
        }
    }

    private Collection<Animal> filteredAnimals(Long v1) {
        Type_Animal type_animal = type_animals.getOne(v1);
        return animals.findByTypeanimal(type_animal);
    }

    @Override
    public Collection<Animal> filterAllGender(Integer v2) {
        if (v2 == 0) {
            return animals.findAll();
        } else {
            return filteredAnimals(v2);
        }
    }

    private Collection<Animal> filteredAnimals(Integer v2) {

        return animals.findByGender(v2);
    }

    @Override
    public ArrayList<Type_AnimalHelper> getListHelper(Long v1, Integer v2, Integer v3) {
        Type_Animal type_animal = type_animals.getOne(v1);
        ArrayList<Type_AnimalHelper> listHelper = new ArrayList<>();
        Collection<Animal> res;
        if (v1 == 0 && v2 == 0 && v3 == 0) {
            res = animals.findAll();
        }
        else if (v1 != 0 && v2 == 0 && v3 == 0) {
            res = animals.findByTypeanimal(type_animal);
        }
        else if (v1 == 0 && v2 != 0 && v3 == 0) {
            res = animals.findByGender(v2);
        }
        else if (v1 == 0 && v2 == 0 && v3 != 0) {
            res = animals.findBySterilized(v3);
        }
        else if (v1 == 0 && v2 != 0 && v3 != 0) {
            res = animals.findByGenderAndSterilized(v2, v3);
        }
        else if (v1 != 0 && v2 != 0 && v3 == 0) {
            res = animals.findByTypeanimalAndGender(type_animal, v2);
        }
        else if (v1 != 0 && v2 == 0 && v3 != 0) {
            res = animals.findByTypeanimalAndSterilized(type_animal, v3);
        } else {
            res = animals.findByTypeanimalAndGenderAndSterilized(type_animal, v2, v3);
        }

        for (Animal animal : res) {
            Type_AnimalHelper helper = new Type_AnimalHelper(animal);

            listHelper.add(helper);
        }
        return listHelper;
    }

}
