<%@page import="teacafe_POS.model.order_detail.OrderDetailService"%>
<%@page import="teacafe_POS.model.order_list.OrderListService"%>
<%@page import="teacafe_POS.model.cart.CartDTO"%>
<%@page import="java.util.List"%>
<%@page import="teacafe_POS.model.cart.CartService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
OrderListService olService = new OrderListService();
OrderDetailService odService = new OrderDetailService();
CartService cService = new CartService();

int order_no = olService.getNewOrderNo();
odService.deleteAllOrderDetail(order_no);
List<CartDTO> cartlist = cService.viewCart(order_no);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  <h1><%=order_no%>번 주문 장바구니 목록</h1>
  <table>
    <tbody>
        <%
        int cnt = 1;
        for(CartDTO cart : cartlist) {
            out.print("<tr>");
        	out.print("<td>" + cnt + "</td>");
        	out.print("<td>");
        	out.print(cart.getMenu_name());
        	out.print("</td>");
        	out.print("<td>");
        	out.print(cart.getOrder_temp());
        	out.print("</td>");
        	out.print("<td>");
        	out.print(cart.getOrder_amount());
        	out.print("</td>");
            out.print("</tr>");
        }
        %>
    </tbody>
  </table>
</body>
</html>