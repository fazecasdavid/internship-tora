package cubs.training.validation;

public interface Validator<T> {

    boolean isValid(T item);
}
