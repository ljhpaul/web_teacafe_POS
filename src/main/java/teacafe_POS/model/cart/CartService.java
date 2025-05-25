package teacafe_POS.model.cart;

import java.util.List;

//Service: 비즈니스 로직을 수행, DB에 가는 업무는 DAO를 호출.
public class CartService {
	
	CartDAO DAO = new CartDAO();
	
	//1.selectAll
	public List<CartDTO> selectAll() {
		return DAO.selectAll();
	}
	
	//2-1.selectBySessionIdAndSeatNo
	public CartDTO selectBySessionIdAndSeatNo(String session_id, Integer seat_no) {
		return DAO.selectBySessionIdAndSeatNo(session_id, seat_no);
	}
	
	//2-2.selectByCartId
	public CartDTO selectByCartId(int cart_id) {
		return DAO.selectByCartId(cart_id);
	}
	
	//3-1.insertCart
	public int insertCart(CartDTO dto) {
		return DAO.insertCart(dto);
	}
	
	//3-2.insertCartAndReturnId
	public int insertCartAndReturnId(CartDTO dto) {
		return DAO.insertCartAndReturnId(dto);
	}
	
	//4-1.deleteBySessionIdAndSeatNo
	public int deleteBySessionIdAndSeatNo(String session_id, Integer seat_no) {
		return DAO.deleteBySessionIdAndSeatNo(session_id, seat_no);
	}
	
	//4-2.deleteCartByCartId
	public int deleteCartByCartId(int cart_id) {
		return DAO.deleteCartByCartId(cart_id);
	}
	
}
