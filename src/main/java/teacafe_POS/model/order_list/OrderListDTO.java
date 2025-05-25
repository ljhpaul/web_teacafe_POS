package teacafe_POS.model.order_list;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderListDTO {
	private int order_id;
	private Integer seat_no;
	private int total_price;
	private Date order_date;
	private String pay_method;
	private String pay_status;
}
