package ee.sml.parking.dao;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    List<T> list();

    void create(T t);

    Optional<T> get(Long id);

    void update(T t, int id);

    void delete(int id);
}
