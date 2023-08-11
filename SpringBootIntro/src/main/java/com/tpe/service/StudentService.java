package com.tpe.service;


import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.exception.ConflictException;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {


    @Autowired
    private StudentRepository studentRepository;

    //2-
    public List<Student> getAllStudent() {
        //List<Student>students=studentRepository.findAll();//query yazsaydik select*from Student diyecektik
        //return students; boyle de yapabilirsin
        return studentRepository.findAll();

    }

    //4-
    public void saveStudent(Student student) {
        //student daha once kaydedilmis mi ? yani ayni emaile sahip student var mi
      if (studentRepository.existsByEmail(student.getEmail())){
          throw new ConflictException("Email is already exist!");
      }else{
          studentRepository.save(student);
      }
    }


    //6-
    public Student getStudentById(Long id) {

       Student student =studentRepository.findById(id).
               orElseThrow(()->new ResourceNotFoundException("Student not found by id: "+id));
       return student;

    }


    //8-
    public void deleteStudent(Long id) {
        //bu id ye sahip ogrenci var mi once kontrol et
        Student foundStudent=getStudentById(id);
        studentRepository.delete(foundStudent);
    }

    //10-
    public void updateStudent(Long id, StudentDTO studentDTO) {
        //gelen id ile ogrenci var mi varsa getirelim

        Student foundStudent=getStudentById(id);

        //foundStudent.setEmail(studentDTO.getEmail());  daha onceden zaten varsa ne olacak

        Boolean existsEmail=studentRepository.existsByEmail(studentDTO.getEmail());

        //existsEmail true ise bu email baska bir user in olabilir. ya da student in kendi email ini girdi
        //hangi durumda true doner ? id=3 olan studentin email=a@email.com
        //dto dan gelen email=b@email.com ama student tablosunda baska kullanicinin id=4 email=b@email.com ise exc atariz
        //dto da c@email.com girildi ve DB de bu email yoksa update olur
        //dto da email=a@email.com ayni degistirmedi o zaman student tablosunda id=3 email=a@email.com exist true oldugu
        //icin exception atmayip update olmali


        //existsEmail true ise bu email başka bir studentın olabilir, studentın kendi emaili olabilir??
        // id:3 student email:a@email.com
        //dto                student
        //b@email.com        id:4 b@email.com->existsEmail:true--->exception
        //c@email.com        DB de yok-------->existsEmail:false--update:OK
        //a@email.com        id:3 a@email.com ->existsEmail:true--update:OK



        if(existsEmail && foundStudent.getEmail().equals(studentDTO.getEmail())){
            throw new ConflictException("Email already exists!");
        }

        foundStudent.setName(studentDTO.getName());
        foundStudent.setLastName(studentDTO.getLastName());
        foundStudent.setGrade(studentDTO.getGrade());
        foundStudent.setPhoneNumber(studentDTO.getPhoneNumber());
        foundStudent.setEmail(studentDTO.getEmail());

        studentRepository.save(foundStudent);

    }


    //12-
    public Page<Student> getAllStudentPaging(Pageable pageable) {

        return studentRepository.findAll(pageable);

    }


    //14-
    public List<Student> getAllStudentByLastName(String lastname) {

        return studentRepository.findAllByLastName(lastname);

    }

    //16-
    public List<Student> getAllStudentByGrade(Integer grade) {
        return studentRepository.findAllByGrade(grade);
        //return studentRepository.findAllGradeEquals(grade);
    }


//    //18-
//    public StudentDTO getStudentDtoById(Long id) {
//        Student student=getStudentById(id);
//        //StudentDTO studentDTO=new StudentDTO(student.getName(),student.getLastName(),student.getGrade(),student.getPhoneNumber(),student.getEmail());
//        //parametre olarak student objesinin kendisini versek DTO olusturan bir cons olsaydi
//        StudentDTO studentDTO=new StudentDTO(student);
//        return studentDTO;
//    }

    //studentı dto ya mapleme işlemini JPQL ile yapalım
    public StudentDTO getStudentDtoById(Long id) {
        StudentDTO studentDTO=studentRepository.findStudentDtoById(id).orElseThrow(()->
                new ResourceNotFoundException("Student not found by id: "+id));
        return studentDTO;
    }
}
