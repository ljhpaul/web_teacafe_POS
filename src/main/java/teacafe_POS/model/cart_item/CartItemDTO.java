package teacafe_POS.model.cart_item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CartItemDTO {
	private int cart_item_id;
	private Integer cart_id;
	private Integer menu_no;
	private Integer unit_price;
	private String temp;
	private Integer amount;
}
