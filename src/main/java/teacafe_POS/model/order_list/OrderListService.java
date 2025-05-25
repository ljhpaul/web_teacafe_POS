package teacafe_POS.model.order_list;

import java.util.List;

//Service: 비즈니스 로직을 수행, DB에 가는 업무는 DAO를 호출.
public class OrderListService {
	
	OrderListDAO DAO = new OrderListDAO();
	
	//1.selectAll
	public List<OrderListDTO> selectAll() {
		return DAO.selectAll();
	}
	
	//2. (선택) 단건 조회 by order_id
    public OrderListDTO selectById(int order_id) {
		return DAO.selectById(order_id);
	}
	
  //3. 주문 추가 (insert) → 생성된 order_id 반환
    public int insert(OrderListDTO dto) {
		return DAO.insert(dto);
	}
		
}
