package com.QuantomSoft.Entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.Pattern;

import jakarta.validation.constraints.Size;

import lombok.Data;

import java.time.LocalDate;

@Entity

@Data

public class Contact {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @NotBlank(message = "First name is required")

    @Size(min = 2, max = 30, message = "First name should be between 2 and 30 characters")

    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only alphabetic characters")

    private String firstName;

    @NotBlank(message = "Last name is required")

    @Size(min = 2, max = 30, message = "Last name should be between 2 and 30 characters")

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only alphabetic characters")

    private String lastName;

    @NotBlank(message = "Country is required")

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Country must contain only alphabetic characters")

    private String country;

    @NotBlank(message = "Designation is required")

    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Designation must contain only letters and spaces")

    private String designation;

    @NotBlank(message = "Company is required")

    @Pattern(regexp = "^[a-zA-Z]+$", message = "Company name must contain only alphabetic characters")

    private String company;

    @Email(message = "Invalid email format")

    @NotBlank(message = "Email is required")

    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$", message = "Email must be in lowercase")

    private String email;

    @Pattern(regexp = "^(\\+91)?\\d{10}$", message = "Phone number must be 10 digits, optionally starting with +91")

    private String phone;

    private String industry;

    private String message;

    private LocalDate dateCreated;

    @PrePersist

    protected void onCreate() {

        dateCreated = LocalDate.now();

    }

    public Long getId() {

        return id;

    }

    public void setId(Long id) {

        this.id = id;

    }

    public @NotBlank(message = "First name is required") @Size(min = 2, max = 30, message = "First name should be between 2 and 30 characters") @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only alphabetic characters") String getFirstName() {

        return firstName;

    }

    public void setFirstName(@NotBlank(message = "First name is required") @Size(min = 2, max = 30, message = "First name should be between 2 and 30 characters") @Pattern(regexp = "^[a-zA-Z]+$", message = "First name must contain only alphabetic characters") String firstName) {

        this.firstName = firstName;

    }

    public @NotBlank(message = "Last name is required") @Size(min = 2, max = 30, message = "Last name should be between 2 and 30 characters") @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only alphabetic characters") String getLastName() {

        return lastName;

    }

    public void setLastName(@NotBlank(message = "Last name is required") @Size(min = 2, max = 30, message = "Last name should be between 2 and 30 characters") @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name must contain only alphabetic characters") String lastName) {

        this.lastName = lastName;

    }

    public @NotBlank(message = "Country is required") @Pattern(regexp = "^[a-zA-Z]+$", message = "Country must contain only alphabetic characters") String getCountry() {

        return country;

    }

    public void setCountry(@NotBlank(message = "Country is required") @Pattern(regexp = "^[a-zA-Z]+$", message = "Country must contain only alphabetic characters") String country) {

        this.country = country;

    }

    public @NotBlank(message = "Designation is required") @Pattern(regexp = "^[a-zA-Z ]+$", message = "Designation must contain only letters and spaces") String getDesignation() {

        return designation;

    }

    public void setDesignation(@NotBlank(message = "Designation is required") @Pattern(regexp = "^[a-zA-Z ]+$", message = "Designation must contain only letters and spaces") String designation) {

        this.designation = designation;

    }

    public @NotBlank(message = "Company is required") @Pattern(regexp = "^[a-zA-Z]+$", message = "Company name must contain only alphabetic characters") String getCompany() {

        return company;

    }

    public void setCompany(@NotBlank(message = "Company is required") @Pattern(regexp = "^[a-zA-Z]+$", message = "Company name must contain only alphabetic characters") String company) {

        this.company = company;

    }

    public @Email(message = "Invalid email format") @NotBlank(message = "Email is required") @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$", message = "Email must be in lowercase") String getEmail() {

        return email;

    }

    public void setEmail(@Email(message = "Invalid email format") @NotBlank(message = "Email is required") @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$", message = "Email must be in lowercase") String email) {

        this.email = email;

    }

    public @Pattern(regexp = "^(\\+91)?\\d{10}$", message = "Phone number must be 10 digits, optionally starting with +91") String getPhone() {

        return phone;

    }

    public void setPhone(@Pattern(regexp = "^(\\+91)?\\d{10}$", message = "Phone number must be 10 digits, optionally starting with +91") String phone) {

        this.phone = phone;

    }

    public String getIndustry() {

        return industry;

    }

    public void setIndustry(String industry) {

        this.industry = industry;

    }

    public String getMessage() {

        return message;

    }

    public void setMessage(String message) {

        this.message = message;

    }

    public LocalDate getDateCreated() {

        return dateCreated;

    }

    public void setDateCreated(LocalDate dateCreated) {

        this.dateCreated = dateCreated;

    }

}
 