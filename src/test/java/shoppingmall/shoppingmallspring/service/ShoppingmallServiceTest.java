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
}