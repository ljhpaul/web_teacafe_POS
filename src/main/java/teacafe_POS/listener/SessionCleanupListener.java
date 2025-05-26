package teacafe_POS.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import teacafe_POS.model.cart.CartDTO;
import teacafe_POS.model.cart.CartService;
import teacafe_POS.model.seat.SeatService;

@WebListener
public class SessionCleanupListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent se)  { 
        // 세션 생성 시 ...
    }

	public void sessionDestroyed(HttpSessionEvent se)  { 
		/* 주문이 완료되지 않은 상태에서 세션 소멸 시 주문 중이던 좌석을 다시 비움 */
		
		// 소멸 예정인 세션의 ID 확인
		HttpSession session = se.getSession();
		String session_id = session.getId();
		
		// 주문 완료 여부 확인
		Boolean ordered = (Boolean) session.getAttribute("ordered");
		
		// 주문이 완료된 경우 좌석 복구 안함 (사용중 유지)
//		if(ordered) return;
		if(Boolean.TRUE.equals(ordered)) return;
		
		// 주문이 완료되지 않은 경우 비정상종료
		// -> 세션ID를 통해 시용중이던 좌석번호를 확인 후 이를 복구
		CartService cartService = new CartService();
		CartDTO cart = cartService.selectBySessionIdAndSeatNo(session_id, null);
		if (cart == null) return;	// 예외 방지
		Integer seat_no = cart.getSeat_no();
		
		// 매장 이용인지 확인
		if(seat_no != null && seat_no > 0) {
			SeatService seatService = new SeatService();
			seatService.setSeated(seat_no, "N");
			System.out.println("[SessionCleanup] 세션 종료로 좌석 복구 수행: " + seat_no);
		}
    }
	
}
