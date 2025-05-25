package teacafe_POS.model.order_detail;

import java.util.List;

//Service: 비즈니스 로직을 수행, DB에 가는 업무는 DAO를 호출.
public class OrderDetailService {
	
	OrderDetailDAO DAO = new OrderDetailDAO();
	
	// 1. 여러 건 insert
    public int insert(List<OrderDetailDTO> list) {
		return DAO.insert(list);
	}
	
    //2-1.updateAmountById
  	public int updateAmountById(int order_amount, int order_no) {
		return DAO.updateAmountById(order_amount, order_no);
	}
	
  //2-2.updateTempById
  	public int updateTempById(String order_temp, int order_no) {
		return DAO.updateTempById(order_temp, order_no);
	}
	
  //3.deleteOrderDetail
  	public int deleteOrderDetail(int order_no) {
		return DAO.deleteOrderDetail(order_no);
	}
}
