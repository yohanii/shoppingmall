package shoppingmall.shoppingmallspring.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.shoppingmallspring.domain.Cloth;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class JdbcShoppingmallRepositoryTest {

    @Autowired
    JdbcShoppingmallRepository repository;

    @Test
    void save() {
        Cloth cloth = new Cloth();
        cloth.setType("tshirt");
        cloth.setColor("blue");
        cloth.setGender("male");
        cloth.setSize("small");
        cloth.setImage("source/blue_t.png");

        repository.save(cloth);

        Cloth result = repository.findById(cloth.getId()).get();
        assertThat(cloth.getType()).isEqualTo(result.getType());
        assertThat(cloth.getColor()).isEqualTo(result.getColor());
        assertThat(cloth.getGender()).isEqualTo(result.getGender());
        assertThat(cloth.getSize()).isEqualTo(result.getSize());
        assertThat(cloth.getImage()).isEqualTo(result.getImage());
    }

    @Test
    void findById() {
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
        Cloth result1 = repository.findById(cloth1.getId()).get();
        assertThat(cloth1.getType()).isEqualTo(result1.getType());
        assertThat(cloth1.getColor()).isEqualTo(result1.getColor());

        Cloth result2 = repository.findById(cloth2.getId()).get();
        assertThat(cloth2.getType()).isEqualTo(result2.getType());
        assertThat(cloth2.getColor()).isEqualTo(result2.getColor());
    }

    @Test
    void findAll() {
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
        List<Cloth> list = repository.findAll();
        assertThat(cloth1.getType()).isEqualTo(list.get(0).getType());
        assertThat(cloth1.getColor()).isEqualTo(list.get(0).getColor());

        assertThat(cloth2.getType()).isEqualTo(list.get(1).getType());
        assertThat(cloth2.getColor()).isEqualTo(list.get(1).getColor());
    }

    @Test
    void deleteByTypeColor() {
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
        Long id_save = cloth1.getId();
        Optional<Cloth> result1 = repository.deleteByTypeColor(cloth1.getType(), cloth1.getColor());
        assertThat(result1.get().getType()).isEqualTo(cloth1.getType());
        assertThat(result1.get().getColor()).isEqualTo(cloth1.getColor());

        assertThat(repository.findById(id_save).get()).isEqualTo(null);

        Optional<Cloth> result2 = repository.deleteByTypeColor(cloth1.getType(), cloth1.getColor());
        assertThat(result2.get()).isEqualTo(null);
    }
}