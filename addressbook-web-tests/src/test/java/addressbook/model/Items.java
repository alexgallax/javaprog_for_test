package addressbook.model;

import com.google.common.collect.ForwardingSet;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Items<T> extends ForwardingSet<T> {

    private Set<T> delegate;

    public Items() {
        this.delegate = new HashSet<>();
    }

    public Items(Items<T> items) {
        this.delegate = new HashSet<>(items.delegate);
    }

    public Items(Collection<T> items) {
        this.delegate = new HashSet<>(items);
    }

    @Override
    protected Set<T> delegate() {
        return this.delegate;
    }

    public Items<T> withAdded(T item) {
        Items<T> items = new Items<>(this);
        items.add(item);

        return items;
    }

    public Items<T> without(T item) {
        Items<T> items = new Items<>(this);
        items.remove(item);

        return items;
    }
}
