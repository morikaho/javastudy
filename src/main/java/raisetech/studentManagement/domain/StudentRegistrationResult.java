package raisetech.studentManagement.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.studentManagement.date.ApplicationStatus;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentCourse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StudentRegistrationResult {

  private Student student;

  private List<StudentCourse> studentCourseList;

  private List<ApplicationStatus> applicationStatusList;
}
