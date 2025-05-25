package teacafe_POS.model.cart_item;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import teacafe_POS.common.util.DBUtil;


public class CartItemDAO {
	
	//0.DTO 만들기
	public CartItemDTO makeDTO(ResultSet rs) throws SQLException {
		
		CartItemDTO dto = CartItemDTO.builder()
				.cart_item_id(rs.getInt("cart_item_id"))
				.cart_id(rs.getInt("cart_id"))
				.menu_no(rs.getInt("menu_no"))
				.unit_price(rs.getInt("unit_price"))
				.temp(rs.getString("temp"))
				.amount(rs.getInt("amount"))
				.build();
		
		return dto;
	}
	
	//1-1. selectAll
	public List<CartItemDTO> selectAll() {
		Connection conn = null;
		Statement st = null;
		List<CartItemDTO> dtolist = new ArrayList<CartItemDTO>();
		
		String sql = "select * from cart_iem ORDER BY created_at DESC ";
		
		try {
			conn = DBUtil.getConnection();	//DB연결
			st = conn.createStatement();	//통로 뚫기
			ResultSet rs = st.executeQuery(sql);		//쿼리문 실행 및 결과값 가져오기
			while(rs.next()) {
				CartItemDTO dto = makeDTO(rs);
				dtolist.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.dbDisconnect(conn, st, null);
		}
		
		return dtolist;
	}
	
	//1-2. 특정 cart_id의 모든 장바구니 항목 조회
    public List<CartItemDTO> selectByCartId(int cart_id) {
        List<CartItemDTO> itemList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM cart_item WHERE cart_id = ?";

        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, cart_id);
            rs = pst.executeQuery();
            while (rs.next()) {
                itemList.add(makeDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, rs);
        }

        return itemList;
    }
	
    //2. 장바구니 항목 추가
    public int insert(CartItemDTO dto) {
        Connection conn = null;
        PreparedStatement pst = null;
        int result = 0;

        String sql = "INSERT INTO cart_item (cart_item_id, cart_id, menu_no, unit_price, temp, amount) "
                   + "VALUES (cart_item_seq.NEXTVAL, ?, ?, ?, ?, ?)";

        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, dto.getCart_id());
            pst.setInt(2, dto.getMenu_no());
            pst.setInt(3, dto.getUnit_price());
            pst.setString(4, dto.getTemp());
            pst.setInt(5, dto.getAmount());

            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, null);
        }

        return result;
    }
    
  //3-1. 장바구니 update (수량, 온도, 단가 등)
    public int update(CartItemDTO dto) {
        Connection conn = null;
        PreparedStatement pst = null;
        int result = 0;

        String sql = "UPDATE cart_item SET menu_no = ?, unit_price = ?, temp = ?, amount = ? "
                + "WHERE cart_item_id = ?";
        
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, dto.getMenu_no());
            pst.setInt(2, dto.getUnit_price());
            pst.setString(3, dto.getTemp());
            pst.setInt(4, dto.getAmount());
            pst.setInt(5, dto.getCart_item_id());

            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, null);
        }

        return result;
    }
    
    //3-2 장바구니 수량 변경
    public int updateAmountById(int amount, int cart_item_id) {
        Connection conn = null;
        PreparedStatement pst = null;
        int result = 0;

        String sql = "UPDATE cart_item SET amount = ? "
                + "WHERE cart_item_id = ?";
        
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, amount);
            pst.setInt(2, cart_item_id);

            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, null);
        }

        return result;
    }
    
    //3-2 장바구니 온도 변경
    public int updateTempById(String temp, int cart_item_id) {
        Connection conn = null;
        PreparedStatement pst = null;
        int result = 0;

        String sql = "UPDATE cart_item SET temp = ? "
                + "WHERE cart_item_id = ?";
        
        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, temp);
            pst.setInt(2, cart_item_id);

            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, null);
        }

        return result;
    }
    
    //4-1. cart_item_id로 삭제
    public int deleteById(int cart_item_id) {
        Connection conn = null;
        PreparedStatement pst = null;
        int result = 0;

        String sql = "DELETE FROM cart_item WHERE cart_item_id = ?";

        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, cart_item_id);
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, null);
        }

        return result;
    }

    //4-2. cart_id로 전체 항목 삭제 (장바구니 전체 비우기)
    public int deleteByCartId(int cart_id) {
        Connection conn = null;
        PreparedStatement pst = null;
        int result = 0;

        String sql = "DELETE FROM cart_item WHERE cart_id = ?";

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

        return result;
    }
}