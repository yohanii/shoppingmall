package shoppingmall.shoppingmallspring.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shoppingmall.shoppingmallspring.domain.Cloth;
import shoppingmall.shoppingmallspring.repository.JdbcShoppingmallRepository;
import shoppingmall.shoppingmallspring.repository.ShoppingmallRepository;

import java.util.HashMap;
import java.util.Map;

@Service
public class ShoppingmallService {

    private final ShoppingmallRepository repository;

    @Autowired
    public ShoppingmallService(ShoppingmallRepository repository) {
        this.repository = repository;
    }

    /**
     * 옷 전체 조회 - DTO를 json으로 변환해서 리턴할 것.
     */
    public String getClothesJson() {
        String json = null;
        Map<String, Object> data = new HashMap<>();
        data.put("items", repository.findAll());

        try {
            json = new ObjectMapper().writeValueAsString(data);
        } catch (Exception e) {
            e.getMessage();
        }

        return json;
    }

    /**
     * 옷 추가
     */
    public Long addCloth(Cloth cloth) {
        return repository.save(cloth).getId();
    }

    /**
     * 전체 개수 조회
     */

    /**
     * 옷 정보 변경
     */
    public Long updateCloth(Long id, Cloth cloth) {
        return repository.updateCloth(id, cloth);
    }

    /**
     * 선택한 옷 제거
     */
    public Long deleteCloth(String type, String color) throws IllegalStateException {
        return repository.deleteOneByTypeColor(type, color).get().getId();
    }
}
