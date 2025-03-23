package raisetech.studentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import raisetech.studentManagement.date.Student;
import raisetech.studentManagement.date.StudentCourse;

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
   * 条件を指定して受講生情報を検索します。受講生名、メールアドレス、住所は部分一致で検索します。
   *
   * @param id           　受講生ID
   * @param fullName     　受講生名
   * @param furigana     　ふりがな
   * @param nickname     　ニックネーム
   * @param emailAddress 　メールアドレス
   * @param area         　住所
   * @param age          　年齢
   * @param sex          　性別
   * @return 指定した条件に紐づく受講生一覧
   */
  List<Student> searchStudentsByCondition(
      @Param("id") String id,
      @Param("fullName") String fullName,
      @Param("furigana") String furigana,
      @Param("nickname") String nickname,
      @Param("emailAddress") String emailAddress,
      @Param("area") String area,
      @Param("age") Integer age,
      @Param("sex") String sex);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生のコース情報（全件）
   */
  List<StudentCourse> searchStudentCourseList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentId 　受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> searchStudentCourse(String studentId);

  /**
   * 複数の受講生IDに紐づく受講生コース情報を検索します。
   *
   * @param studentIds　受講生ID
   * @return 複数の受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> searchStudentCoursesByStudentIds(@Param("list") List<String> studentIds);

  /**
   * 受講生を新規登録します。　IDに関しては自動採番を行う。
   *
   * @param student 受講生
   */
  void insertStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。　IDに関しては自動採番を行う。
   *
   * @param studentCourse 受講生コース情報
   */
  void insertStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生を更新します。
   *
   * @param student 受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   *
   * @param studentCourse 受講生コース情報
   */
  void updateStudentCourse(StudentCourse studentCourse);
}


