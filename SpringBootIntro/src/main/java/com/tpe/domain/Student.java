package com.tpe.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor//tum degiskenleri parametreli cons
@NoArgsConstructor//parametresiz cons
//@RequiredArgsConstructor// hangi degiskenlerden cons istiyorsan secili cons
// icin bunu yapiyorsun. final veya notnull olan fieldlardan cons olusturur
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Getter her degisken uzerinde ayri ayri da kullanibilir
    //@Setter(AccessLevel.NONE)  // id nin setter methodu olmasin dedik
    private Long id;

    @NotBlank(message = "name can not be space")//client asamasindayken
    @Size(min = 2,max = 25,message = "name '${validatedValue}' must be between {min} and {max}")//client asamasindayken
    @Column(nullable = false,length = 25)//db asamasinda hata veriyor eger null falan olursa
    private String name;


    @NotBlank(message = "lastname can not be space")
    @Size(min = 2,max = 25,message = "lastname '${validatedValue}' must be between {min} and {max}")
    @Column(nullable = false,length = 25)
    /*final*/ private String lastName;



    /*final*/  private Integer grade;


    @Column(nullable = false,unique = true,length = 50)
    @Email(message = "Please provide valid email")//email formatinda olmasini sagliyor
    /*final*/  private String email;// final yapman icin @RequiredArgsConstructor yapman lazim

    private String phoneNumber;

    private LocalDateTime createDate=LocalDateTime.now();

    @OneToMany(mappedBy = "student")
    private List<Book> bookList=new ArrayList<>();

    @OneToOne
    private User user;


}
