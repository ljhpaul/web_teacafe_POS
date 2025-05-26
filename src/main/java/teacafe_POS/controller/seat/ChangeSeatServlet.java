package teacafe_POS.controller.seat;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import teacafe_POS.model.cart.CartService;
import teacafe_POS.model.seat.SeatService;

@WebServlet("/changeSeat.do")
public class ChangeSeatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String session_id = request.getSession().getId();
		Integer oldSeat = Integer.parseInt(request.getParameter("old_seat_no"));
		Integer newSeat = Integer.parseInt(request.getParameter("new_seat_no"));

		CartService cartService = new CartService();
		SeatService seatService = new SeatService();

		// 1. cart의 seat_no 변경
		cartService.updateSeatNo(session_id, newSeat);
		session.setAttribute("seat_no", newSeat);

		// 2. 좌석 상태 변경
		seatService.setSeated(oldSeat, "N");
		seatService.setSeated(newSeat, "Y");

		// 3. 장바구니로 이동
		response.sendRedirect("viewCart.do");
	}
}
