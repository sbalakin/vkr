/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shelter;

import Shelter.Comraz.core.Animal;
import Shelter.Comraz.core.Expense;
import Shelter.Comraz.core.Owner;
import Shelter.Comraz.core.Profit;
import Shelter.Comraz.core.Shelter;
import Shelter.Comraz.core.Sponsor;
import Shelter.Comraz.core.Staff;
import Shelter.Comraz.core.Support_Type;
import Shelter.Comraz.core.TempOwner;
import Shelter.Comraz.core.Type_Animal;
import Shelter.Comraz.core.Volunteer;
import Shelter.Comraz.svc.MyService;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

/**
 *
 * @author DAT
 */
@Controller
public class MyController {

    @Autowired
    private MyService svc;

    protected final Log log = LogFactory.getLog(getClass());

    //animals page
    @RequestMapping(method = RequestMethod.GET, value = "/")
    public String index(Model vars) {
        vars.addAttribute("type_animal", svc.type_animal());
        vars.addAttribute("animals", svc.animals());

        svc.filterGender();
        svc.filterSterilized();
        svc.filterAge();
        return "animals";
    }

    //profits page
    @RequestMapping(method = RequestMethod.GET, value = "/profits")
    public String profits(Model vars) {
        vars.addAttribute("sponsors", svc.sponsors());
        vars.addAttribute("support_types", svc.support_types());
        vars.addAttribute("profits", svc.profits());
        return "profits";
    }

    //support types page
    @RequestMapping(method = RequestMethod.GET, value = "/support_types")
    public String support_types(Model vars) {
        vars.addAttribute("support_types", svc.support_types());

        return "support_types";
    }

    //add 1 profit
    @RequestMapping(method = RequestMethod.POST, value = "/profits/add")
    public String addNewProfit(@RequestParam Long pk_sponsor, @RequestParam Long pk_support_type, int amount, String description, String date_receive) {
//        Sponsor sponsor = svc.sponsor(pk_sponsor);
//        Support_Type support_type = svc.support_type(pk_support_type);

        log.info("support_type:" + pk_support_type);
        log.info("sponsor:" + pk_sponsor);
        Profit profit = svc.addProfit(pk_sponsor, pk_support_type, amount, description, date_receive);
        return "redirect:/profits";
    }

    //filter
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/filter")
    public Object filter(Long v1, Integer v2, Integer v3) {
        return svc.getListHelper(v1, v2, v3);
        //return svc.filterAllGender(v2);
    }

    //show 1 animal
    @RequestMapping(method = RequestMethod.GET, value = "/animals/{pk_animal}")
    public String animal(@PathVariable Long pk_animal, Model vars
    ) {
        Animal animal = svc.animal(pk_animal);
        Type_Animal type_animal = animal.getType_animal();
        vars.addAttribute("animal", animal);
        vars.addAttribute("type_animal", type_animal);

        svc.filterGender();
        svc.filterSterilized();
        svc.filterAge();
        return "animal";
    }

    //edit animal page
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
    //add 1 animal
    @RequestMapping(method = RequestMethod.POST, value = "/animals/add")
    public String addNewAnimal(@RequestParam("file") MultipartFile file, @RequestParam String type_animal, String name, String type, int gender, String color, String health_status, float weight, String breed, String relationship_with_human, int age, String description, int sterilized) {
        Type_Animal animalType = svc.findAnimalPK(type_animal);

        Long pk_type_animal = animalType.getPk_type_animal();

        Animal animal = svc.addAnimal(pk_type_animal, name, type, gender, color, health_status, weight, breed, relationship_with_human, age, description, sterilized);
        // Animal animal = svc.addAnimal(pk_type_animal, weight,age);
        svc.filterGender();
        svc.filterSterilized();
        svc.filterAge();
        addImage(file, "animals", animal.getPk_animal());
        return "redirect:/";
        //return "redirect:/";
    }

    //delete 1 animal
    @RequestMapping(method = RequestMethod.DELETE, value = "/animals/{pk_animal}")
    public String deleteAnimal(@PathVariable Long pk_animal) {
        svc.deleteAnimal(pk_animal);
        deleteImage("animals", pk_animal);
        return "redirect:/";
    }

