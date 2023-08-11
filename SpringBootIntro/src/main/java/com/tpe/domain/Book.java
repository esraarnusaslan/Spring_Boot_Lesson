package com.tpe.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("bookName")//student classindaki name ile karismasin diye json formatinde boyle goster diyoruz
    private String name;

    //1S-->ManyBook
    @JsonIgnore//book objemi jsona donustururken studenti alma demek. demezsen stackoverflowerror hatasi veriyor.
    // to string gibi surekli donguye girmesin diye.(bi direction )
    @ManyToOne
    private Student student;


}
