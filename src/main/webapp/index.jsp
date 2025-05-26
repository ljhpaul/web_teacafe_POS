<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>통합 주문 관리 시스템</title>
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
    padding: 2.5rem 2rem;
    border-radius: 1.5rem;
    box-shadow: 0 4px 24px 0 rgba(0,0,0,0.07);
    text-align: center;
    width: 100%;
    max-width: 400px;
  }
  h2 {
    margin-bottom: 2rem;
    font-size: 2rem;
    font-weight: 700;
    color: #263053;
    letter-spacing: -1px;
  }
  .menu-btn {
    display: block;
    width: 100%;
    margin-bottom: 1.2rem;
    padding: 1.1rem 0;
    color: #fff;
    border: none;
    border-radius: 0.8rem;
    font-size: 1.15rem;
    font-weight: 600;
    text-decoration: none;
    box-shadow: 0 2px 10px 0 rgba(58,123,213,0.10);
    cursor: pointer;
    position: relative;
    transition: 
      background 0.3s cubic-bezier(.4,2,.4,1),
      box-shadow 0.33s cubic-bezier(.4,2,.4,1),
      transform 0.3s cubic-bezier(.4,2,.4,1);
    outline: none;
  }
.menu-btn.kiosk {
  background: linear-gradient(90deg, #ffe7a3 40%, #ffe082 100%);
  color: #7c5b14;
  border: 1.5px solid #ffe082cc;
  box-shadow: 0 2px 10px 0 rgba(255,224,130,0.09);
}
.menu-btn.pos {
  background: linear-gradient(90deg, #a9d2fb 40%, #76b6ee 100%);
  color: #215177;
  border: 1.5px solid #76b6eec0;
  box-shadow: 0 2px 10px 0 rgba(118,182,238,0.10);
}
.menu-btn.kiosk:hover, .menu-btn.kiosk:focus {
  background: linear-gradient(90deg, #fff1bf 60%, #ffe7a3 100%);
  box-shadow: 0 0 18px 6px #ffe08244, 0 2px 10px 0 #ffe08233;
  border-color: #ffce40cc;
}
.menu-btn.pos:hover, .menu-btn.pos:focus {
  background: linear-gradient(90deg, #c2e1ff 20%, #a9d2fb 100%);
  box-shadow: 0 0 18px 6px #76b6ee55, 0 2px 10px 0 #76b6ee33;
  border-color: #42a0e6bb;
}



  @media (max-width: 600px) {
    .container {
      padding: 1.2rem 0.5rem;
      border-radius: 0.7rem;
      max-width: 95vw;
    }
    h2 {
      font-size: 1.2rem;
    }
    .menu-btn {
      padding: 0.8rem 0;
      font-size: 1rem;
      border-radius: 0.6rem;
    }
  }
</style>
</head>
<body>
  <div class="container">
    <h2>통합 주문 관리 시스템</h2>
    <a class="menu-btn kiosk" href="kiosk/kioskMain.jsp">키오스크</a>
    <a class="menu-btn pos" href="">POS 시스템</a>
  </div>
</body>
</html>
