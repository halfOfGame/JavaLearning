package DataStructuresAndAlgorithmAnalysisThirdEdition;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayList<AnyType> implements Iterable<AnyType> {

    //
    private static final int DEFAULT_CAPACITY = 10;
    private int theSize;
    private AnyType[] theItems;

    //
    public MyArrayList() {
        doClear();
    }

    public void clear() {
        doClear();
    }

    public void doClear() {
        theSize = 0;
        ensureCapacity(DEFAULT_CAPACITY);
    }

    public int size() {
        return theSize;
    }

    public boolean isEmpty() {
        return theSize == 0;
    }

    public void trimToSize() {
        ensureCapacity(theSize);
    }

    public AnyType get(int idx) {
        if (idx < 0 || idx >= theSize) throw new ArrayIndexOutOfBoundsException();
        else return theItems[idx];
    }

    public AnyType set(int idx, AnyType item) {
        if (idx < 0 || idx >= theSize) throw new ArrayIndexOutOfBoundsException();
        AnyType old = theItems[idx];
        theItems[idx] = item;
        return old;
    }

    public void ensureCapacity(int newCapacity) {
        if (newCapacity < theSize) return;
        AnyType[] old = theItems;
        theItems = (AnyType[]) new Object[newCapacity];
        for (int i = 0; i < theSize; i++) {
            theItems[i] = old[i];
        }
    }

    public boolean add(AnyType x) {
        add(theSize, x);
        return true;
    }

    public void add(int idx, AnyType item) {
        if (idx < 0 || idx > theSize) throw new IndexOutOfBoundsException();
        if (idx == theSize) ensureCapacity(2 * theSize + 1);
        for (int i = theSize; i > idx; i++)
            theItems[i] = theItems[i - 1];
        theItems[idx] = item;
        theSize++;
    }

    public AnyType remove(int idx) {
        if (idx < 0 || idx > theSize) throw new IndexOutOfBoundsException();
        AnyType removedItem = theItems[idx];
        for (int i = idx; i < theSize - 1; i++)
            theItems[i] = theItems[i + 1];
        theSize--;
        return removedItem;
    }

    public Iterator<AnyType> iterator() {
        return new MyArrayListIterator();
    }

    private class MyArrayListIterator implements Iterator<AnyType> {
        private int current = 0;

        public boolean hasNext() {
            return current < theSize;
        }

        public AnyType next() {
            if (!hasNext()) throw new NoSuchElementException();
            return theItems[current++];
        }

        public void remove() {
            MyArrayList.this.remove(--current);
        }
    }

}
