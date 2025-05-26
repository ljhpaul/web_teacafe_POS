package teacafe_POS.controller.menu;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import teacafe_POS.model.cart.CartService;
import teacafe_POS.model.cart_item.CartItemDTO;
import teacafe_POS.model.cart_item.CartItemService;
import teacafe_POS.model.menu.MenuDTO;
import teacafe_POS.model.menu.MenuService;

@WebServlet("/viewMenu.do")
public class ViewMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		// 1. 메뉴 전체 조회
		MenuService menuService = new MenuService();
		List<MenuDTO> menuList = menuService.selectAll();
		request.setAttribute("menuList", menuList);

		// 2. viewMenu.jsp로 포워딩
		request.getRequestDispatcher("/kiosk/viewMenu.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
	        throws ServletException, IOException {

	    request.setCharacterEncoding("UTF-8");

	    // 1. 세션에서 cart_id 가져오기
	    HttpSession session = request.getSession(false); // 이미 존재하는 세션만
	    if (session == null || session.getAttribute("cart_id") == null) {
	        // 세션이 없거나 cart_id가 없으면 좌석 선택 페이지로 리다이렉트
	        response.sendRedirect("seatList.do");
	        return;
	    }

	    int cart_id = (int) session.getAttribute("cart_id");

	    try {
	        // 2. 파라미터 수신
	        int menu_no = Integer.parseInt(request.getParameter("menu_no"));
	        String temp = request.getParameter("temp");
	        int amount = Integer.parseInt(request.getParameter("amount"));

	        // 3. 단가 조회 (menu 테이블에서 가져옴)
	        MenuService menuService = new MenuService();
	        MenuDTO menu = menuService.selectById(menu_no);
	        int unit_price = menu.getPrice();

	        // 4. DTO 생성
	        CartItemDTO dto = CartItemDTO.builder()
	                .cart_id(cart_id)
	                .menu_no(menu_no)
	                .unit_price(unit_price)
	                .temp(temp)
	                .amount(amount)
	                .build();

	        // 5. 장바구니 항목 추가
	        CartItemService cartItemService = new CartItemService();
	        cartItemService.insert(dto);

	        // 6. 장바구니 페이지로 이동
	        response.sendRedirect("viewCart.do");

	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	        // 잘못된 입력 처리 → 예외 페이지 또는 메뉴 선택 페이지로 되돌리기
	        response.sendRedirect("viewMenu.do");
	    }
	}


}
