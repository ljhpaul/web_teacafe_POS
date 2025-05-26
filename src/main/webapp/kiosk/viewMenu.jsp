<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/common/header.jsp" %>

<h2>메뉴 목록</h2>
<table border="1">
  <tr>
    <th>메뉴번호</th>
    <th>메뉴이름</th>
    <th>가격</th>
    <th>선택</th>
  </tr>
  <c:forEach var="menu" items="${menuList}">
    <tr>
      <td>${menu.menu_no}</td>
      <td>${menu.menu_name}</td>
      <td>${menu.price}원</td>
      <td>
        <form action="viewMenu.do" method="post">
          <input type="hidden" name="cart_id" value="${cart_id}"/>
          <input type="hidden" name="menu_no" value="${menu.menu_no}"/>
          온도:
          <select name="temp">
            <option value="HOT">HOT</option>
            <option value="ICED">ICED</option>
          </select>
          수량:
          <input type="number" name="amount" value="1" min="1" style="width:50px"/>
          <input type="submit" value="담기"/>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>

<a href="viewCart.do">← 장바구니로 돌아가기</a>
