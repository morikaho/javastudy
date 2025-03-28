package raisetech.studentManagement.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentCourse;
import raisetech.studentManagement.domain.StudentDetail;
import raisetech.studentManagement.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること() throws Exception {
    mockMvc.perform(get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の検索ができてIDに紐づく受講生詳細が返ってくること() throws Exception {
    String id = "100";
    Student student = new Student(id, "渡辺　恵子", "わたなべ　けいこ", "けいこ",
        "unique.user1937@example.com", "東京都", 30, "女", "特になし", false);
    StudentCourse studentCourse = new StudentCourse("100", id, "JAVAコース",
        LocalDate.parse("2024-01-01"), LocalDate.parse("2024-04-01"));
    StudentDetail studentDetail = new StudentDetail(student, List.of(studentCourse));

    when(service.searchStudent(id)).thenReturn(studentDetail);

    mockMvc.perform(get("/student/{id}", id))
        .andExpect(status().isOk())
        .andExpect(content().json("""
            {
                "student": {
                    "id": "100",
                    "fullName": "渡辺　恵子",
                    "furigana": "わたなべ　けいこ",
                    "nickname": "けいこ",
                    "emailAddress": "unique.user1937@example.com",
                    "area": "東京都",
                    "age": 30,
                    "sex": "女",
                    "remark": "特になし",
                    "deleted": false
                },
                "studentCourseList": [
                    {
                        "id": "100",
                        "studentId": "100",
                        "course": "JAVAコース",
                        "startDate": "2024-01-01",
                        "expectedCompletionDate": "2024-04-01"
                    }
                ]
            }
            """));

    verify(service, times(1)).searchStudent(id);
  }

  @Test
  void 受講生コース詳細の検索ができて空で返ってくること() throws Exception {
    String id = "100";
    mockMvc.perform(get("/course/{id}", id))
        .andExpect(status().isOk());

    verify(service, times(1)).searchCoursesByStudentId(id);
  }


  @Test
  void 受講生詳細の登録ができて空の受講生詳細が返ってくること() throws Exception {
    //リクエストデータは適切に構築して入力チェックの検証も兼ねている。
    //本来であれば返りは登録されたデータが入るが、モック化すると意味がないため、レスポンスは作らない。
    mockMvc.perform(post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                 {
                     "student":{
                         "fullName": "渡辺　恵子",
                         "furigana": "わたなべ　けいこ",
                         "nickname": "けいこ",
                         "emailAddress": "unique.user1937@example.com",
                         "area": "東京都",
                         "age": 30,
                         "sex": "女",
                         "remark": "特になし"
                     },
                     "courseDetail":{
                         "course": "JAVAコース",
                         "startDate": "2025-01-01",
                         "expectedCompletionDate": "2026-01-01",
                         "applicationStatus":"仮申込"
                     }
                 }
                """))
        .andExpect(status().isOk());

    verify(service, times(1)).registerStudent(any());
  }

  @Test
  void 受講コース詳細の登録ができて空の受講コース詳細が返ってくること() throws Exception {
    mockMvc.perform(post("/registerCourse")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "studentId": "100",
                    "course": "JAVAコース",
                    "startDate": "2025-01-01",
                    "expectedCompletionDate": "2026-01-01",
                    "applicationStatus": "仮申込"
                }
                """))
        .andExpect(status().isOk());

    verify(service, times(1)).registerCourse(any());
  }

  @Test
  void 受講生の更新ができて適切なメッセージが返ってくること() throws Exception {
    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "id": "100",
                    "fullName": "渡辺 恵子",
                    "furigana": "わたなべ けいこ",
                    "nickname": "けいこ",
                    "emailAddress": "unique.user1937@example.com",
                    "area": "東京都",
                    "age": 30,
                    "sex": "女"
                }
                """))
        .andExpect(status().isOk())
        .andExpect(content().string("更新処理が成功しました。"));

    verify(service, times(1)).updateStudent(any());
  }

  @Test
  void 受講コース詳細の更新ができて適切なメッセージが返ってくること() throws Exception {
    mockMvc.perform(put("/updateCourse")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
                {
                    "studentId": "100",
                    "courseId": "1",
                    "course": "JAVAコース",
                    "startDate": "2024-01-01",
                    "expectedCompletionDate": "2025-01-01",
                    "applicationStatusId": 1,
                    "applicationStatus": "受講終了"
                }
                """))
        .andExpect(status().isOk())
        .andExpect(content().string("更新処理が成功しました。"));

    verify(service, times(1)).updateCourse(any());
  }

  @Test
  void 受講生コース詳細の削除ができて適切なメッセージが返ってくること() throws Exception {
    String courseId = "100";
    mockMvc.perform(delete("/deleteCourse/{courseId}", courseId))
        .andExpect(status().isOk())
        .andExpect(content().string("削除処理が成功しました。"));

    verify(service, times(1)).deleteCourse(courseId);
  }


  @Test
  void studentsで検索をした際適切なエラーメッセージが返ってくること() throws Exception {
    mockMvc.perform(get("/students"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.status").value(400))
        .andExpect(jsonPath("$.error").value("Bad Request"))
        .andExpect(jsonPath("$.message").value(
            "現在このAPIは利用できません。URLは「students」ではなく「studentList」を利用してください。"))
        .andExpect(jsonPath("$.path").value("/students"));
  }

  @Test
  void 受講生詳細の受講生で適切な値を入力した時に入力チェックに異常が発生しないこと() {
    Student student = new Student();
    student.setId("100");
    student.setFullName("渡辺　恵子");
    student.setFurigana("わたなべ　けいこ");
    student.setNickname("けいこ");
    student.setEmailAddress("unique.user1937@example.com");
    student.setArea("東京都新宿区新宿");
    student.setAge(32);
    student.setSex("女");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いた時に入力チェックにかかること() {
    Student student = new Student();
    student.setId("テストです。");
    student.setFullName("渡辺　恵子");
    student.setFurigana("わたなべ　けいこ");
    student.setNickname("けいこ");
    student.setEmailAddress("unique.user1937@example.com");
    student.setArea("東京都新宿区新宿");
    student.setAge(32);
    student.setSex("女");

    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("数字のみ入力するようにしてください。");
  }

  @Test
  void 受講生詳細の受講生コース情報で適切な値を入力した時に入力チェックに異常が発生しないこと() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("100");
    studentCourse.setStudentId("100");
    studentCourse.setCourse("JAVAコース");
    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生コース情報で受講生IDとコースIDに数字以外を用いた時に入力チェックにかかること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("テストです。");
    studentCourse.setStudentId("テストです。");
    studentCourse.setCourse("JAVAコース");
    Set<ConstraintViolation<StudentCourse>> violations = validator.validate(studentCourse);

    assertThat(violations.size()).isEqualTo(2);
    assertThat(violations).extracting(v -> v.getPropertyPath().toString(),
            ConstraintViolation::getMessage)
        .containsOnly(
            tuple("id", "数字のみ入力するようにしてください。"),
            tuple("studentId", "数字のみ入力するようにしてください。")
        );
  }
}