package CustomIterator;

public  interface Iterator<E> {
    boolean hasNext(); // idempotent
    E next();
}
