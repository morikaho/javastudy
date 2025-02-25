package raisetech.studentManagement.date;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生")
@Getter
@Setter
public class Student {

  @Schema(description = "受講生ID", example = "1234")
  @NotBlank
  @Pattern(regexp = "^\\d+$")
  private String id;

  @Schema(description = "受講生の名前", example = "山田　太郎")
  @NotBlank
  private String fullName;

  @Schema(description = "受講生のふりがな", example = "やまだ　たろう")
  @NotBlank
  private String furigana;

  @Schema(description = "受講生のニックネーム", example = "たろう")
  @NotBlank
  private String nickname;

  @Schema(description = "受講生のメールアドレス", example = "example@example.com")
  @NotBlank
  @Email
  private String emailAddress;

  @Schema(description = "受講生の住んでいる地域（市区町村）", example = "東京都新宿区西新宿")
  @NotBlank
  private String area;

  @Schema(description = "受講生の年齢", example = "20")
  private int age;

  @Schema(description = "受講生の性別", example = "男")
  @NotBlank
  private String sex;

  @Schema(description = "備考", example = "特になし")
  private String remark;

  @Schema(description = "削除フラグ（true: 削除済み, false: 有効）", example = "false")
  private boolean isDeleted;
}
