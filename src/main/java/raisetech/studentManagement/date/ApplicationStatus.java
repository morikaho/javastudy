package raisetech.studentManagement.date;

import io.swagger.v3.oas.models.security.SecurityScheme.In;
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
public class ApplicationStatus {

  private Integer id;

  private String courseId;

  private String applicationStatus;
}
