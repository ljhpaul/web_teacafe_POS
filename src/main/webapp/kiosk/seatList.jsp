<%@page import="teacafe_POS.model.seat.SeatDTO"%>
<%@page import="java.util.List"%>
<%@page import="teacafe_POS.model.seat.SeatService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../common/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
  table, th, tb {
    border: 1px solid black;
    border-collapse: collapse;
    text-align: center;
  }
  th, td { padding: 3px; }
</style>
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
          <td><a href="${cpath}/cart.jsp?seat_no=${seat.seat_no}">${seat.seat_no}</a></td>
          <td>${seat.getReservation().equals("Y")?"예약됨"
        		   :(seat.getSeated().equals("Y")?"사용중":"사용가능")}</td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</body>
</html>