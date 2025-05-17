package Repository;

import java.util.List;

public interface Repository<T> {

    public List<T> findAll();
    public T findById(int id);
    public void save(T object);
    public void update(T object);
    public void delete(int id);

}
