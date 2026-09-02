// Java Iterator interface reference:
// https://docs.oracle.com/javase/8/docs/api/java/util/Iterator.html

class PeekingIterator implements Iterator<Integer> {

    private Iterator<Integer> iterator;
    private Integer nextElement;

    public PeekingIterator(Iterator<Integer> iterator) {
        this.iterator = iterator;

        if (iterator.hasNext()) {
            nextElement = iterator.next();
        }
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return nextElement;
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    @Override
    public Integer next() {
        Integer current = nextElement;

        if (iterator.hasNext()) {
            nextElement = iterator.next();
        } else {
            nextElement = null;
        }

        return current;
    }

    @Override
    public boolean hasNext() {
        return nextElement != null;
    }
}