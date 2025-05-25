--------------------------------------------------------------
-- 테이블 제거
BEGIN
  FOR t IN (SELECT table_name FROM user_tables) LOOP
    EXECUTE IMMEDIATE 'DROP TABLE ' || t.table_name || ' CASCADE CONSTRAINTS';
  END LOOP;
END;
/
--------------------------------------------------------------
-- 시퀀스 제거
DROP SEQUENCE cart_seq;
DROP SEQUENCE cart_item_seq;
DROP SEQUENCE order_seq;
DROP SEQUENCE order_detail_seq;
--------------------------------------------------------------
-- 1. 카테고리 테이블
CREATE TABLE Category (
  category_id     NUMBER PRIMARY KEY,
  category_name   VARCHAR2(30) NOT NULL
);

-- 2. 메뉴 테이블 (카테고리 추가)
CREATE TABLE Menu (
  menu_no       NUMBER PRIMARY KEY,
  menu_name     VARCHAR2(30) NOT NULL UNIQUE,
  price         NUMBER NOT NULL,
  category_id   NUMBER REFERENCES Category(category_id),
  temp          VARCHAR2(20),
  sold_out      VARCHAR2(1) DEFAULT 'N'
);

-- 3. 좌석 테이블
CREATE TABLE Seat (
  seat_no       NUMBER PRIMARY KEY,
  seated        VARCHAR2(1) DEFAULT 'N',
  reservation   VARCHAR2(1) DEFAULT 'N'
);

-- 4. 장바구니 테이블
CREATE TABLE Cart (
  cart_id     NUMBER PRIMARY KEY,
  session_id  VARCHAR2(100),
  seat_no     NUMBER REFERENCES Seat(seat_no) ON DELETE SET NULL,
  created_at  DATE DEFAULT SYSDATE
);

-- 5. 장바구니 아이템 테이블
CREATE TABLE Cart_Item (
  cart_item_id  NUMBER PRIMARY KEY,
  cart_id       NUMBER REFERENCES Cart(cart_id) ON DELETE CASCADE,
  menu_no       NUMBER REFERENCES Menu(menu_no) ON DELETE SET NULL,
  unit_price    NUMBER NOT NULL,
  temp          VARCHAR2(20),
  amount        NUMBER DEFAULT 1
);

-- 6. 주문 테이블
CREATE TABLE Order_List (
  order_id      NUMBER PRIMARY KEY,
  seat_no       NUMBER REFERENCES Seat(seat_no) ON DELETE SET NULL,
  total_price   NUMBER,
  order_date    DATE DEFAULT SYSDATE,
  pay_method    VARCHAR2(20),
  pay_status    VARCHAR2(20)
);

-- 7. 주문 상세 테이블
CREATE TABLE Order_Detail (
  order_detail_no  NUMBER PRIMARY KEY,
  order_id         NUMBER REFERENCES Order_List(order_id) ON DELETE CASCADE,
  menu_no          NUMBER REFERENCES Menu(menu_no) ON DELETE SET NULL,
  unit_price       NUMBER NOT NULL,
  order_temp       VARCHAR2(20),
  order_amount     NUMBER DEFAULT 1
);
--------------------------------------------------------------
----시퀀스 추가
CREATE SEQUENCE cart_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE;  -- (필요 시 CACHE 20 등으로 조정)
  
CREATE SEQUENCE cart_item_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE;  -- (필요 시 CACHE 20 등으로 조정)
  
CREATE SEQUENCE order_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE;  -- (필요 시 CACHE 20 등으로 조정)
  
CREATE SEQUENCE order_detail_seq
  START WITH 1
  INCREMENT BY 1
  NOCACHE;  -- (필요 시 CACHE 20 등으로 조정)
--------------------------------------------------------------
----데이터 추가
--(카테고리 데이터: 카테고리번호, 카테고리명)
INSERT INTO Category VALUES (1, '수제전통차');
INSERT INTO Category VALUES (2, '허브차');
INSERT INTO Category VALUES (3, '과실차');
INSERT INTO Category VALUES (4, '전통디저트');
INSERT INTO Category VALUES (5, '계절메뉴');
INSERT INTO Category VALUES (6, '커피·코코아');


