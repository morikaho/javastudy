package raisetech.studentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import raisetech.studentManagement.domain.CourseDetail;
import raisetech.studentManagement.domain.StudentDetail;
import raisetech.studentManagement.domain.StudentRegistrationResult;
import raisetech.studentManagement.exception.TestException;
import raisetech.studentManagement.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@Validated
@RestController
public class StudentController {

  private StudentService service;

  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索です。 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生詳細一覧（全件）
   */
  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 例外を発生させます。
   */
  @GetMapping("/students")
  public List<StudentDetail> getStudents() throws TestException {
    throw new TestException(
        "現在このAPIは利用できません。URLは「students」ではなく「studentList」を利用してください。");
  }

  /**
   * 受講生詳細の検索です。 IDに紐づく任意の受講生情報を取得します。
   *
   * @param id 　受講生ID
   * @return 受講生
   */
  @Operation(summary = "詳細検索", description = "受講生詳細の検索です。 IDに紐づく任意の受講生情報を取得します。")
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(
      @Parameter(
          description = "受講生ID",
          required = true,
          example = "1234"
      )
      @PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id) {
    return service.searchStudent(id);
  }

  //受講生IDによる受講コース詳細情報の検索
  @GetMapping("/course/{id}")
  public List<CourseDetail> getCourses(@PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id){
    return service.searchCoursesByStudentId(id);
  }

  /**
   *
   * @param studentRegistrationResult
   * @return
   */
  @Operation(summary = "受講生登録", description = "受講生を登録します。")
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentRegistrationResult> registerStudent(
      @RequestBody StudentRegistrationResult studentRegistrationResult) {
    final StudentRegistrationResult responseStudentRegistrationResult = service.registerStudent(
        studentRegistrationResult);
    return ResponseEntity.ok(responseStudentRegistrationResult);
  }

  /**
   * 受講生詳細の更新を行います。キャンセルフラグの更新もここで行います。（論理削除）
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生更新", description = "受講生を更新します。")
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}