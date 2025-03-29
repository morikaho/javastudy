package raisetech.studentManagement.date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ApplicationStatus {

  private Integer id;

  @Pattern(regexp = "^\\d+$",message = "数字のみ入力するようにしてください。")
  private String courseId;

  @NotBlank
  private String applicationStatus;
}
