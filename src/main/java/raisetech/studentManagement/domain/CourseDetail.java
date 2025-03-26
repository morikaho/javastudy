package raisetech.studentManagement.domain;

import java.time.LocalDate;
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
public class CourseDetail {

  private String studentId;

  private String courseId;

  private String course;

  private LocalDate startDate;

  private LocalDate expectedCompletionDate;

  private Integer applicationStatusId;

  private String applicationStatus;
}
