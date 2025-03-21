package raisetech.studentManagement.date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StudentCourse {

  @Schema(description = "コースID", example = "1234")
  @Pattern(regexp = "^\\d+$",message = "数字のみ入力するようにしてください。")
  private String id;

  @Schema(description = "受講生のID", example = "1234")
  @Pattern(regexp = "^\\d+$",message = "数字のみ入力するようにしてください。")
  private String studentId;

  @Schema(description = "コース名", example = "JAVAコース")
  @NotBlank
  private String course;

  @Schema(description = "受講開始日", example = "2024-01-01")
  private LocalDate startDate;

  @Schema(description = "受講終了日", example = "2024-04-01")
  private LocalDate expectedCompletionDate;
}
