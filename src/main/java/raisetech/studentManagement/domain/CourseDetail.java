package raisetech.studentManagement.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.studentManagement.date.ApplicationStatus;
import raisetech.studentManagement.date.StudentCourse;

@Schema(description = "受講生コース詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CourseDetail {

  @Schema(description = "受講生のID", example = "1234")
  @Pattern(regexp = "^\\d+$", message = "数字のみ入力するようにしてください。")
  private String studentId;

  @Schema(description = "コースID", example = "1234")
  @Pattern(regexp = "^\\d+$", message = "数字のみ入力するようにしてください。")
  private String courseId;

  @Schema(description = "コース名", example = "JAVAコース")
  @NotBlank
  private String course;

  @Schema(description = "受講開始日", example = "2024-01-01")
  private LocalDate startDate;

  @Schema(description = "受講終了日", example = "2024-04-01")
  private LocalDate expectedCompletionDate;

  @Schema(description = "申込状況ID", example = "1234")
  private Integer applicationStatusId;

  @Schema(description = "コース申込状況", example = "仮申込")
  @NotBlank
  private String applicationStatus;

  /**
   * 受講生コース詳細から受講生コース情報を作成します。
   *
   * @return 受講生コース情報
   */
  public StudentCourse converterToStudentCourse() {
    return new StudentCourse(
        this.courseId,
        this.studentId,
        this.course,
        this.startDate,
        this.expectedCompletionDate
    );
  }

  /**
   * 受講生コース詳細から受講コース申込状況を作成します。
   *
   * @return 受講コース申込状況
   */
  public ApplicationStatus converterToApplicationStatus() {
    return new ApplicationStatus(
        this.applicationStatusId,
        this.courseId,
        this.applicationStatus
    );
  }

  /**
   * 受講生コース情報と受講コース申込状況から受講生コース詳細を作成します。
   *
   * @param studentCourse     受講生コース情報
   * @param applicationStatus 受講コース申込状況
   * @return 受講生コース詳細
   */
  public static CourseDetail of(StudentCourse studentCourse, ApplicationStatus applicationStatus) {
    return new CourseDetail(
        studentCourse.getStudentId(),
        studentCourse.getId(),
        studentCourse.getCourse(),
        studentCourse.getStartDate(),
        studentCourse.getExpectedCompletionDate(),
        applicationStatus.getId(),
        applicationStatus.getApplicationStatus()
    );
  }
}
