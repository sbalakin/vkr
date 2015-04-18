/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shelter;

import Shelter.Comraz.core.Animal;
import Shelter.Comraz.core.Shelter;
import Shelter.Comraz.core.TempOwner;
import Shelter.Comraz.core.Type_Animal;
import Shelter.Comraz.svc.MyService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author DAT
 */
@Controller
public class MyController {

    @Autowired
    private MyService svc;

    protected final Log log = LogFactory.getLog(getClass());

    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index(Model vars) {
        vars.addAttribute("type_animal", svc.type_animal());
        vars.addAttribute("animals", svc.animals());

        svc.filterGender();
        svc.filterSterilized();
        return "animals";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/filter")
    public Object filter(Long v1, Integer v2, Integer v3) {
        return svc.getListHelper(v1, v2, v3);
        //return svc.filterAllGender(v2);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/animals/{pk_animal}")
    public String animal(@PathVariable Long pk_animal, Model vars
    ) {
        Animal animal = svc.animal(pk_animal);
        Type_Animal type_animal = animal.getType_animal();
        vars.addAttribute("animal", animal);
        vars.addAttribute("type_animal", type_animal);

        svc.filterGender();
        svc.filterSterilized();
        return "animal";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/animals/{pk_animal}/edit")
    public String editAnimal(@PathVariable Long pk_animal, Model vars) {
        Animal animal = svc.animal(pk_animal);
        Type_Animal type_animal = animal.getType_animal();
        vars.addAttribute("animal", animal);
        vars.addAttribute("type_animal", type_animal);
        vars.addAttribute("all_type_animal", svc.type_animal());

        return "animal_edit";
    }

//    @ResponseBody
//    @RequestMapping(method = RequestMethod.POST, value = "/animals/add")
//    public Object TestAdd(@RequestParam String type_animal, float weight, int age
//    ) {
//        Type_Animal animalType = svc.findAnimalPK(type_animal);
//
//        Long pk_type_animal = animalType.getPk_type_animal();
//
//        Animal animal = svc.addAnimal(pk_type_animal, null, null, 1, null, null, weight, null, null, age, null, null);
//        // Animal animal = svc.addAnimal(pk_type_animal, weight,age);
//        return "redirect:/";
//    }
    @RequestMapping(method = RequestMethod.POST, value = "/animals/add")
    public String addNewAnimal(@RequestParam String type_animal, String name, String type, int gender, String color, String health_status, float weight, String breed, String relationship_with_human, int age, String image, String description, int sterilized) {
        Type_Animal animalType = svc.findAnimalPK(type_animal);

        Long pk_type_animal = animalType.getPk_type_animal();

        Animal animal = svc.addAnimal(pk_type_animal, name, type, gender, color, health_status, weight, breed, relationship_with_human, age, image, description, sterilized);
        // Animal animal = svc.addAnimal(pk_type_animal, weight,age);
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/animals/{pk_animal}")
    public String deleteAnimal(@PathVariable Long pk_animal) {
        svc.deleteAnimal(pk_animal);
        return "redirect:/";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/animals/{pk_animal}")
    public String editAnimal(@PathVariable Long pk_animal, @RequestParam Long pk_type_animal, String name, String type, int gender, String color, String health_status, float weight, String breed, String relationship_with_human, int age, String image, String description, int sterilized) {
        Animal animal = svc.animal(pk_animal);
        svc.updateAnimal(pk_animal, pk_type_animal, name, type, gender, color, health_status, weight, breed, relationship_with_human, age, image, description, sterilized);
        return "redirect:/animals/{pk_animal}";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/temporary_owners")
    public String temp_owners(Model vars) {
        vars.addAttribute("temp_owners", svc.temp_owners());

        return "temp_owners";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/temporary_owners/add")
    public String addNewTempOwner(@RequestParam String name, int telephone, String address, int amount_of_animal, String image) {

        TempOwner temp_owner = svc.addTempOwner(name, telephone, address, amount_of_animal, image);
        return "redirect:/temporary_owners";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/temporary_owners/{pk_temp_owner}")
    public String temp_owner(@PathVariable Long pk_temp_owner, Model vars
    ) {
        TempOwner temp_owner = svc.temp_owner(pk_temp_owner);

        vars.addAttribute("temp_owner", temp_owner);
        return "temp_owner";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/temporary_owners/{pk_temp_owner}/edit")
    public String editTempOwnerPage(@PathVariable Long pk_temp_owner, Model vars) {
        TempOwner temp_owner = svc.temp_owner(pk_temp_owner);

        vars.addAttribute("temp_owner", temp_owner);

        return "temp_owner_edit";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/temporary_owners/{pk_temp_owner}")
    public String deleteTempOwner(@PathVariable Long pk_temp_owner) {
        svc.deleteTempOwner(pk_temp_owner);
        return "redirect:/temporary_owners";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/temporary_owners/{pk_temp_owner}")
    public String editTempOwner(@PathVariable Long pk_temp_owner, @RequestParam String name, int telephone, String address, int amount_of_animal, String image) {
        TempOwner temp_owner = svc.temp_owner(pk_temp_owner);
        svc.updateTempOwner(pk_temp_owner, name, telephone, address, amount_of_animal, image);
        return "redirect:/temporary_owners/{pk_temp_owner}";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/shelters")
    public String shelters(Model vars) {
        vars.addAttribute("shelters", svc.shelters());

        return "shelters";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/shelters/add")
    public String addNewShelter(@RequestParam String name, int telephone, String address, int seat, int free_seat, String site, String email, String description, String image) {

        Shelter shelter = svc.addShelter(name, telephone, address, seat, free_seat, site, email, description, image);
        return "redirect:/shelters";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/shelters/{pk_shelter}")
    public String shelter(@PathVariable Long pk_shelter, Model vars
    ) {
        Shelter shelter = svc.shelter(pk_shelter);

        vars.addAttribute("shelter", shelter);
        return "shelter";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/shelters/{pk_shelter}")
    public String deleteShelter(@PathVariable Long pk_shelter) {
        svc.deleteShelter(pk_shelter);
        return "redirect:/shelters";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/shelters/{pk_shelter}/edit")
    public String editShelterPage(@PathVariable Long pk_shelter, Model vars) {
        Shelter shelter = svc.shelter(pk_shelter);

        vars.addAttribute("shelter", shelter);

        return "shelter_edit";
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/shelters/{pk_shelter}")
    public String editShelter(@PathVariable Long pk_shelter, @RequestParam String name, int telephone, String address, int seat, int free_seat, String site, String email, String description, String image) {
        Shelter shelter = svc.shelter(pk_shelter);
        svc.updateShelter(pk_shelter, name, telephone, address, seat, free_seat, site, email, description, image);
        return "redirect:/shelters/{pk_shelter}";
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/json")
    public Object descIndex(String type_animal, String name, Float weight, Integer age, Integer sterilized) {

        if (type_animal == null) {
            return "redirect:/null";
        }
        Type_Animal animalType = svc.findAnimalPK(type_animal);

        Long pk_type_animal = animalType.getPk_type_animal();

        Animal animal = svc.addAnimal(pk_type_animal, name, null, 1, null, null, 2.3f, null, null, age, null, null, sterilized);

        return animal;

    }

}
