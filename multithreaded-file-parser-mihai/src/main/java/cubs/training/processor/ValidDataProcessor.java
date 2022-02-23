package cubs.training.processor;

import cubs.training.validation.Validator;

public interface ValidDataProcessor<T> extends DataProcessor<T>, Validator<T> {

    default void processIfValid(T data) {
        if (isValid(data)) {
            process(data);
        }
    }
}
