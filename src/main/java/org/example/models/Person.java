package org.example.models;

import javax.validation.constraints.*;

public class Person {
    private Integer id;

    @NotBlank(message = "Enter the name")
    @Size(min = 2, max = 20, message = "Name length should be from 2 to 30 characters")
    private String name;

    @NotNull(message = "Enter the age")
    @Min(value = 1, message = "age must be above zero")
    @Max(value = 150, message = "Age should not be greater than 150")
    private Integer age;

    @NotEmpty(message = "Enter the email")
    @Email(message = "invalid email")
    private String email;

    public Person() {}
    public Person(Integer id, String name, Integer age, String email) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
