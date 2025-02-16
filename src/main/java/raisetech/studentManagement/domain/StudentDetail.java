package raisetech.studentManagement.domain;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentsCourses;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  private Student student;
  private List<StudentsCourses> studentsCourses;
}