    //edit 1 animal
    @RequestMapping(method = RequestMethod.POST, value = "/animals/{pk_animal}")
    public String editAnimal(@PathVariable Long pk_animal, @RequestParam Long pk_type_animal, String name, String type, int gender, String color, String health_status, float weight, String breed, String relationship_with_human, Integer age, String description, int sterilized, @RequestParam("file") MultipartFile file) {
        Animal animal = svc.animal(pk_animal);
        svc.updateAnimal(pk_animal, pk_type_animal, name, type, gender, color, health_status, weight, breed, relationship_with_human, age, description, sterilized);
        addImage(file, "animals", animal.getPk_animal());
        return "redirect:/animals/{pk_animal}";
    }

    //tempowners page
    @RequestMapping(method = RequestMethod.GET, value = "/temp_owners")
    public String temp_owners(Model vars) {
        vars.addAttribute("temp_owners", svc.temp_owners());

        return "temp_owners";
    }

    //owners page
    @RequestMapping(method = RequestMethod.GET, value = "/owners")
    public String owners(Model vars) {
        vars.addAttribute("owners", svc.owners());

        return "owners";
    }

    //show 1 owner
    @RequestMapping(method = RequestMethod.GET, value = "/owners/{pk_owner}")
    public String owner(@PathVariable Long pk_owner, Model vars
    ) {
        Owner owner = svc.owner(pk_owner);

        vars.addAttribute("owner", owner);
        return "owner";
    }

    //add 1 owner
    @RequestMapping(method = RequestMethod.POST, value = "/owners/add")
    public String addNewOwner(@RequestParam("file") MultipartFile file, @RequestParam String name, int telephone, String address, int amount_of_animal) {
        log.info(file);
        Owner owner = svc.addOwner(name, telephone, address, amount_of_animal);
        addImage(file, "owners", owner.getPk_owner());
        return "redirect:/owners";
    }

    //add 1 tempowner
    @RequestMapping(method = RequestMethod.POST, value = "/temp_owners/add")
    public String addNewTempOwner(@RequestParam("file") MultipartFile file, @RequestParam String name, int telephone, String address, int amount_of_animal) {

        TempOwner temp_owner = svc.addTempOwner(name, telephone, address, amount_of_animal);
        addImage(file, "temp_owners", temp_owner.getPk_temp_owner());
        return "redirect:/temp_owners";
    }

    //show 1 tempowner
    @RequestMapping(method = RequestMethod.GET, value = "/temp_owners/{pk_temp_owner}")
    public String temp_owner(@PathVariable Long pk_temp_owner, Model vars
    ) {
        TempOwner temp_owner = svc.temp_owner(pk_temp_owner);

        vars.addAttribute("temp_owner", temp_owner);
        return "temp_owner";
    }

    //edit tempowner page
    @RequestMapping(method = RequestMethod.GET, value = "/temp_owners/{pk_temp_owner}/edit")
    public String editTempOwnerPage(@PathVariable Long pk_temp_owner, Model vars) {
        TempOwner temp_owner = svc.temp_owner(pk_temp_owner);

        vars.addAttribute("temp_owner", temp_owner);

        return "temp_owner_edit";
    }

    //delete 1 tempowner
    @RequestMapping(method = RequestMethod.DELETE, value = "/temp_owners/{pk_temp_owner}")
    public String deleteTempOwner(@PathVariable Long pk_temp_owner) {
        svc.deleteTempOwner(pk_temp_owner);
        deleteImage("temp_owners", pk_temp_owner);
        return "redirect:/temp_owners";
    }

    //edit owner page
    @RequestMapping(method = RequestMethod.GET, value = "/owners/{pk_owner}/edit")
    public String editOwnerPage(@PathVariable Long pk_owner, Model vars) {
        Owner owner = svc.owner(pk_owner);

        vars.addAttribute("owner", owner);

        return "owner_edit";
    }

    //delete 1 owner
    @RequestMapping(method = RequestMethod.DELETE, value = "/owners/{pk_owner}")
    public String deleteOwner(@PathVariable Long pk_owner) {
        svc.deleteOwner(pk_owner);
        deleteImage("owners", pk_owner);
        return "redirect:/owners";
    }

    //edit 1 owner
    @RequestMapping(method = RequestMethod.POST, value = "/owners/{pk_owner}")
    public String editOwner(@RequestParam("file") MultipartFile file, @PathVariable Long pk_owner, @RequestParam String name, int telephone, String address, int amount_of_animal) {
        Owner owner = svc.owner(pk_owner);
        svc.updateOwner(pk_owner, name, telephone, address, amount_of_animal);
        addImage(file, "owners", owner.getPk_owner());
        return "redirect:/owners/{pk_owner}";
    }

