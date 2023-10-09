package adultdinosaurdooley.threesixnine.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginTokenSaveDTO {
    private Long id;
    private String email;
}
