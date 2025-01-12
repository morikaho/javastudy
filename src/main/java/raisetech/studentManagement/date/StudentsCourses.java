package raisetech.studentManagement.date;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {

  private String id;
  private String studentId;
  private String course;
  private LocalDate startDate;
  private LocalDate expectedCompletionDate;
}
