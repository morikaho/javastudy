package raisetech.studentManagement.date;

import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {

  private int id;
  private int studentId;
  private String course;
  private LocalDate startDate;
  private LocalDate expectedCompletionDate;
}
