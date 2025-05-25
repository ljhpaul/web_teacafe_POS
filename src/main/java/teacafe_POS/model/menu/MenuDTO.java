package teacafe_POS.model.menu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class MenuDTO {
	private int menu_no;
	private String menu_name;
	private int price;
	private Integer category_id;
	private String temp;
	private String sold_out;
	
	//카테고리명 추가 조회시 사용
	private String category_name;
}
