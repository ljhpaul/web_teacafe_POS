package teacafe_POS.model.order_list;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import teacafe_POS.common.util.DBUtil;


public class OrderListDAO {
	//field
	Connection conn;	//DB연결
	Statement st;		//SQL문을 DB에 전송
	PreparedStatement pst;
	ResultSet rs;		//SELECT 결과
	int resultCount;	//DML의 영향 받은 건수
	
	//0.DTO 만들기
	public OrderListDTO makeDTO(ResultSet rs) throws SQLException {
		int seat = rs.getInt(3);
		Integer seat_no = rs.wasNull() ? null : seat;	//null처리
		
		
		OrderListDTO dto = OrderListDTO.builder()
				.order_id(rs.getInt("order_id"))
				.seat_no(seat_no)
				.total_price(rs.getInt("total_price"))
				.order_date(rs.getDate("order_date"))
				.pay_method(rs.getString("pay_method"))
				.pay_status(rs.getString("pay_status"))
				.build();
		return dto;
	}
	
	//1.selectAll
	public List<OrderListDTO> selectAll() {
		Connection conn = null;
		Statement st = null;
		List<OrderListDTO> dtolist = new ArrayList<OrderListDTO>();
		
		String sql = "select * from order_list ORDER BY order_id ";
		
		try {
			conn = DBUtil.getConnection();	//DB연결
			st = conn.createStatement();	//통로 뚫기
			ResultSet rs = st.executeQuery(sql);		//쿼리문 실행 및 결과값 가져오기
			while(rs.next()) {
				OrderListDTO dto = makeDTO(rs);
				dtolist.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}
		
		return dtolist;
	}
	
    //2. (선택) 단건 조회 by order_id
    public OrderListDTO selectById(int order_id) {
        OrderListDTO dto = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM order_list WHERE order_id = ?";

        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, order_id);
            rs = pst.executeQuery();

            if(rs.next()) {
                dto = makeDTO(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, rs);
        }

        return dto;
    }
	
	//3. 주문 추가 (insert) → 생성된 order_id 반환
    public int insert(OrderListDTO dto) {
    	Connection conn = null;
        CallableStatement cst = null;
        int order_id = -1;

        String sql = """
                BEGIN 
                    INSERT INTO order_list (
                        order_id, seat_no, total_price, 
                        order_date, pay_method, pay_status )
                    VALUES ( order_seq.NEXTVAL, ?, ?, SYSDATE, ?, ? )
                    RETURNING order_id INTO ?;
                END;
                """;

        try {
        	conn = DBUtil.getConnection();
            cst = conn.prepareCall(sql);

            if (dto.getSeat_no() == null) {
                cst.setNull(1, Types.INTEGER);
            } else {
                cst.setInt(1, dto.getSeat_no());
            }
            cst.setInt(2, dto.getTotal_price());
            cst.setString(3, dto.getPay_method());
            cst.setString(4, dto.getPay_status());
            cst.registerOutParameter(5, Types.INTEGER);
            cst.execute();
            order_id = cst.getInt(5);  // 생성된 order_id 반환
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, cst, null);
        }
        
		return order_id;
    }
	
}



