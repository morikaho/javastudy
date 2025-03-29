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

@Schema(description = "受講生コース詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CourseDetail {

  @Schema(description = "受講生のID", example = "1234")
  @Pattern(regexp = "^\\d+$",message = "数字のみ入力するようにしてください。")
  private String studentId;

  @Schema(description = "コースID", example = "1234")
  @Pattern(regexp = "^\\d+$",message = "数字のみ入力するようにしてください。")
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
}
