package raisetech.studentManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  @Autowired
  private StudentRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  //生徒情報を一覧で表示する
  @GetMapping("/studentInfo")
  public List<String> getStudentInfo() {
    List<Student> studentList = repository.studentInfo();
    return studentList.stream()
        .map(Student::toString)
        .toList();
  }

  //生徒情報を検索
  @GetMapping("/student")
  public String getStudent(@RequestParam String name) {
    Student student = repository.searchByName(name);
    return student.getName() + " " + student.getAge() + "歳";
  }

  //生徒情報を登録
  @PostMapping("/student")
  public void registerStudent(String name, int age) {
    repository.registerStudent(name, age);

  }

  //名前を指定して年齢を更新
  @PatchMapping("/student")
  public void updateStudent(String name, int age) {
    repository.updateStudent(name, age);
  }

  //名前を指定して削除
  @DeleteMapping("/student")
  public void deleteStudent(String name) {
    repository.deleteStudent(name);
  }
}
