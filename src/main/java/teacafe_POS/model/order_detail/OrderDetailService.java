package teacafe_POS.model.order_detail;

//Service: 비즈니스 로직을 수행, DB에 가는 업무는 DAO를 호출.
public class OrderDetailService {
	
	OrderDetailDAO DAO = new OrderDetailDAO();
	
	//1.insertOrderDetail
	public int insertOrderDetail(OrderDetailDTO dto) {
		return DAO.insertOrderDetail(dto);
	}
	
	//2.deleteAllOrderDetail
	public int deleteAllOrderDetail(int order_no) {
		return DAO.deleteAllOrderDetail(order_no);
	}
	
	//3.updateOrderAmount
	public int updateOrderAmount(int order_no, int amount) {
		return DAO.updateOrderAmount(order_no, amount);
	}
	
	//4.deleteOrderDetail
	public int deleteOrderDetail(int order_no) {
		return DAO.deleteOrderDetail(order_no);
	}
}
