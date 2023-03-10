package shoppingmall.shoppingmallspring.service;

import org.assertj.core.api.Assertions;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.shoppingmallspring.domain.Cloth;
import shoppingmall.shoppingmallspring.repository.JdbcShoppingmallRepository;
import shoppingmall.shoppingmallspring.repository.ShoppingmallRepository;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ShoppingmallServiceTest {

    @Autowired
    ShoppingmallRepository repository;
    @Autowired
    ShoppingmallService service;

    @Test
    void getClothesJson() throws JSONException {
        JSONObject result1 = null;
        JSONObject result2 = null;

        //given
        Cloth cloth1 = new Cloth();
        cloth1.setType("tshirt");
        cloth1.setColor("blue");

        Cloth cloth2 = new Cloth();
        cloth2.setType("skirt");
        cloth2.setColor("yellow");

        //when
        repository.save(cloth1);
        repository.save(cloth2);

        //then
        String result = service.getClothesJson();
        try {
            JSONObject jObject = new JSONObject(result);
            result1 = jObject.getJSONArray("items").getJSONObject(0);
            result2 = jObject.getJSONArray("items").getJSONObject(1);

        } catch (Exception e) {
            e.printStackTrace();
        }

        assertThat(result1.get("type")).isEqualTo("tshirt");
        assertThat(result1.get("color")).isEqualTo("blue");
        assertThat(result2.get("type")).isEqualTo("skirt");
        assertThat(result2.get("color")).isEqualTo("yellow");
    }

    @Test
    void addCloth() {
        //저장 잘 되었는지 확인

        //given
        Cloth cloth1 = new Cloth();
        cloth1.setType("tshirt");
        cloth1.setColor("blue");
        cloth1.setGender("male");
        cloth1.setSize("small");
        cloth1.setImage("source/blue_t.png");

        //when
        Long save_id = service.addCloth(cloth1);

        //then
        Cloth result = repository.findById(save_id).get();
        assertThat(result.getId()).isEqualTo(cloth1.getId());
        assertThat(result.getType()).isEqualTo(cloth1.getType());
        assertThat(result.getColor()).isEqualTo(cloth1.getColor());
        assertThat(result.getGender()).isEqualTo(cloth1.getGender());
        assertThat(result.getSize()).isEqualTo(cloth1.getSize());
        assertThat(result.getImage()).isEqualTo(cloth1.getImage());
    }

    @Test
    void addClothValidation() {
        //이상한 데이터 거르기 -> validator implement해서 구현해보기

        //given
        //when
        //then
    }

    @Test
    void deleteCloth() {
        //given
        Cloth cloth1 = new Cloth();
        cloth1.setType("tshirt");
        cloth1.setColor("blue");
        cloth1.setGender("male");
        cloth1.setSize("small");
        cloth1.setImage("source/blue_t.png");

        //when
        service.addCloth(cloth1);
        Long save_id = service.deleteCloth(cloth1.getType(), cloth1.getColor());

        //then
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> repository.findById(save_id));
        assertThat(e.getMessage()).isEqualTo("java.sql.SQLException: id로 옷 조회 실패");
    }
}