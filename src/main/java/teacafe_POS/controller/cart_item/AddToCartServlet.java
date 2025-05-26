package teacafe_POS.controller.cart_item;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import teacafe_POS.model.cart.CartService;
import teacafe_POS.model.cart_item.CartItemDTO;
import teacafe_POS.model.menu.MenuDTO;
import teacafe_POS.model.menu.MenuService;

@WebServlet("/addToCart.do")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");

		// 1. 파라미터 수신
		int cart_id = Integer.parseInt(request.getParameter("cart_id"));
		int menu_no = Integer.parseInt(request.getParameter("menu_no"));
		String temp = request.getParameter("temp");
		int amount = Integer.parseInt(request.getParameter("amount"));

		// 2. 단가 조회 (menu 테이블에서 가져옴)
		MenuService menuService = new MenuService();
		MenuDTO menu = menuService.selectById(menu_no);
		int unit_price = menu.getPrice();

		// 3. DTO 생성
		CartItemDTO dto = CartItemDTO.builder()
				.cart_id(cart_id)
				.menu_no(menu_no)
				.unit_price(unit_price)
				.temp(temp)
				.amount(amount)
				.build();

		// 4. cart_id로 seat_no 조회
		CartService cartService = new CartService();
		Integer seat_no = cartService.selectByCartId(cart_id).getSeat_no();  // null 가능성 고려

		// 5. 리다이렉트
		String redirectURL = "viewCart.do";
		if (seat_no != null) {
			redirectURL += "?seat_no=" + seat_no;
		}
		response.sendRedirect(redirectURL);
	}
}