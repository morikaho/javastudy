package raisetech.studentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.studentManagement.date.ApplicationStatus;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentCourse;
import raisetech.studentManagement.domain.CourseDetail;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  /**
   * 受講生の全件検索を行います。
   *
   * @return 受講生一覧（全件）
   */
  List<Student> search();

  /**
   * 受講生の検索を行います。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  Student searchStudent(String id);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生のコース情報（全件）
   */
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> searchStudentCourseByStudentId(String studentId);

  /**
   * 受講生IDに紐づく受講生コース詳細を検索します。
   *
   * @param studentId 　受講生ID
   * @return 受講生IDに紐づく受講生コース詳細
   */
  List<CourseDetail> searchCourseDetailsByStudentId(String studentId);

  /**
   * 受講生を新規登録します。　IDに関しては自動採番を行います。
   *
   * @param student 受講生
   */
  void insertStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。IDに関しては自動採番を行います。
   *
   * @param studentCourse 受講生コース情報
   */
  void insertStudentCourse(StudentCourse studentCourse);

  /**
   * 受講コース申込状況を新規登録します。IDに関しては自動採番を行います。
   *
   * @param applicationStatus 受講コース申込状況
   */
  void insertApplicationStatus(ApplicationStatus applicationStatus);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報を更新します。
   *
   * @param studentCourse 　受講生コース情報
   */
  void updateStudentCourse(StudentCourse studentCourse);

  /**
   * 受講コース申込状況を更新します。
   *
   * @param applicationStatus 受講コース申込状況
   */
  void updateApplicationStatus(ApplicationStatus applicationStatus);

  /**
   * 受講生コースIDに紐づく受講生コース情報を削除します。
   *
   * @param courseId 受講生コースID
   */
  void deleteStudentCourse(String courseId);

  /**
   * 受講生コースIDに紐づく受講コース申込状況を削除します。
   *
   * @param courseId 受講生コースID
   */
  void deleteApplicationStatus(String courseId);
}