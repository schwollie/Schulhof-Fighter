package logic;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Queue<T> implements Iterable<T>{

    private QueueElement<T> start;
    private QueueElement<T> end;

    private int size = 0;

    public void enqueue(T t) {
        size++;
        QueueElement<T> newT = new QueueElement<T>(t);

        if (start == null) {
            start = newT;
            end = newT;
        } else {
            end.setNext(newT);
            end = newT;
        }
    }

    public T dequeue() {
        if (size == 0) { return null; }

        size--;
        QueueElement<T> currentStart = start;

        start = start.getNext();
        if (start == null) { end = null; }

        return currentStart.getData();
    }

    public boolean contains(T obj) {
        Iterator<T> it = this.iterator();
        while (it.hasNext()) {
            T element = it.next();
            if (obj.equals(element)) {
                return true;
            }
        }
        return false;
    }

    public T getStart() {
        return start.getData();
    }

    public T getEnd() {
        return end.getData();
    }

    public int size() {
        return size;
    }

    public void flush() {
        this.start = null;
        this.end = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        String element = "Queue = { ";
        QueueElement<T> currentE = start;

        for (int i = 0; i < size(); i++) {
            element += currentE.getData().toString() + ", ";
            currentE = currentE.getNext();
        }

        element += "} ";

        return element;

    }

    @Override
    public Iterator<T> iterator() {
        Iterator<T> it = new Iterator<T>() {

            private QueueElement<T> currentE = start;

            @Override
            public boolean hasNext() {
                return currentE != null;
            }

            @Override
            public T next() {
                T data = currentE.getData();
                currentE = currentE.getNext();

                return data;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }


        };
        return it;
    }

    public static void main(String[] args) {
        Queue<Integer> q = new Queue<>();
        q.enqueue(0);
        q.enqueue(1);
        q.enqueue(2);
        //q.dequeue();
        //q.dequeue();
        //q.dequeue();
        boolean c = q.contains(2);
        System.out.println(c);
    }
}

class QueueElement<T> {

    private T data;

    private QueueElement<T> next;

    public QueueElement(T t) {
        data = t;
    }

    public T getData() {
        return data;
    }

    public void setNext(QueueElement<T> next) {
        this.next = next;
    }

    public QueueElement<T> getNext() {
        return next;
    }
}
