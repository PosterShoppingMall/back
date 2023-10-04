package adultdinosaurdooley.threesixnine.users.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMyPage {

    private String userName;
    private String password;
    private String address;
    private String addressDetail;
    private String phoneNumber;
}
