package com.tpe.repository;


import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository//opsiyonel
public interface StudentRepository extends JpaRepository<Student,Long> { //long u id den dolayi yazdik
                                                                            //JpaRepository<Entity class,id nin data type i>


    Boolean existsByEmail(String email);//bu emaile sahip kayit varsa true yoksa false donecek


    List<Student> findAllByLastName(String lastname);


    List<Student> findAllByGrade(Integer grade);



//    //JPQL
//    @Query("Select s FROM Student s WHERE s.grade=:pGrade")
////    @Query("FROM Student s WHERE s.grade=:pGrade")
//    List<Student> findAllGradeEquals(@Param("pGrade") Integer grade);



//    //SQL
//    @Query(value = "SELECT * FROM student s WHERE s.grade=:pGrade",nativeQuery = true)//SQL.
//    nativeQuery nin defaullttaki degeri false tur.yani query i JPQL olarak algilar. o yuzden true dedik
//    List<Student> findAllGradeEquals(@Param("pGrade") Integer grade);


    //db den gelen studenti dto ya cevirerek gonderiyor
    @Query("select new com.tpe.dto.StudentDTO(s) from Student s where s.id=:pId")
    Optional<StudentDTO> findStudentDtoById(@Param("pId") Long id);








}