    //edit 1 tempowner
    @RequestMapping(method = RequestMethod.POST, value = "/temp_owners/{pk_temp_owner}")
    public String editTempOwner(@RequestParam("file") MultipartFile file, @PathVariable Long pk_temp_owner, @RequestParam String name, int telephone, String address, int amount_of_animal) {
        TempOwner temp_owner = svc.temp_owner(pk_temp_owner);
        svc.updateTempOwner(pk_temp_owner, name, telephone, address, amount_of_animal);
        addImage(file, "temp_owners", temp_owner.getPk_temp_owner());
        return "redirect:/temp_owners/{pk_temp_owner}";
    }

    //shelters page
    @RequestMapping(method = RequestMethod.GET, value = "/shelters")
    public String shelters(Model vars) {
        vars.addAttribute("shelters", svc.shelters());

        return "shelters";
    }

    //sponsors page
    @RequestMapping(method = RequestMethod.GET, value = "/sponsors")
    public String sponsors(Model vars) {
        vars.addAttribute("sponsors", svc.sponsors());
        svc.filterOrganization();
        return "sponsors";
    }

    //staves page
    @RequestMapping(method = RequestMethod.GET, value = "/staves")
    public String staves(Model vars) {
        vars.addAttribute("staves", svc.staves());

        return "staves";
    }

    //expenses page
    @RequestMapping(method = RequestMethod.GET, value = "/expenses")
    public String expenses(Model vars) {
        vars.addAttribute("expenses", svc.expenses());

        return "expenses";
    }

    //volunteers page
    @RequestMapping(method = RequestMethod.GET, value = "/volunteers")
    public String volunteer(Model vars) {
        vars.addAttribute("volunteers", svc.volunteers());

        return "volunteers";
    }

    //add 1 shelter
    @RequestMapping(method = RequestMethod.POST, value = "/shelters/add")
    public String addNewShelter(@RequestParam("file") MultipartFile file, @RequestParam String name, int telephone, String address, int seat, int free_seat, String site, String email, String description) {

        Shelter shelter = svc.addShelter(name, telephone, address, seat, free_seat, site, email, description);
        addImage(file, "shelters", shelter.getPk_shelter());
        return "redirect:/shelters";
    }

    //add 1 support type
    @RequestMapping(method = RequestMethod.POST, value = "/support_types/add")
    public String addNewSupport_type(@RequestParam String title) {

        Support_Type support_type = svc.addSupport_type(title);
        return "redirect:/support_types";
    }

    //add 1 sponsor
    @RequestMapping(method = RequestMethod.POST, value = "/sponsors/add")
    public String addNewSponsor(@RequestParam("file") MultipartFile file, @RequestParam String name, int telephone, String address, String site, String email, String description, int is_organization) {

        Sponsor sponsor = svc.addSponsor(name, telephone, address, site, email, description, is_organization);
        addImage(file, "sponsors", sponsor.getPk_sponsor());
        return "redirect:/sponsors";
    }

    //add 1 staff
    @RequestMapping(method = RequestMethod.POST, value = "/staves/add")
    public String addNewStaff(@RequestParam("file") MultipartFile file, @RequestParam String name, String career, Integer telephone, String date_of_birth, String address, String description) {
        log.info(date_of_birth);
        Staff staff = svc.addStaff(name, career, telephone, date_of_birth, address, description);
        addImage(file, "staves", staff.getPk_staff());
        return "redirect:/staves";
    }

    //add 1 expense
    @RequestMapping(method = RequestMethod.POST, value = "/expenses/add")
    public String addNewExpense(@RequestParam String product, int price, String organization, String date_use, String description) {

        Expense expense = svc.addExpense(product, price, organization, date_use, description);
        return "redirect:/expenses";
    }

    //add 1 volunteer
    @RequestMapping(method = RequestMethod.POST, value = "/volunteers/add")
    public String addNewVolunteer(@RequestParam("file") MultipartFile file, @RequestParam String name, String career, Integer telephone, String date_of_birth, String address, String description) {
        log.info(date_of_birth);
        Volunteer volunteer = svc.addVolunteer(name, career, telephone, date_of_birth, address, description);
        addImage(file, "volunteers", volunteer.getPk_volunteer());
        return "redirect:/volunteers";
    }

