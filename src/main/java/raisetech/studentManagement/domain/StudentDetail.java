package raisetech.studentManagement.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentCourse;

@Schema(description = "受講生詳細")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {

  @Valid
  private Student student;

  @Valid
  private List<StudentCourse> studentCourseList;

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }

    if (!(other instanceof StudentDetail)) {
      return false;
    }

    StudentDetail otherStudentDetail = (StudentDetail) other;
    return Objects.equals(this.student, otherStudentDetail.student) &&
        Objects.equals(this.studentCourseList, otherStudentDetail.studentCourseList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(student, studentCourseList);
  }
}