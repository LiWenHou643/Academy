package com.liwenhou.SchoolWebApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.stream.BaseStream;

@Data
@Entity
@Table(name = "contact_msg")
public class Contact extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int contactId;

    @NotBlank(message = "This field is required!")
    @Size(min = 3, message = "Name must be at least {min} characters long")
    private String name;

    @NotBlank(message = "This field is required!")
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
    private String mobileNum;

    @NotBlank(message = "This field is required!")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "This field is required!")
    @Size(min = 5, message = "Name must be at least {min} characters long")
    private String subject;

    @NotBlank(message = "This field is required!")
    @Size(min = 10, message = "Name must be at least {min} characters long")
    private String message;

    private String status;
}