    //show 1 staff
    @RequestMapping(method = RequestMethod.GET, value = "/staves/{pk_staff}")
    public String staff(@PathVariable Long pk_staff, Model vars
    ) {
        Staff staff = svc.staff(pk_staff);

        vars.addAttribute("staff", staff);
        return "staff";
    }

    //show 1 staff
    @RequestMapping(method = RequestMethod.GET, value = "/expenses/{pk_expense}")
    public String expense(@PathVariable Long pk_expense, Model vars
    ) {
        Expense expense = svc.expense(pk_expense);

        vars.addAttribute("expense", expense);
        return "expense";
    }

    //show 1 profit
    @RequestMapping(method = RequestMethod.GET, value = "/profits/{pk_profit}")
    public String profit(@PathVariable Long pk_profit, Model vars
    ) {
        Profit profit = svc.profit(pk_profit);

        vars.addAttribute("profit", profit);
        return "profit";
    }

    //show 1 volunteer
    @RequestMapping(method = RequestMethod.GET, value = "/volunteers/{pk_volunteer}")
    public String volunteer(@PathVariable Long pk_volunteer, Model vars
    ) {
        Volunteer volunteer = svc.volunteer(pk_volunteer);

        vars.addAttribute("volunteer", volunteer);
        return "volunteer";
    }

    //delete 1 staff
    @RequestMapping(method = RequestMethod.DELETE, value = "/staves/{pk_staff}")
    public String deleteStaff(@PathVariable Long pk_staff) {
        svc.deleteStaff(pk_staff);
        deleteImage("staves", pk_staff);
        return "redirect:/staves";
    }

    //delete 1 support type
    @RequestMapping(method = RequestMethod.DELETE, value = "/support_types/{pk_support_type}")
    public String deleteSupport_type(@PathVariable Long pk_support_type) {
        svc.deleteSupport_type(pk_support_type);
        return "redirect:/support_types";
    }

    //delete 1 expense
    @RequestMapping(method = RequestMethod.DELETE, value = "/expenses/{pk_expense}")
    public String deleteExpense(@PathVariable Long pk_expense) {
        svc.deleteExpense(pk_expense);
        return "redirect:/expenses";
    }

    //delete 1 profit
    @RequestMapping(method = RequestMethod.DELETE, value = "/profits/{pk_profit}")
    public String deleteProfit(@PathVariable Long pk_profit) {
        svc.deleteProfit(pk_profit);
        return "redirect:/profits";
    }

    //delete 1 volunteer
    @RequestMapping(method = RequestMethod.DELETE, value = "/volunteers/{pk_volunteer}")
    public String deleteVolunteer(@PathVariable Long pk_volunteer) {
        svc.deleteVolunteer(pk_volunteer);
        deleteImage("volunteers", pk_volunteer);
        return "redirect:/volunteers";
    }

    //edit staff page
    @RequestMapping(method = RequestMethod.GET, value = "/staves/{pk_staff}/edit")
    public String editStaffPage(@PathVariable Long pk_staff, Model vars) {
        Staff staff = svc.staff(pk_staff);

        vars.addAttribute("staff", staff);

        return "staff_edit";
    }

    //edit profit page
    @RequestMapping(method = RequestMethod.GET, value = "/profits/{pk_profit}/edit")
    public String editProfitPage(@PathVariable Long pk_profit, Model vars) {
        Profit profit = svc.profit(pk_profit);

        Sponsor sponsor = profit.getSponsor();
        Support_Type support_type = profit.getSupporttype();
        vars.addAttribute("sponsor", sponsor);
        vars.addAttribute("support_type", support_type);

        vars.addAttribute("support_types", svc.support_types());
        vars.addAttribute("sponsors", svc.sponsors());
        vars.addAttribute("profit", profit);

        return "profit_edit";
    }

    //edit volunteer page
    @RequestMapping(method = RequestMethod.GET, value = "/volunteers/{pk_volunteer}/edit")
    public String editVolunteerPage(@PathVariable Long pk_volunteer, Model vars) {
        Volunteer volunteer = svc.volunteer(pk_volunteer);

        vars.addAttribute("volunteer", volunteer);

        return "volunteer_edit";
    }

