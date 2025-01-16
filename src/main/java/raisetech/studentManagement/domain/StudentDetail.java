package raisetech.studentManagement.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentsCourses;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentsCourses> studentsCourses;
}
