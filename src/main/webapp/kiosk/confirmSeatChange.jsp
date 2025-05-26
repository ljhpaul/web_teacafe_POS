<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<h3>
현재 ${old_seat_no}번 테이블에서 주문 중입니다.<br>
${new_seat_no}번 테이블에서 주문하시겠습니까?
</h3>

<form action="changeSeat.do" method="post">
  <input type="hidden" name="old_seat_no" value="${old_seat_no}">
  <input type="hidden" name="new_seat_no" value="${new_seat_no}">
  <input type="submit" value="예, 변경합니다.">
</form>

<a href="viewCart.do">아니요, 기존 좌석 유지</a>