    //edit expense page
    @RequestMapping(method = RequestMethod.GET, value = "/expenses/{pk_expense}/edit")
    public String editExpensePage(@PathVariable Long pk_expense, Model vars) {
        Expense expense = svc.expense(pk_expense);

        vars.addAttribute("expense", expense);

        return "expense_edit";
    }

    //edit 1 staff
    @RequestMapping(method = RequestMethod.POST, value = "/staves/{pk_staff}")
    public String editStaff(@RequestParam("file") MultipartFile file, @PathVariable Long pk_staff, @RequestParam String name, String career, Integer telephone, String date_of_birth, String address, String description) {
        Staff staff = svc.staff(pk_staff);
        svc.updateStaff(pk_staff, name, career, telephone, date_of_birth, address, description);
        addImage(file, "staves", staff.getPk_staff());
        return "redirect:/staves/{pk_staff}";
    }

    //edit 1 profit
    @RequestMapping(method = RequestMethod.POST, value = "/profits/{pk_profit}")
    public String editProfit(@PathVariable Long pk_profit, Long pk_sponsor, Long pk_support_type, int amount, String description, String date_receive) {
        Profit profit = svc.profit(pk_profit);
        svc.updateProfit(pk_profit, pk_sponsor, pk_support_type, amount, description, date_receive);
        return "redirect:/profits/{pk_profit}";
    }

    //edit 1 expense
    @RequestMapping(method = RequestMethod.POST, value = "/expenses/{pk_expense}")
    public String editExpense(@PathVariable Long pk_expense, String product, int price, String organization, String date_use, String description) {
        //Expense expense = svc.expense(pk_expense);
        svc.updateExpense(pk_expense, product, price, organization, date_use, description);
        return "redirect:/expenses/{pk_expense}";
    }

    //edit 1 volunteer
    @RequestMapping(method = RequestMethod.POST, value = "/volunteers/{pk_volunteer}")
    public String editVolunteer(@RequestParam("file") MultipartFile file, @PathVariable Long pk_volunteer, @RequestParam String name, String career, Integer telephone, String date_of_birth, String address, String description) {
        Volunteer volunteer = svc.volunteer(pk_volunteer);
        svc.updateVolunteer(pk_volunteer, name, career, telephone, date_of_birth, address, description);
        addImage(file, "volunteers", volunteer.getPk_volunteer());
        return "redirect:/volunteers/{pk_volunteer}";
    }

    //show 1 shelter
    @RequestMapping(method = RequestMethod.GET, value = "/shelters/{pk_shelter}")
    public String shelter(@PathVariable Long pk_shelter, Model vars
    ) {
        Shelter shelter = svc.shelter(pk_shelter);

        vars.addAttribute("shelter", shelter);
        return "shelter";
    }

    //show 1 support_type
    @RequestMapping(method = RequestMethod.GET, value = "/support_types/{pk_support_type}")
    public String support_type(@PathVariable Long pk_support_type, Model vars
    ) {
        Support_Type support_type = svc.support_type(pk_support_type);

        vars.addAttribute("support_type", support_type);
        return "support_type";
    }

    //show 1 sponsor
    @RequestMapping(method = RequestMethod.GET, value = "/sponsors/{pk_sponsor}")
    public String sponsor(@PathVariable Long pk_sponsor, Model vars
    ) {
        Sponsor sponsor = svc.sponsor(pk_sponsor);
        svc.filterOrganization();
        vars.addAttribute("sponsor", sponsor);
        return "sponsor";
    }

    //delete 1 shelter
    @RequestMapping(method = RequestMethod.DELETE, value = "/shelters/{pk_shelter}")
    public String deleteShelter(@PathVariable Long pk_shelter) {
        svc.deleteShelter(pk_shelter);
        deleteImage("shelters", pk_shelter);
        return "redirect:/shelters";
    }

    //delete 1 sponsor
    @RequestMapping(method = RequestMethod.DELETE, value = "/sponsors/{pk_sponsor}")
    public String deleteSponsor(@PathVariable Long pk_sponsor) {
        svc.deleteSponsor(pk_sponsor);
        deleteImage("sponsors", pk_sponsor);
        return "redirect:/sponsors";
    }

    //edit shelter page
    @RequestMapping(method = RequestMethod.GET, value = "/shelters/{pk_shelter}/edit")
    public String editShelterPage(@PathVariable Long pk_shelter, Model vars) {
        Shelter shelter = svc.shelter(pk_shelter);

        vars.addAttribute("shelter", shelter);

        return "shelter_edit";
    }

