package writer;

import java.util.List;

public interface Writer<T> {

    void print(T source, String path);

    void print(List<T> source, String path);
}
