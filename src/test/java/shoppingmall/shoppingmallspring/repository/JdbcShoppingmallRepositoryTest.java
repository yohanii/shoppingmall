package shoppingmall.shoppingmallspring.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shoppingmall.shoppingmallspring.domain.Cloth;

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

        repository.save(cloth);

        assertThat(cloth).isEqualTo(repository.findById(cloth.getId()).get());
    }

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    void deleteByTypeColor() {
    }
}