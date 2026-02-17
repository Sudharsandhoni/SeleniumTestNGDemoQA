package core.helpers.reports;

@FunctionalInterface
public interface ThrowingSupplier<T> {
    T get() throws Exception;
}
