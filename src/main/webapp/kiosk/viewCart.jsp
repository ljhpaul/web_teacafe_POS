<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <h1>${cart.seat_no}번 테이블 장바구니 목록</h1>
  <a href="viewMenu.do?cart_id=${cart.cart_id}">메뉴추가</a>
  <a href="payOrder.do?cart_id=${cart.cart_id}">결제하기</a>
  <a href="cancelOrder.do?cart_id=${cart.cart_id}">주문취소</a>
  <hr>
 <%--  <p>총 금액: ${cart.total_price}</p> --%>
  <table border="1">
    <tr>
      <th>메뉴번호</th>
      <th>온도</th>
      <th>수량</th>
      <th>단가</th>
      <th>총액</th>
    </tr>
    <c:forEach var="item" items="${itemList}">
    <tr>
      <td>${item.menu_no}</td>
      <td>${item.temp}</td>
      <td>${item.amount}</td>
      <td>${item.unit_price}</td>
      <td>${item.unit_price * item.amount}</td>
    </tr>
    </c:forEach>
  </table>
</body>
</html>