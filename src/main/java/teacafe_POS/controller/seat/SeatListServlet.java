package teacafe_POS.controller.seat;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import teacafe_POS.model.cart.CartDTO;
import teacafe_POS.model.cart.CartService;
import teacafe_POS.model.seat.SeatDTO;
import teacafe_POS.model.seat.SeatService;

@WebServlet("/seatList.do")
public class SeatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		SeatService seatService = new SeatService();
		List<SeatDTO> seatlist = seatService.selectAll();
		
		System.out.println(seatlist);
		
		request.setAttribute("seatlist", seatlist);
		
		request.getRequestDispatcher("/kiosk/seatList.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		// 세션 객체
		HttpSession session = request.getSession();
		String session_id = session.getId();

		// 1. 사용자가 선택한 seat_no
		Integer new_seat_no = Integer.parseInt(request.getParameter("seat_no"));

		// 2. 세션에 저장된 기존 cart 상태 확인
		Integer current_seat_no = (Integer) session.getAttribute("seat_no");
		Integer cart_id = (Integer) session.getAttribute("cart_id");

		// 3. 동일한 좌석을 다시 선택한 경우 → 그대로 viewCart로 이동
		if (cart_id != null && current_seat_no != null && current_seat_no.equals(new_seat_no)) {
			response.sendRedirect("viewCart.do");
			return;
		}

		// 4. 다른 좌석 선택 시 → 변경 여부 확인 페이지로 이동
		if (cart_id != null && current_seat_no != null && !current_seat_no.equals(new_seat_no)) {
			request.setAttribute("old_seat_no", current_seat_no);
			request.setAttribute("new_seat_no", new_seat_no);
			request.getRequestDispatcher("/kiosk/confirmSeatChange.jsp").forward(request, response);
			return;
		}

		// 5. 새로운 장바구니 생성
		CartDTO newCart = CartDTO.builder()
				.session_id(session_id)
				.seat_no(new_seat_no)
				.build();

		CartService cartService = new CartService();
		int new_cart_id = cartService.insertCartAndReturnId(newCart);

		// 6. 좌석을 임시 사용중 처리
		new SeatService().setSeated(new_seat_no, "Y");

		// 7. 세션에 cart 상태 저장
		session.setAttribute("cart_id", new_cart_id);
		session.setAttribute("seat_no", new_seat_no);

		// 8. 장바구니 페이지로 이동
		response.sendRedirect("viewCart.do");
	}
}
