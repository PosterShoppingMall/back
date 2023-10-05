package adultdinosaurdooley.threesixnine.users.service.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserException extends RuntimeException {

    private UserErrorCode errorCode;
    private String errorMessage;

    public UserException(UserErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorMessage = errorCode.getDescription();
    }
}
