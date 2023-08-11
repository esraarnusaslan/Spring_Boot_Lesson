package com.tpe.controller;


import com.tpe.domain.Student;
import com.tpe.dto.StudentDTO;
import com.tpe.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Controller
@RestController//restapi controlleri
@RequestMapping("/students")//http://localhost:8080/students
public class StudentController {


    Logger logger=LoggerFactory.getLogger(StudentController.class);



    @Autowired
    private StudentService studentService;



    //spring boot u selamlama
    //http://localhost:8080/students/greet+GET
    @GetMapping("/greet")
    public String greet(){
        return "Hello Spring Boot";
    }


    //1-tüm studentları listeleyelim: READ
    //http://localhost:8080/students + GET
    @PreAuthorize("hasRole('ADMIN')")//sadece admin bu istegi yapabilir.ROLE_ADMIN oldugunda sadece ADMIN kismini veririz
    @GetMapping
    public ResponseEntity<List<Student>> listAllStudents(){
        List<Student> studentList=studentService.getAllStudent();
        //return new ResponseEntity<>(studentList, HttpStatus.OK);//200 basarili kodu donuyor
        return  ResponseEntity.ok(studentList);//yukardaki satirla aynisi
    }
    //response:body(data)+HTTP status code
    //ResponseEntity: response bodysi ile birlikte HTTP status code nu göndermemizi sağlar.
    //ResponseEntity.ok() metodu HTTP status olarak OK yada 200 dönmek için bir kısayoldur.

    //3-yeni bir student CREATE etme
    //http://localhost:8080/students + POST + RequestBody(JSON)
    @PostMapping
    public ResponseEntity<Map<String,String>> createStudent(@Valid @RequestBody Student student){

        studentService.saveStudent(student);

        Map<String,String> response=new HashMap<>();
        response.put("message","Student is created successfully");
        response.put("status","success");
        return new ResponseEntity<>(response,HttpStatus.CREATED);//201

    }

    //@RequestBody: Http Requestin body sindeki JSON formatindaki datayi Student objesine mapler.
    // Hangi degisken onunde kullandiysan onu mapler
    //JSON<->Entity mapleme islemini ise JACKSON kutuphanesi yapiyor


    //5- belirli bir id ile student goruntuleme                 request ile kullanimi
    //http://localhost:8080/students/query?id=1 + GET
    @GetMapping("/query")
    public ResponseEntity<Student> getStudent(@RequestParam("id") Long id){
        Student student=studentService.getStudentById(id);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }

    //client tan bilgi almak icin 3 yontem var.
    //1. RequestBody(JSON)
    //2. RequestParam()  query?id=1 gibi. birden fazla paramatre girilecekse bu tercih ediliyor
    //3. PathParam()  /1 gibi


    //5- belirli bir id ile student goruntuleme                     path ile kullanimi
    //http://localhost:8080/students/query?id=1 + GET
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") Long id){
        Student student=studentService.getStudentById(id);
        return new ResponseEntity<>(student,HttpStatus.OK);
    }


    //7- id si verilen Student i silme
    //http://localhost:8080/students/1 + DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>>deleteStudent(@PathVariable Long id){
        studentService.deleteStudent(id);
        Map<String,String> response=new HashMap<>();
        response.put("message","Student is deleted successfully");
        response.put("status","success");
        return ResponseEntity.ok(response);//200
    }

    //9-belirli bir id ile studenti update etmek.(name,lastName,grade,email,phoneNumber update edilebilir)
    //http://localhost:8080/students/1 + UPDATE + JSON
    @PutMapping("/{id}")//put tamamen patch kismi
    public ResponseEntity<Map<String,String>> updateStudent(@PathVariable("id") Long id,
                                                            @Valid @RequestBody StudentDTO studentDTO){
        studentService.updateStudent(id,studentDTO);
        Map<String,String> response=new HashMap<>();
        response.put("message","Student is updated successfully");
        response.put("status","success");
        return ResponseEntity.ok(response);//200
    }

    //11-pagination-sayfalandırma
    //tüm kayıtları page page listeleyelim
    //http://localhost:8080/students/page?
    //                               page=1&
    //                               size=10&
    //                               sort=name&
    //                               direction=DESC + GET
    @GetMapping("/page")
    public ResponseEntity<Page<Student>> getAllStudentByPage
    (@RequestParam(value = "page",required = false,defaultValue = "0") int page,//hangi page gösterilsin.
     // value,required,defaultvalue girersen sayfa bilgisi vermzse bunlari dersin. defaultvalue ile 0 diyebilirsin
     @RequestParam(value = "size",required = false,defaultValue = "2") int size,//kaç kayıt.yukardaki ile ayni calisiyor
     @RequestParam("sort") String prop,//hangi fielda göre
     @RequestParam("direction") Sort.Direction direction)//sıralama yönü
    {
        Pageable pageable= PageRequest.of(page,size,Sort.by(direction,prop));
        Page<Student> studentsByPage=studentService.getAllStudentPaging(pageable);
        return new ResponseEntity<>(studentsByPage,HttpStatus.OK);
    }


    //13-lastName ile studentları listeleyelim. **SIRA SİZDE**
    ////http://localhost:8080/students/querylastname?lastName=Bey
    @GetMapping("/querylastname")
    public ResponseEntity<List<Student>> getAllStudentsByLastName(@RequestParam("lastName") String lastname){

        List<Student> studentList=studentService.getAllStudentByLastName(lastname);

        return ResponseEntity.ok(studentList);
    }

    //15-grade ile studentları listeleyelim. **ÖDEV**
    //http://localhost:8080/students/grade/99
    @GetMapping("/grade/{grade}")
    public ResponseEntity<List<Student>> getAllStudentByGrade(@PathVariable("grade") Integer grade){
        List<Student> studentList=studentService.getAllStudentByGrade(grade);
        return ResponseEntity.ok(studentList);
    }

    //17-idsi verilen studentın görüntüleme requestine response olarak DTO dönelim
    //http://localhost:8080/students/dto/1 + GET
    @GetMapping("/dto/{id}")
    public ResponseEntity<StudentDTO> getStudentDtoById(@PathVariable("id") Long id){
        StudentDTO studentDTO= studentService.getStudentDtoById(id);

        logger.warn("service ten StudentDTO objesi alindi: "+studentDTO.getName());

        return ResponseEntity.ok(studentDTO);
    }

    //19-
    @GetMapping("/welcome")
    public String welcome(HttpServletRequest request){//HttpServletRequest bu class gelen requestle ilgili bilgilere ulasmamizi saglar

        logger.info("welcome message {}",request.getServletPath());//{} degisken gelecek demek

        return "WELCOME :)";
    }


































}
