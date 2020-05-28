package repository;

public interface IRepository<ID, E> {
    void save(E entity);

    void update(ID id, E entity);

    void delete(ID id);

    E findOne(ID id);

    Iterable<E> findAll();

    int size();

}
