package com.tpe.dto;


import com.tpe.domain.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {



    @NotBlank(message = "name can not be space")//client asamasindayken
    @Size(min = 2,max = 25,message = "name '${validatedValue}' must be between {min} and {max}")//client asamasindayken
    private String name;


    @NotBlank(message = "lastname can not be space")
    @Size(min = 2,max = 25,message = "lastname '${validatedValue}' must be between {min} and {max}")
    private String lastName;


    private Integer grade;


    @Email(message = "Please provide valid email")//email formatinda olmasini sagliyor
    private String email;// final yapman icin @RequiredArgsConstructor yapman lazim

    private String phoneNumber;

    public StudentDTO(Student student){
        this.name=student.getName();
        this.lastName=student.getLastName();
        this.grade=student.getGrade();
        this.email=student.getEmail();
        this.phoneNumber=student.getPhoneNumber();
    }





}
