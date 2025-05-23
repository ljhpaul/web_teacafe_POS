package teacafe_POS.controller.seat;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import teacafe_POS.model.seat.SeatDTO;
import teacafe_POS.model.seat.SeatService;

@WebServlet("/seatList.do")
public class SeatListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		SeatService seatService = new SeatService();
		List<SeatDTO> seatlist = seatService.selectAll();
		
		System.out.println(seatlist);
		
		request.setAttribute("seatlist", seatlist);
		
		request.getRequestDispatcher("/kiosk/seatList.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
