package adultdinosaurdooley.threesixnine.users.service.exception;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserErrorResponse {
    private UserErrorCode errorCode;
    private String errorMessage;
}
