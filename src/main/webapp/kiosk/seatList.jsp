<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/header.jsp" %>
<c:set var="currentSeatNo" value="${sessionScope.seat_no}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
  
</script>
<style>
  table, th, tb {
    border: 1px solid black;
    border-collapse: collapse;
    text-align: center;
  }
  th, td { padding: 3px; }
</style>
<script>
<script>
$(function() {
  	const currentSeatNo = "${currentSeatNo}";

  	$(".seat_form").on("submit", function(e) {
   		const seatNo = $(this).find(".seat_btn").data("seat");
   		const seated = $(this).find(".seat_btn").data("seated");
   		const reserved = $(this).find(".seat_btn").data("reserved");

    	// 1. 예약석이면 알림 후 중단
   	 	if (reserved === "Y") {
     		e.preventDefault();
      		alert("해당 좌석은 예약석입니다.");
      		return;
    	}

    	// 2. 사용중이면 알림 후 중단
    	if (seated === "Y") {
      		e.preventDefault();
      		alert("해당 좌석은 이미 사용 중입니다.");
      		return;
    	}

    	// 3. 현재 세션의 좌석을 다시 누른 경우 → 장바구니로 이동
    	if (seatNo == currentSeatNo) {
    		e.preventDefault();
      		location.href = "${cpath}/viewCart.do";
      		return;
    	}
    	
    	// 그 외 → 정상 submit 진행
  });
});
</script>
</head>
<body>
  <h1>좌석 선택</h1>
  <div>
    <table>
      <thead>
        <tr>
          <th>테이블</th>
          <th>사용여부</th>
        </tr>
      </thead>
      <tbody>
      <c:forEach items="${seatlist}" var="seat">
        <tr>
          <td>
            <form class="seat_form" method="post" action="${cpath}/seatList.do">
              <input type="hidden" name="seat_no" value="${seat.seat_no}">
              <button type="submit"
        			  class="seat_btn"
        			  data-seat="${seat.seat_no}"
        			  data-seated="${seat.seated}"
        			  data-reserved="${seat.reservation}">
  				${seat.seat_no}번
			  </button>
            </form>
          </td>
          <td>
            ${seat.getReservation().equals("Y")?"예약됨"
			:(seat.getSeated().equals("Y")?"사용중":"사용가능")}
		  </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</body>
</html>