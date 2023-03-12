package shoppingmall.shoppingmallspring.repository;

import shoppingmall.shoppingmallspring.domain.Cloth;

import java.util.List;
import java.util.Optional;

public interface ShoppingmallRepository {
    Cloth save(Cloth cloth);
    Optional<Cloth> findById(Long id);
    List<Cloth> findAll();
    Long updateCloth(Long id, Cloth cloth);
    Optional<Cloth> deleteOneByTypeColor(String type, String color);
    Long getRowLength();
    List<Integer> getCounts();
}
