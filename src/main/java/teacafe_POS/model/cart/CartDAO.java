package teacafe_POS.model.cart;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import teacafe_POS.common.util.DBUtil;


public class CartDAO {
	
	//0.DTO 만들기
	public CartDTO makeDTO(ResultSet rs) throws SQLException {
		int seat = rs.getInt(3);
		Integer seat_no = rs.wasNull() ? null : seat;	//null처리
		
		CartDTO dto = CartDTO.builder()
				.cart_id(rs.getInt(1))
				.session_id(rs.getString(2))
				.seat_no(seat_no)
				.created_at(rs.getDate(4))
				.build();
		return dto;
	}
	
	//1.selectAll
	public List<CartDTO> selectAll() {
		Connection conn = null;
		Statement st = null;
		List<CartDTO> dtolist = new ArrayList<CartDTO>();
		
		String sql = "select * from cart ORDER BY created_at DESC ";
		
		try {
			conn = DBUtil.getConnection();	//DB연결
			st = conn.createStatement();	//통로 뚫기
			ResultSet rs = st.executeQuery(sql);		//쿼리문 실행 및 결과값 가져오기
			while(rs.next()) {
				CartDTO dto = makeDTO(rs);
				dtolist.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}
		
		return dtolist;
	}
	
	//2-1.selectBySessionIdAndSeatNo
	public CartDTO selectBySessionIdAndSeatNo(String session_id, Integer seat_no) {
	    Connection conn = null;
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    CartDTO dto = null;
		
		String sql = (seat_no == null)
			    ? "SELECT * FROM cart WHERE session_id = ? AND seat_no IS NULL"
			    : "SELECT * FROM cart WHERE session_id = ? AND seat_no = ?";
		
		try {
			conn = DBUtil.getConnection();	//DB연결
			pst = conn.prepareStatement(sql);	//통로 뚫기
			pst.setString(1, session_id);
			if(seat_no != null) pst.setInt(2, seat_no);
			rs = pst.executeQuery();		//쿼리문 실행 및 결과값 가져오기
			if(rs.next()) dto = makeDTO(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, pst, null);
		}
		
		return dto;
	}
	
	//2-2.selectByCartId
	public CartDTO selectByCartId(int cart_id) {
	    Connection conn = null;
	    PreparedStatement pst = null;
	    ResultSet rs = null;
	    CartDTO dto = null;

	    String sql = "SELECT * FROM cart WHERE cart_id = ?";

	    try {
	        conn = DBUtil.getConnection();
	        pst = conn.prepareStatement(sql);
	        pst.setInt(1, cart_id);
	        rs = pst.executeQuery();
	        if (rs.next()) dto = makeDTO(rs);
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.dbDisconnect(conn, pst, null);
	    }

	    return dto;
	}
	
	//3-1.insertCart
	public int insertCart(CartDTO dto) {
	    Connection conn = null;
	    PreparedStatement pst = null;
	    int result = 0;

	    String sql = "INSERT INTO cart (cart_id, session_id, seat_no, created_at) "
	               + "VALUES (cart_seq.NEXTVAL, ?, ?, sysdate)";

	    try {
	        conn = DBUtil.getConnection();
	        pst = conn.prepareStatement(sql);
	        pst.setString(1, dto.getSession_id());

	        if (dto.getSeat_no() == null) pst.setNull(2, java.sql.Types.INTEGER);
	        else pst.setInt(2, dto.getSeat_no());
	        result = pst.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.dbDisconnect(conn, pst, null);
	    }

	    return result;  // 1이면 성공
	}
	
	//3-2.insertCartAndReturnId
	public int insertCartAndReturnId(CartDTO dto) {
	    Connection conn = null;
	    PreparedStatement pst = null;
	    int cart_id = -1;

	    try {
	        conn = DBUtil.getConnection();
	        // Oracle에서는 RETURNING INTO는 CallableStatement로 처리해야 하므로 방식 변경
	        String returningSql = "BEGIN "
	                             + "INSERT INTO cart (cart_id, session_id, seat_no, created_at) "
	                             + "VALUES (cart_seq.NEXTVAL, ?, ?, sysdate) RETURNING cart_id INTO ?; "
	                             + "END;";
	        CallableStatement cst = conn.prepareCall(returningSql);
	        cst.setString(1, dto.getSession_id());
	        if (dto.getSeat_no() == null) cst.setNull(2, java.sql.Types.INTEGER);
	        else cst.setInt(2, dto.getSeat_no());

	        cst.registerOutParameter(3, java.sql.Types.INTEGER);
	        cst.execute();
	        cart_id = cst.getInt(3);
	        cst.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.dbDisconnect(conn, pst, null);
	    }

	    return cart_id;
	}
	
	//4.updateSeatNo
	public int updateSeatNo(String session_id, Integer seat_no) {
	    Connection conn = null;
	    PreparedStatement pst = null;
	    int result = 0;

	    String sql = "UPDATE cart "
	    		   + "SET seat_no = ? "
	               + "WHERE session_id = ? AND seat_no IS NOT NULL";

	    try {
	        conn = DBUtil.getConnection();
	        pst = conn.prepareStatement(sql);
	        pst.setInt(1, seat_no);
	        pst.setString(2, session_id);
	        result = pst.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.dbDisconnect(conn, pst, null);
	    }

	    return result;  // 1이면 성공
	}
	
	//5-1.deleteBySessionIdAndSeatNo
	public int deleteBySessionIdAndSeatNo(String session_id, Integer seat_no) {
	    Connection conn = null;
	    PreparedStatement pst = null;
	    int result = 0;

	    String sql = (seat_no == null)
	        ? "DELETE FROM cart WHERE session_id = ? AND seat_no IS NULL"
	        : "DELETE FROM cart WHERE session_id = ? AND seat_no = ?";

	    try {
	        conn = DBUtil.getConnection();
	        pst = conn.prepareStatement(sql);
	        pst.setString(1, session_id);
	        if (seat_no != null) pst.setInt(2, seat_no);
	        result = pst.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.dbDisconnect(conn, pst, null);
	    }

	    return result;  // 삭제된 건수 (1 or 0)
	}
	
	//5-2.deleteCartByCartId
	public int deleteCartByCartId(int cart_id) {
	    Connection conn = null;
	    PreparedStatement pst = null;
	    int result = 0;

	    String sql = "DELETE FROM cart WHERE cart_id = ?";

	    try {
	        conn = DBUtil.getConnection();
	        pst = conn.prepareStatement(sql);
	        pst.setInt(1, cart_id);
	        result = pst.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBUtil.dbDisconnect(conn, pst, null);
	    }

	    return result;  // 삭제된 건수 (1 또는 0)
	}
	
}