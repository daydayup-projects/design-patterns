package tech.alexchen.daydayup.designpattern.behavioural.chain.filter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * FilterChain 也可以 Filter，可以让不同的 FilterChain 连接在一起
 *
 * @author alexchen
 * @date 2023/3/5
 */
public class FilterChain {

    private final List<Filter> filters = new CopyOnWriteArrayList<>();

    private final ThreadLocal<Integer> index = ThreadLocal.withInitial(() -> 0);

    public FilterChain() {}

    public FilterChain(List<Filter> filters) {
        this.filters.addAll(filters);
    }

    public FilterChain add(Filter filter) {
        if (filter == null) {
            throw new RuntimeException("Filter invalid");
        }
        filters.add(filter);
        return this;
    }

    public void doFilter(Request request, Response response) {
        if (index.get() >= filters.size()) {
            index.set(0);
            return;
        }
        Filter filter = filters.get(index.get());
        index.set(index.get() + 1);
        filter.doFilter(request, response, this);
    }
}
