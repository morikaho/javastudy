package raisetech.studentManagement;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagementApplication {

  private static Map<String, String> student = new HashMap<>();

  static {
    student.put("小林","33");
  }

  public static void main(String[] args) {
    SpringApplication.run(StudentManagementApplication.class, args);
  }

  //生徒情報を確認
  @GetMapping("/studentInfo")
  public Map<String, String> getStudentInfo() {
    return student;
  }

  //生徒情報を登録
  @PostMapping("/studentInfo")
  public void setStudentInfo(String name, String age) {
    student.put(name, age);
  }

  //年齢の更新
  @PostMapping("/studentAge")
  public void updateAge(String name, String age) {
    student.replace(name, age);
  }

}
