package teacafe_POS.controller.cart;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import teacafe_POS.model.cart.CartDTO;
import teacafe_POS.model.cart.CartService;
import teacafe_POS.model.cart_item.CartItemDTO;
import teacafe_POS.model.cart_item.CartItemService;

@WebServlet("/viewCart.do")
public class ViewCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	//세션ID 확인
    	HttpSession session = (HttpSession) request.getSession();
    	String session_id = session.getId();
    	
    	//파라미터값 확인(포장이면 seat_no = 0)
    	Integer seat_no = convertInteger( request.getParameter("seat_no") );
		
		//cart 조회 및 생성
    	CartService cartService = new CartService();
    	CartDTO cart = cartService.selectBySessionIdAndSeatNo(session_id, seat_no);
    	int cart_id = -1;
    	
    	if(cart == null) {
    		CartDTO newCart = CartDTO.builder()
    				.session_id(session_id)
    				.seat_no(seat_no)
    				.build();
    		
    		cart_id = cartService.insertCartAndReturnId(newCart);
    		cart = cartService.selectByCartId(cart_id);
    	}
    	
    	//cart_item 조회
    	CartItemService cartItemService = new CartItemService();
    	List<CartItemDTO> itemlist = cartItemService.selectByCartId(cart_id);
    	
    	//viewCart.jsp로 전달
    	request.setAttribute("cart", cart);
		request.setAttribute("itemlist", itemlist);
		request.getRequestDispatcher("/kiosk/viewCart.jsp").forward(request, response);
	}
    
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
	

	private Integer convertInteger(String parameter) {
		if(parameter == null || parameter.equals("")) return null;
		return Integer.parseInt(parameter);
	}
	
}
