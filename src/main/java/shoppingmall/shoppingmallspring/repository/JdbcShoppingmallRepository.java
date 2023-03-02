package shoppingmall.shoppingmallspring.repository;

import org.springframework.stereotype.Repository;
import shoppingmall.shoppingmallspring.domain.Cloth;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcShoppingmallRepository implements ShoppingmallRepository{

    private final DataSource dataSource;

    public JdbcShoppingmallRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Cloth save(Cloth cloth) {
        return null;
    }

    @Override
    public Optional<Cloth> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Cloth> findAll() {
        return null;
    }

    @Override
    public Optional<Cloth> deleteByTypeColor(String type, String color) {
        return Optional.empty();
    }
}
