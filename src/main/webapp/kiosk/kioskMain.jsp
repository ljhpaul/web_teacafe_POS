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
  <div class="container">
    <h1>주문하기</h1>
    <ul>
      <li><a href="${cpath}/seatList.do">매장</a></li>
      <li><a href="${cpath}/kiosk/jsp/viewCart.jsp?seat_no=takeout">포장</a></li>      
    </ul>
  </div>
</body>
</html>