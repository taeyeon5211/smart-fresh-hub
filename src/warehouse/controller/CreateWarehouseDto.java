package warehouse.controller;

@FunctionalInterface
public interface CreateWarehouseDto<T, U, V, W, R> {
    R apply(T t, U u, V v, W w);
}
