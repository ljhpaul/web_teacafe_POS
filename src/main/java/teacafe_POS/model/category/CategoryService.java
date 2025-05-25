package teacafe_POS.model.category;

import java.util.List;

//Service: 비즈니스 로직을 수행, DB에 가는 업무는 DAO를 호출.
public class CategoryService {
	
	CategoryDAO DAO = new CategoryDAO();
	
	//1.selectAll
	public List<CategoryDTO> selectAll() {
		return DAO.selectAll();
	}
	
	// 2. ID로 조회
    public CategoryDTO selectById(int category_id) {
    	return DAO.selectById(category_id);
	}
	
    // 3. 추가
    public int insert(CategoryDTO dto) {
    	return DAO.insert(dto);
	}
	
    // 4. 수정
    public int update(CategoryDTO dto) {
    	return DAO.update(dto);
	}
	
    // 5. 삭제
    public int deleteById(int category_id) {
    	return DAO.deleteById(category_id);
	}
	
}
