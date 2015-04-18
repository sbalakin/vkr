/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shelter.Comraz.repo;

import Shelter.Comraz.core.Animal;
import Shelter.Comraz.core.Type_Animal;
import java.util.Collection;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author DAT
 */
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    Collection<Animal> findByGender(Integer gender);

    Collection<Animal> findBySterilized(Integer sterilized);

    Collection<Animal> findByTypeanimal(Type_Animal typeanimal);

    Collection<Animal> findByGenderAndSterilized(Integer gender, Integer sterilized);

    Collection<Animal> findByTypeanimalAndGender(Type_Animal typeanimal, Integer gender);

    Collection<Animal> findByTypeanimalAndSterilized(Type_Animal typeanimal, Integer sterilized);

    Collection<Animal> findByTypeanimalAndGenderAndSterilized(Type_Animal typeanimal, Integer gender, Integer sterilized);
}
