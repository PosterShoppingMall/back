package adultdinosaurdooley.threesixnine.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StockDTO {
    private int stockAmount;
    private int sellAmount;

    @Builder
    public StockDTO(int stockAmount, int sellAmount) {
        this.stockAmount = stockAmount;
        this.sellAmount = sellAmount;
    }
}