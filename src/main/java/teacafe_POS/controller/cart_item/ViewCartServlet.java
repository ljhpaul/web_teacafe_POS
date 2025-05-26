package teacafe_POS.controller.cart_item;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
import teacafe_POS.model.seat.SeatService;

@WebServlet("/viewCart.do")
public class ViewCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
    	HttpSession session = request.getSession();
    	Integer cart_id = (Integer) session.getAttribute("cart_id");
    	
    	if (cart_id == null) {
    		response.sendRedirect("seatList.do");
    		return;
    	}
    	
    	CartService cartService = new CartService();
    	CartDTO cart = cartService.selectByCartId(cart_id);
    	
    	CartItemService cartItemService = new CartItemService();
    	List<CartItemDTO> itemlist = cartItemService.selectByCartId(cart_id);
    	
    	System.out.println(itemlist);

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
