package teacafe_POS.model.order_detail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import teacafe_POS.common.util.DBUtil;

public class OrderDetailDAO {
	//field
	Connection conn;	//DB연결
	Statement st;		//SQL문을 DB에 전송
	PreparedStatement pst;
	ResultSet rs;		//SELECT 결과
	int resultCount;	//DML의 영향 받은 건수
	
	//0.DTO 만들기
	public OrderDetailDTO makeDTO(ResultSet rs) throws SQLException {
		OrderDetailDTO dto = OrderDetailDTO.builder()
				.order_detail_no(rs.getInt("order_detail_no"))
				.order_id(rs.getInt("order_id"))
				.menu_no(rs.getInt("menu_no"))
				.unit_price(rs.getInt("unit_price"))
				.order_temp(rs.getString("order_temp"))
				.order_amount(rs.getInt("order_amount"))
				.build();
		return dto;
	}
	
	// 1. 여러 건 insert
    public int insert(List<OrderDetailDTO> list) {
        Connection conn = null;
        PreparedStatement pst = null;
        int[] results = null;

        String sql = """
            INSERT INTO order_detail (
                order_detail_no, order_id, menu_no, 
                unit_price, order_temp, order_amount ) 
            VALUES ( order_detail_seq.NEXTVAL, ?, ?, ?, ?, ? )
            """;

        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(sql);

            for (OrderDetailDTO dto : list) {
                pst.setInt(1, dto.getOrder_id());

                if (dto.getMenu_no() == null) {
                    pst.setNull(2, Types.INTEGER);
                } else {
                    pst.setInt(2, dto.getMenu_no());
                }

                pst.setInt(3, dto.getUnit_price());
                pst.setString(4, dto.getOrder_temp());
                pst.setInt(5, dto.getOrder_amount());

                pst.addBatch();
            }

            results = pst.executeBatch();  // 일괄 insert 수행

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, null);
        }
        
        // 성공한 건 수의 합을 반환 (results[i] == 1 이면 성공)
        int total = 0;
        if (results != null) {
            for (int r : results) if (r >= 0) total++;
        }
        return total;
    }
	
	//2-1.updateAmountById
	public int updateAmountById(int order_amount, int order_id) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = "update order_detail set order_amount = ? where order_id = ? ";
			pst = conn.prepareStatement(sql);	//통로 뚫기
			pst.setInt(1, order_amount);
			pst.setInt(2, order_id);
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
			
		return resultCount;
	}
	
	//2-2.updateTempById
	public int updateTempById(String order_temp, int order_id) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = "update order_detail set order_temp = ? where order_id = ? ";
			pst = conn.prepareStatement(sql);	//통로 뚫기
			pst.setString(1, order_temp);
			pst.setInt(2, order_id);
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
			
		return resultCount;
	}
	
	//3.deleteOrderDetail
	public int deleteOrderDetail(int order_id) {
		resultCount = 0;	//삽입 건수 초기화
		Connection conn = DBUtil.getConnection();	//DB연결
		
		try {
			String sql = "delete from order_detail where order_id = ? ";
			pst = conn.prepareStatement(sql);	//통로 뚫기
			pst.setInt(1, order_id);
			resultCount = pst.executeUpdate();		//쿼리문 실행 및 결과값 가져오기
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
			
		return resultCount;
	}
}



