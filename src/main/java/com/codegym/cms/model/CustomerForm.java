package com.codegym.cms.model;
import org.springframework.web.multipart.MultipartFile;
public class CustomerForm {
    private Long id;
    private MultipartFile image;
    private String firstName;
    private String lastName;


    public CustomerForm() {
    }

    public CustomerForm(Long id, MultipartFile image, String firstName, String lastName) {
        this.id = id;
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public CustomerForm( MultipartFile image, String firstName, String lastName) {
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
