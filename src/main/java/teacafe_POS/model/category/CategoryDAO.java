package teacafe_POS.model.category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import teacafe_POS.common.util.DBUtil;


public class CategoryDAO {
	
	//0.DTO 만들기
	public CategoryDTO makeDTO(ResultSet rs) throws SQLException {
		CategoryDTO dto = CategoryDTO.builder()
				.category_id(rs.getInt(1))
				.category_name(rs.getString(2))
				.build();
		return dto;
	}
	
	// 1. 전체 조회
    public List<CategoryDTO> selectAll() {
        List<CategoryDTO> dtolist = new ArrayList<>();
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM category ORDER BY category_id";

        try {
            conn = DBUtil.getConnection();
            st = conn.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()) {
            	dtolist.add(makeDTO(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, st, rs);
        }

        return dtolist;
    }
	
    // 2. ID로 조회
    public CategoryDTO selectById(int category_id) {
        CategoryDTO dto = null;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM category WHERE category_id = ?";

        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, category_id);
            rs = pst.executeQuery();
            if (rs.next()) {
                dto = makeDTO(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, rs);
        }

        return dto;
    }
	
    // 3. 추가
    public int insert(CategoryDTO dto) {
        Connection conn = null;
        PreparedStatement pst = null;
        int result = 0;

        String sql = "INSERT INTO category (category_id, category_name) VALUES (?, ?)";

        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, dto.getCategory_id());
            pst.setString(2, dto.getCategory_name());
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, null);
        }

        return result;
    }
	
    // 4. 수정
    public int update(CategoryDTO dto) {
        Connection conn = null;
        PreparedStatement pst = null;
        int result = 0;

        String sql = "UPDATE category SET category_name = ? WHERE category_id = ?";

        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setString(1, dto.getCategory_name());
            pst.setInt(2, dto.getCategory_id());
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, null);
        }

        return result;
    }
	
	
    // 5. 삭제
    public int deleteById(int category_id) {
        Connection conn = null;
        PreparedStatement pst = null;
        int result = 0;

        String sql = "DELETE FROM category WHERE category_id = ?";

        try {
            conn = DBUtil.getConnection();
            pst = conn.prepareStatement(sql);
            pst.setInt(1, category_id);
            result = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.dbDisconnect(conn, pst, null);
        }

        return result;
    }
	

}