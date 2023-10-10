package adultdinosaurdooley.threesixnine.order.dto;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderAddressDTO {

    private String userGetName;
    private String userGetphoneNumber;
    private String userGetDetailAddress;
    private String userGetRoadAddress;
    private String userGetPostCode;
}

