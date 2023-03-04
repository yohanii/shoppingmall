package shoppingmall.shoppingmallspring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shoppingmall.shoppingmallspring.repository.JdbcShoppingmallRepository;
import shoppingmall.shoppingmallspring.repository.ShoppingmallRepository;

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
        return "{\n" +
                "  \"items\": [\n" +
                "    {\"type\": \"tshirt\",\n" +
                "      \"gender\": \"female\",\n" +
                "      \"size\": \"large\",\n" +
                "      \"color\": \"blue\",\n" +
                "      \"image\": \"source/blue_t.png\"\n" +
                "    },\n" +
                "    {\"type\": \"skirt\",\n" +
                "      \"gender\": \"female\",\n" +
                "      \"size\": \"large\",\n" +
                "      \"color\": \"yellow\",\n" +
                "      \"image\": \"source/yellow_s.png\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
    }

    /**
     * 옷 추가
     */

    /**
     * 전체 개수 조회
     */

    /**
     * 옷 정보 변경
     */

    /**
     * 선택한 옷 제거
     */
}
