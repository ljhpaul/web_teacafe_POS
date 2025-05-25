package teacafe_POS.model.order_detail;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderDetailDTO {
	private int order_detail_no;
	private int order_id;
	private Integer menu_no;
	private int unit_price;
	private String order_temp;
	private int order_amount;
}