    //edit support_type page
    @RequestMapping(method = RequestMethod.GET, value = "/support_types/{pk_support_type}/edit")
    public String editSupport_typePage(@PathVariable Long pk_support_type, Model vars) {
        Support_Type support_type = svc.support_type(pk_support_type);

        vars.addAttribute("support_type", support_type);

        return "support_type_edit";
    }

    //edit sponsor page
    @RequestMapping(method = RequestMethod.GET, value = "/sponsors/{pk_sponsor}/edit")
    public String editSponsorPage(@PathVariable Long pk_sponsor, Model vars) {
        Sponsor sponsor = svc.sponsor(pk_sponsor);

        vars.addAttribute("sponsor", sponsor);

        return "sponsor_edit";
    }

    //edit 1 shelter
    @RequestMapping(method = RequestMethod.POST, value = "/shelters/{pk_shelter}")
    public String editShelter(@RequestParam("file") MultipartFile file, @PathVariable Long pk_shelter, @RequestParam String name, int telephone, String address, int seat, int free_seat, String site, String email, String description) {
        Shelter shelter = svc.shelter(pk_shelter);
        svc.updateShelter(pk_shelter, name, telephone, address, seat, free_seat, site, email, description);
        addImage(file, "shelters", shelter.getPk_shelter());
        return "redirect:/shelters/{pk_shelter}";
    }

    //edit 1 support_type
    @RequestMapping(method = RequestMethod.POST, value = "/support_types/{pk_support_type}")
    public String editShelter(@PathVariable Long pk_support_type, @RequestParam String title) {

        svc.updateSupport_type(pk_support_type, title);
        return "redirect:/support_types/{pk_support_type}";
    }

    //edit 1 sponsor
    @RequestMapping(method = RequestMethod.POST, value = "/sponsors/{pk_sponsor}")
    public String editSponsor(@RequestParam("file") MultipartFile file, @PathVariable Long pk_sponsor, @RequestParam String name, int telephone, String address, String site, String email, String description, int is_organization) {
        Sponsor sponsor = svc.sponsor(pk_sponsor);
        svc.updateSponsor(pk_sponsor, name, telephone, address, site, email, description, is_organization);
        addImage(file, "sponsors", sponsor.getPk_sponsor());
        return "redirect:/sponsors/{pk_sponsor}";
    }

    //get form client
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/json")
    public Object descIndex(String type_animal, String name, Float weight, Integer age, Integer sterilized) {

        if (type_animal == null) {
            return "redirect:/null";
        }
        Type_Animal animalType = svc.findAnimalPK(type_animal);

        Long pk_type_animal = animalType.getPk_type_animal();

        Animal animal = svc.addAnimal(pk_type_animal, null, null, 1, name, name, 2.5f, null, type_animal, age, name, sterilized);

        return animal;

    }

    private void addImage(MultipartFile file, String folderName, Long pk) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                String myPath = System.getProperty("user.dir") + File.separator + "src\\main\\resources\\static\\img" + File.separator + folderName;
                File dir = new File(myPath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Create the file on server
                File serverFile = new File(myPath
                        + File.separator + pk);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));

                stream.write(bytes);
                stream.close();

                log.info("Server File Location=" + serverFile.getAbsolutePath());

                // return "You successfully uploaded file=" + name;
            } catch (Exception e) {
                log.info("You failed to upload " + folderName + pk + " => " + e.getMessage());
            }
        }
    }

    private void deleteImage(String folderName, Long pk) {
        String myPath = System.getProperty("user.dir") + File.separator + "src\\main\\resources\\static\\img" + File.separator + folderName;
        File file = new File(myPath + File.separator + pk);

        if (file.delete()) {
            log.info(file.getName() + "is Deleted");
        } else {

            log.info(file.getName() + "Delete operation is failed.");
        }

    }

    private void replaceImage(MultipartFile file, String folderName, Long pk) {
        String myPath = System.getProperty("user.dir") + File.separator + "src\\main\\resources\\static\\img" + File.separator + folderName;
        File myfile = new File(myPath + File.separator + pk);

        if (!myfile.exists()) {
            log.info(myfile.getName() + "not exist");
        } else {
            if (myfile.delete()) {
                log.info(file.getName() + "is Deleted");
                addImage(file, folderName, pk);
            } else {
                log.info(file.getName() + "Delete operation is failed.");
            }
        }

    }

}
