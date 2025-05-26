<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/header.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>주문하기</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<style>
  body {
    min-height: 100vh;
    display: flex;
    justify-content: center;
    align-items: center;
    background: #f6f8fb;
    font-family: 'Noto Sans KR', Arial, sans-serif;
    margin: 0;
  }
  .container {
    background: #fff;
    padding: 2.5rem 2rem 2.3rem 2rem;
    border-radius: 1.5rem;
    box-shadow: 0 4px 24px 0 rgba(0,0,0,0.07);
    text-align: center;
    width: 100%;
    max-width: 420px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    /* 상단 여백은 padding-top으로 조정 */
    padding-top: 3.7rem;
  }
  h1 {
    font-size: 2.3rem;
    font-weight: 650;
    color: #263053;
    letter-spacing: -1px;
    margin-bottom: 2.5rem;
    margin-top: 0;
    line-height: 1.1;
    text-align: center;
  }
  .btn-row {
    display: flex;
    gap: 1.1rem;
    justify-content: center;
    align-items: stretch;
    width: 100%;
    margin-top: 0.7rem;
  }
  .order-btn {
    flex: 1 1 0;
    min-width: 0;
    min-height: 108px;
    padding: 1.7rem 0;
    font-size: 1.7rem;
    font-weight: 700;
    border-radius: 1.1rem;
    border: 1.5px solid #ececec;
    text-decoration: none;
    box-shadow: 0 2px 10px 0 rgba(200,200,200,0.09);
    cursor: pointer;
    transition: 
      background 0.21s cubic-bezier(.4,2,.4,1),
      box-shadow 0.28s cubic-bezier(.4,2,.4,1),
      border-color 0.18s,
      color 0.13s,
      transform 0.18s;
    display: flex;
    align-items: center;
    justify-content: center;
    outline: none;
    margin: 0;
    color: #6c6250;
    background: linear-gradient(120deg, #fdf6e3 60%, #fcf7ef 100%);
  }
  .order-btn.takeout {
    background: linear-gradient(120deg, #e9f7fa 60%, #f0fafc 100%);
    color: #60707d;
    border-color: #e1f1f7;
  }
  .order-btn:hover, .order-btn:focus {
    background: linear-gradient(120deg, #fcf7ef 80%, #fffefd 100%);
    box-shadow: 0 0 16px 4px #f7e6bc55;
    border-color: #f7e6bcaa;
    transform: scale(1.045);
  }
  .order-btn.takeout:hover, .order-btn.takeout:focus {
    background: linear-gradient(120deg, #f0fafc 70%, #ffffff 100%);
    box-shadow: 0 0 16px 4px #d7f6fa44;
    border-color: #bfe7efbb;
    transform: scale(1.045);
  }
  @media (max-width: 600px) {
    .container {
      padding: 1.2rem 0.5rem 1.1rem 0.5rem;
      border-radius: 0.9rem;
      max-width: 98vw;
      padding-top: 1.1rem;
    }
    h1 {
      font-size: 1.3rem;
      margin-bottom: 1.3rem;
    }
    .btn-row {
      gap: 0.5rem;
    }
    .order-btn {
      padding: 1.1rem 0;
      min-height: 68px;
      font-size: 1.23rem;
      border-radius: 0.7rem;
    }
  }
</style>
</head>
<body>
  <div class="container">
    <h1>주문하기</h1>
    <div class="btn-row">
      <a href="${cpath}/seatList.do" class="order-btn">매장</a>
      <a href="${cpath}/kiosk/jsp/viewCart.jsp?seat_no=takeout" class="order-btn takeout">포장</a>
    </div>
  </div>
</body>
</html>
