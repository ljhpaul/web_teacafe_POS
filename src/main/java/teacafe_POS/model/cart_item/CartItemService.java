package teacafe_POS.model.cart_item;

import java.util.List;

//Service: 비즈니스 로직을 수행, DB에 가는 업무는 DAO를 호출.
public class CartItemService {
	
	CartItemDAO DAO = new CartItemDAO();
	
	//1-1. selectAll
	public List<CartItemDTO> selectAll() {
		return DAO.selectAll();
	}
	
	//1-2. 특정 cart_id의 모든 장바구니 항목 조회
    public List<CartItemDTO> selectByCartId(int cart_id) {
    	return DAO.selectByCartId(cart_id);
    }
	
    //2. 장바구니 항목 추가
    public int insert(CartItemDTO dto) {
    	return DAO.insert(dto);
    }
    
    //3-1. 장바구니 update (수량, 온도, 단가 등)
    public int update(CartItemDTO dto) {
    	return DAO.update(dto);
    }
    
    //3-2. 장바구니 update (수량, 온도, 단가 등)
    public int updateAmountById(int amount, int cart_item_id) {
    	return DAO.updateAmountById(amount, cart_item_id);
    }
    
    //3-3. 장바구니 update (수량, 온도, 단가 등)
    public int updateTempById(String temp, int cart_item_id) {
    	return DAO.updateTempById(temp, cart_item_id);
    }
    
    //4-1. cart_item_id로 삭제
    public int deleteById(int cart_item_id) {
    	return DAO.deleteById(cart_item_id);
    }
    
    //4-2. cart_id로 전체 항목 삭제 (장바구니 전체 비우기)
    public int deleteByCartId(int cart_id) {
    	return DAO.deleteByCartId(cart_id);
    }
    
}
