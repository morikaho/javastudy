package raisetech.studentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import raisetech.studentManagement.domain.StudentDetail;
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
  @Operation(summary = "IDによる詳細検索", description = "受講生詳細の検索です。 IDに紐づく任意の受講生情報を取得します。")
  @GetMapping("/student/{id}")
  public StudentDetail getStudentById(
      @Parameter(
          description = "受講生ID",
          required = true,
          example = "1234"
      )
      @PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id) {
    return service.searchStudent(id);
  }

  /**
   * 受講生詳細の検索です。 指定した条件に紐づく任意の受講生情報を取得します。
   *
   * @param id           　受講生ID
   * @param fullName     　受講生名
   * @param furigana     　ふりがな
   * @param nickname     　ニックネーム
   * @param emailAddress 　メールアドレス
   * @param area         　住所
   * @param age          　年齢
   * @param sex          　性別
   * @return 条件に紐づく受講生詳細一覧
   */
  @Operation(summary = "指定した条件の詳細検索", description = "受講生詳細の検索です。指定した条件に紐づく任意の受講生情報を取得します。")
  @GetMapping("/students/filter")
  public List<StudentDetail> getStudentsByCondition(
      @Parameter(
          description = "受講生ID",
          example = "1234"
      )
      @RequestParam(required = false) @Pattern(regexp = "^\\d+$") String id,

      @Parameter(
          description = "受講生名",
          example = "渡辺 恵子"
      )
      @RequestParam(required = false) String fullName,

      @Parameter(
          description = "ふりがな",
          example = "わたなべ けいこ"
      )
      @RequestParam(required = false) String furigana,

      @Parameter(
          description = "ニックネーム",
          example = "けいちゃん"
      )
      @RequestParam(required = false) String nickname,

      @Parameter(
          description = "メールアドレス",
          example = "example@example.com"
      )
      @RequestParam(required = false) String emailAddress,

      @Parameter(
          description = "住所",
          example = "東京都新宿区"
      )
      @RequestParam(required = false) String area,

      @Parameter(
          description = "年齢",
          example = "30"
      )
      @RequestParam(required = false) Integer age,

      @Parameter(
          description = "性別",
          example = "女"
      )
      @RequestParam(required = false) String sex
  ) {
    return service.searchStudentsByCondition(id, fullName, furigana, nickname, emailAddress, area,
        age, sex);
  }

  /**
   * 受講生詳細の登録を行います。
   *
   * @param studentDetail 　受講生詳細
   * @return 実行結果
   */
  @Operation(summary = "受講生登録", description = "受講生を登録します。")
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(
      @RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
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