--(메뉴 데이터: 메뉴번호, 메뉴명, 가격, 카테고리번호, 온도, 품절여부)
INSERT INTO Menu VALUES (1, '쌍화차', 9500, 1, 'BOTH', 'N');
INSERT INTO Menu VALUES (2, '대추차', 9000, 1, 'BOTH', 'N');
INSERT INTO Menu VALUES (3, '배숙', 8500, 1, 'BOTH', 'N');
INSERT INTO Menu VALUES (4, '모과차', 8000, 1, 'BOTH', 'N');
INSERT INTO Menu VALUES (5, '생강차', 8000, 1, 'BOTH', 'N');
INSERT INTO Menu VALUES (6, '수정과', 8000, 1, 'BOTH', 'N');
INSERT INTO Menu VALUES (7, '오미자차', 8000, 1, 'BOTH', 'N');

INSERT INTO Menu VALUES (8, '작설차(우전)', 8000, 2, 'HOT', 'N');
INSERT INTO Menu VALUES (9, '홍삼귤피차', 8000, 2, 'HOT', 'N');
INSERT INTO Menu VALUES (10, '국화차', 7500, 2, 'HOT', 'N');
INSERT INTO Menu VALUES (11, '제주메리골드차', 7500, 2, 'BOTH', 'N');

INSERT INTO Menu VALUES (12, '유자차', 7500, 3, 'BOTH', 'N');
INSERT INTO Menu VALUES (13, '매실차', 7500, 3, 'BOTH', 'N');
INSERT INTO Menu VALUES (14, '오디차', 7500, 3, 'ICED', 'N');
INSERT INTO Menu VALUES (15, '자몽차', 7500, 3, 'HOT', 'N');

INSERT INTO Menu VALUES (16, '수제단팥죽', 10000, 4, NULL, 'Y');
INSERT INTO Menu VALUES (17, '모둠떡', 8000, 4, NULL, 'N');
INSERT INTO Menu VALUES (18, '인절미', 5500, 4, NULL, 'N');
INSERT INTO Menu VALUES (19, '한과', 4000, 4, NULL, 'N');

INSERT INTO Menu VALUES (20, '수제팥빙수', 15000, 5, NULL, 'N');
INSERT INTO Menu VALUES (21, '오미자빙수', 14000, 5, NULL, 'Y');
INSERT INTO Menu VALUES (22, '인절미빙수', 13000, 5, NULL, 'N');
INSERT INTO Menu VALUES (23, '콩가루아이스크림', 8000, 5, NULL, 'Y');
INSERT INTO Menu VALUES (24, '홍시슬러시', 8000, 5, NULL, 'N');
INSERT INTO Menu VALUES (25, '유자슬러시', 8000, 5, NULL, 'N');
INSERT INTO Menu VALUES (26, '미숫가루쉐이크', 8000, 5, NULL, 'N');

INSERT INTO Menu VALUES (27, '아메리카노', 6000, 6, 'BOTH', 'N');
INSERT INTO Menu VALUES (28, '카페라떼', 6500, 6, 'BOTH', 'N');
INSERT INTO Menu VALUES (29, '코코아', 7000, 6, 'BOTH', 'N');

--(좌석 데이터: 좌석번호, 착석여부, 예약여부);
INSERT INTO Seat VALUES (1, 'N', 'N');
INSERT INTO Seat VALUES (2, 'N', 'N');
INSERT INTO Seat VALUES (3, 'N', 'N');
INSERT INTO Seat VALUES (4, 'N', 'N');
INSERT INTO Seat VALUES (5, 'N', 'N');
INSERT INTO Seat VALUES (6, 'N', 'N');
INSERT INTO Seat VALUES (7, 'N', 'N');
INSERT INTO Seat VALUES (8, 'N', 'N');
INSERT INTO Seat VALUES (9, 'N', 'N');

--------------------------------------------------------------
commit;
--------------------------------------------------------------

select * from menu;
select * from menu where category_id = 1;
select * from seat;
select * from cart;
select * from cart_item where cart_id = 1;

INSERT INTO seat(seat_no) VALUES(10);
INSERT INTO seat(seat_no) VALUES(11);
delete from seat where seat_no = 11;