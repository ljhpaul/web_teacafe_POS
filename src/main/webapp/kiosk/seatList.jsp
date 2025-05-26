<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/header.jsp" %>
<c:set var="currentSeatNo" value="${sessionScope.seat_no}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>좌석 선택</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style>
  body {
    min-height: 100vh;
    background: #f6f8fb;
    font-family: 'Noto Sans KR', Arial, sans-serif;
    margin: 0;
    display: flex;
    justify-content: center;
    align-items: flex-start;
  }
  .seat-container {
    background: #fff;
    border-radius: 1.2rem;
    box-shadow: 0 4px 24px 0 rgba(0,0,0,0.07);
    padding: 2.2rem 1.2rem 2rem 1.2rem;
    margin-top: 2.5rem;
    min-width: 280px;
    max-width: 560px;
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
  }
  h1 {
    font-size: 2.1rem;
    font-weight: 800;
    color: #263053;
    margin-bottom: 2rem;
    margin-top: 0;
    text-align: center;
    letter-spacing: -1px;
  }
  .seat-grid {
    display: flex;
    flex-wrap: wrap;
    gap: 1.2rem;
    justify-content: center;
    align-items: flex-start;
    width: 100%;
    margin: 0 auto;
  }
  .seat-form { margin: 0; }

  /* 카드 공통 스타일 */
  .seat-card {
    flex: 1 1 110px;
    max-width: 120px;
    min-width: 100px;
    aspect-ratio: 1 / 1;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    background: #f9f8f4;
    border-radius: 1.1rem;
    box-shadow: 0 2px 10px 0 rgba(210,210,210,0.10);
    border: 1.5px solid #eae4d9;
    position: relative;
    transition: 
      background 0.26s cubic-bezier(.4,2,.4,1),
      box-shadow 0.24s cubic-bezier(.4,2,.4,1),
      border-color 0.18s;
  }

  /* 카드 hover/focus 효과: 예약/사용중/선택 제외 */
  .seat-card:not(.reserved):not(.seated):not(.selected):hover,
  .seat-card:not(.reserved):not(.seated):not(.selected):focus-within {
    background: linear-gradient(120deg, #fdf4d6 85%, #f6eee1 100%);
    box-shadow: 0 0 16px 6px #f7e6bc33;
    border-color: #fae2ad;
    z-index: 1;
  }

  /* 주문 중(세션 점유) 카드 */
  .seat-card.selected {
    background: linear-gradient(120deg, #fff6ca 65%, #fff2a5 100%);
    border-color: #ffe082;
    box-shadow: 0 2px 10px 0 rgba(255,224,130,0.13);
    transition: background 0.23s, border-color 0.17s, box-shadow 0.2s;
  }
  .seat-card.selected:hover,
  .seat-card.selected:focus-within {
    background: linear-gradient(120deg, #fffbe2 90%, #fff7c5 100%);
    border-color: #ffed97;
    box-shadow: 0 0 16px 6px #ffe08233;
    z-index: 1;
  }

  /* 예약석 */
  .seat-card.reserved {
    background: linear-gradient(120deg, #f8dada 70%, #f9eded 100%);
    border-color: #f8bbbb;
  }
  /* 사용중 */
  .seat-card.seated:not(.reserved):not(.selected) {
    background: linear-gradient(120deg, #dde5ef 70%, #f2f6fa 100%);
    border-color: #b8c9e0;
  }

  .seat-btn {
    width: 100%;
    height: 100%;
    background: none;
    border: none;
    padding: 0;
    cursor: pointer;
    outline: none;
    font-size: 1.27rem;
    font-weight: 700;
    color: #6c6250;
    border-radius: 1.1rem;
    transition: 
      background 0.18s, 
      color 0.13s, 
      transform 0.15s;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  /* 주문 중(세션 점유) 버튼 */
  .seat-card.selected .seat-btn {
    color: #b38a05;
    background: none;
    cursor: pointer;
    opacity: 1;
  }
  .seat-card.selected .seat-btn:hover,
  .seat-card.selected .seat-btn:focus {
    color: #b38a05;
    background: none;
    transform: scale(1.07);
  }

  /* 예약/사용중 버튼 */
  .seat-btn.reserved {
    color: #c25a5a;
    cursor: not-allowed;
    opacity: 0.7;
  }
  .seat-btn.seated {
    color: #5671a4;
    cursor: not-allowed;
    opacity: 0.82;
  }

  /* 일반 버튼 hover는 카드에서 처리하므로 색 변화 최소화 */
  .seat-btn:hover:not(.reserved):not(.seated):not(.selected),
  .seat-btn:focus:not(.reserved):not(.seated):not(.selected) {
    color: #a98729;
    transform: scale(1.07);
  }

  .seat-status {
    font-size: 0.99rem;
    font-weight: 600;
    border-radius: 0.8rem;
    padding: 0.36rem 0.9rem;
    display: inline-block;
    margin-top: 0.37rem;
    margin-bottom: 0.07rem;
  }
  .seat-status.selected {
    background: #fffbe2;
    color: #b38a05;
    border: 1px solid #ffe082;
  }
  .seat-status.reserved {
    background: #fbe9e9;
    color: #d47d7d;
    border: 1px solid #f6c9c9;
  }
  .seat-status.seated {
    background: #e4edf8;
    color: #7a97c1;
    border: 1px solid #b1c9e7;
  }
  .seat-status.available {
    background: #eafae8;
    color: #599c5c;
    border: 1px solid #a9e7b6;
  }
  @media (max-width: 700px) {
    .seat-container {
      padding: 1rem 0.3rem 1.1rem 0.3rem;
      border-radius: 0.9rem;
      margin-top: 0.7rem;
      min-width: 0;
      max-width: 99vw;
    }
    h1 {
      font-size: 1.18rem;
      margin-bottom: 1.13rem;
    }
    .seat-grid {
      gap: 0.6rem;
    }
    .seat-card {
      min-width: 72px;
      max-width: 88px;
      border-radius: 0.7rem;
    }
    .seat-btn {
      font-size: 0.99rem;
      border-radius: 0.7rem;
    }
    .seat-status {
      font-size: 0.82rem;
      padding: 0.21rem 0.5rem;
    }
  }
</style>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
$(function() {
  	const currentSeatNo = "${currentSeatNo}";

  	$(".seat-btn").on("click", function(e) {
   		const seatNo = $(this).data("seat");
   		const seated = $(this).data("seated");
   		const reserved = $(this).data("reserved");
   		const isCurrentSeat = seatNo == currentSeatNo;

        // 1. 현재 세션 좌석 클릭 시 → 메뉴선택(viewMenu.do) 이동이 무조건 우선
        if (isCurrentSeat) {
            e.preventDefault();
            location.href = "${cpath}/viewCart.do?seat_no=" + seatNo;
            return false;
        }

    	// 2. 예약석이면 알림 후 중단
   	 	if (reserved === "Y") {
     		e.preventDefault();
      		alert("해당 좌석은 예약석입니다.");
      		return false;
    	}

    	// 3. 사용중이면 알림 후 중단
    	if (seated === "Y") {
      		e.preventDefault();
      		alert("해당 좌석은 이미 사용 중입니다.");
      		return false;
    	}

    	// 4. 그 외 좌석은 폼 제출(좌석 변경)
    	$(this).closest("form").submit();
  	});
});
</script>
</head>
<body>
  <div class="seat-container">
    <h1>좌석 선택</h1>
    <div class="seat-grid">
      <c:forEach items="${seatlist}" var="seat">
        <form class="seat-form" method="post" action="${cpath}/seatList.do">
          <div class="seat-card
              ${seat.reservation == 'Y' ? 'reserved' : ''}
              ${seat.seated == 'Y' && seat.reservation != 'Y' ? 'seated' : ''}
              ${seat.seat_no == currentSeatNo ? 'selected' : ''}">
            <button type="button"
        			  class="seat-btn
          			  ${seat.reservation == 'Y' ? 'reserved' : ''}
          			  ${seat.seated == 'Y' && seat.reservation != 'Y' ? 'seated' : ''}
          			  ${seat.seat_no == currentSeatNo ? 'selected' : ''}"
        			  data-seat="${seat.seat_no}"
        			  data-seated="${seat.seated}"
        			  data-reserved="${seat.reservation}">
  				${seat.seat_no}번
			  </button>
            <div>
              <c:choose>
                <c:when test="${seat.seat_no == currentSeatNo}">
                  <span class="seat-status selected">주문 중</span>
                </c:when>
                <c:when test="${seat.reservation == 'Y'}">
                  <span class="seat-status reserved">예약됨</span>
                </c:when>
                <c:when test="${seat.seated == 'Y'}">
                  <span class="seat-status seated">사용 중</span>
                </c:when>
                <c:otherwise>
                  <span class="seat-status available">사용가능</span>
                </c:otherwise>
              </c:choose>
            </div>
            <input type="hidden" name="seat_no" value="${seat.seat_no}">
          </div>
        </form>
      </c:forEach>
    </div>
  </div>
</body>
</html>
