package cubs.training.processor;

public interface DataProcessor<T> {

    void process(T data);
}
