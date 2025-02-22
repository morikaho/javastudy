package raisetech.studentManagement.date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

  @NotBlank
  @Pattern(regexp = "^\\d+$")
  private int id;

  @NotBlank
  @Pattern(regexp = "^\\d+$")
  private int studentId;

  @NotBlank
  private String course;

  private LocalDate startDate;
  private LocalDate expectedCompletionDate;
}
