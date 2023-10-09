package adultdinosaurdooley.threesixnine.product.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class StockDTO {
    private int stockAmount;
    private int sellAmount; //판매수량
    @Builder
    public StockDTO(int stockAmount, int sellAmount){
        this.stockAmount = stockAmount;
        this.sellAmount = sellAmount;
    }
}