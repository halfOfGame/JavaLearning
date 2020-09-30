package DataStructuresAndAlgorithmAnalysisThirdEdition;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyLinkedList<AnyType> implements Iterable<AnyType> {

    private static class Node<AnyType> {
        public AnyType data;
        public Node prev;
        public Node next;

        public Node(AnyType data, Node prev, Node next) {
            this.data = data;
            this.prev = prev;
            this.next = next;
        }
    }

    private int theSize;
    private int modCount = 0;
    private Node beginMarker;
    private Node endMarker;


    public MyLinkedList() {
        doClear();
    }

    public void clear() {
        doClear();
    }

    public void doClear() {
        beginMarker = new Node(null, null, null);
        endMarker = new Node(null, beginMarker, null);
        beginMarker.next = endMarker;
        theSize = 0;
        modCount++;
    }

    public int size() {
        return theSize;
    }

    public boolean isEmpty() {
        return theSize == 0;
    }

    public boolean add(AnyType data) {
        add(theSize, data);
        return true;
    }

    public void add(int idx, AnyType data) {
        addBefore(getNode(idx, 0, theSize), data);
    }

    public AnyType get(int idx) {
        return (AnyType) getNode(idx).data;
    }

    public AnyType set(int idx, AnyType data) {
        Node node = getNode(idx);
        AnyType oldVal = (AnyType) node.data;
        node.data = data;
        return oldVal;
    }

    public AnyType remove(int idx) {
        return remove(getNode(idx));
    }

    public void addBefore(Node node, AnyType data) {
        //要更改三个节点的四条信息
        Node newNode = new Node(data, node.prev, node);
        newNode.prev.next = newNode;
        node.prev = newNode;
        theSize++;
        modCount++;
    }

    public AnyType remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        theSize--;
        modCount++;
        return (AnyType) node.data;
    }

    public Node getNode(int idx) {
        return getNode(idx, 0, theSize - 1);
    }

    public Node getNode(int idx, int lower, int upper) {
        Node node;
        if (idx < lower || idx > upper) throw new IndexOutOfBoundsException();
        if (idx < theSize / 2) {
            node = beginMarker.next;
            for (int i = 0; i < idx; i++)
                node = node.next;
        } else {
            node = endMarker;
            for (int i = theSize; i > idx; i--)
                node = node.prev;
        }
        return node;
    }

    public Iterator iterator() {
        return new MyLinkedListIterator();
    }

    private class MyLinkedListIterator implements Iterator {
        private Node current = beginMarker.next;
        private int expectedModCount = modCount;
        private boolean okToRemove = false;

        public boolean hasNext() {
            return current != endMarker;
        }

        public AnyType next() {
            if (modCount != expectedModCount) throw new ConcurrentModificationException();
            if (!hasNext()) throw new NoSuchElementException();

            AnyType nextItem = (AnyType) current.data;
            current = current.next;
            okToRemove = true;
            return nextItem;
        }

        public void remove() {
            if (modCount != expectedModCount) throw new ConcurrentModificationException();
            if (!okToRemove) throw new IllegalStateException();

            MyLinkedList.this.remove(current.prev);
            expectedModCount++;
            okToRemove = false;
        }
    }


}
