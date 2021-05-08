import java.nio.BufferUnderflowException;
import java.util.Iterator;

public class RingBufferImpl<E> implements RingBuffer<E> {

    private E[] buffer;

    public E[] getBuffer() {
        return buffer;
    }

    private int head;

    public int getHead() {
        return head;
    }

    private int count;

    private int faсt_count;

    public int getFaсt_count() {
        return faсt_count;
    }

    public RingBufferImpl(int size) {
        buffer = (E[]) new Object[size];
        count = 0;
        head = 0;
    }

    @Override
    public E poll() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        E res = buffer[head];
        head = (head+1)%buffer.length;
        count--;
        faсt_count--;
        return res;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new BufferUnderflowException();
        }
        return buffer[head];
    }

    @Override
    public void add(E item) {
        int tail = (head + faсt_count)%buffer.length;
        buffer[tail] = item;
        if (!isFull()) { count++; }
        faсt_count++;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == buffer.length;
    }

    @Override
    public int getSize() {
        return count;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {

            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < buffer.length && buffer[currentIndex] != null;
            }

            @Override
            public E next() {
                return buffer[currentIndex++];
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    public String toString() {
        return "CircularBuffer(size=" + buffer.length + ", head=" + head + ", count=" + count + ")";
    }

}

