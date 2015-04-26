/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Shelter.Comraz.core;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author DAT
 */
@Entity
@Table(name = "staff")
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long pk_staff;

    @Column(nullable = false, unique = false)
    private String name;
    private String career;
    private Integer telephone;
    private String date_of_birth;
    private String address;
    private String description;

    public Staff() {
    }

    public Staff(String name, String career, int telephone, String date_of_birth, String address, String description) {
        this.name = name;
        this.career = career;
        this.telephone = telephone;
        this.date_of_birth = date_of_birth;
        this.address = address;

        this.description = description;

    }

    public void updateStaff(String name, String career, Integer telephone, String date_of_birth, String address, String description) {
        this.name = name;
        this.career = career;
        this.telephone = telephone;
        this.date_of_birth = date_of_birth;
        this.address = address;

        this.description = description;

    }

    public Long getPk_staff() {
        return pk_staff;
    }

    public void setPk_staff(Long pk_staff) {
        this.pk_staff = pk_staff;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
