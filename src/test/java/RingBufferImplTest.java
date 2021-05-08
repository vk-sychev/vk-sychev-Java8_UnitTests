
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;

import java.nio.BufferUnderflowException;

class RingBufferImplTest {

    static String testElem = "test";
    static String testElem1 = "test1";
    static String testElem2 = "test2";
    static String testElem3 = "test3";

    @org.junit.jupiter.api.Test
    void pollBufferUnderflowException() {
        RingBufferImpl<String> b = new RingBufferImpl<>(3);
        Assertions.assertThrows(BufferUnderflowException.class, () -> b.poll());
    }

    @org.junit.jupiter.api.Test
    void poll() {
        RingBufferImpl<String> b = new RingBufferImpl<>(3);
        b.add(testElem);
        b.add(testElem1);
        b.add(testElem2);
        b.add(testElem3);
        String res = b.poll();
        Assert.assertEquals(testElem3, res);
    }

    @org.junit.jupiter.api.Test
    void peek() {
        RingBufferImpl<String> b = new RingBufferImpl<>(3);
        b.add(testElem);
        b.add(testElem1);
        b.add(testElem2);
        b.add(testElem3);
        b.peek();
        b.peek();
        String res = b.peek();
        Assert.assertEquals(testElem3, res);
    }

    @org.junit.jupiter.api.Test
    void peekBufferUnderflowException() {
        RingBufferImpl<String> b = new RingBufferImpl<String>(3);
        Assertions.assertThrows(BufferUnderflowException.class, () -> b.peek());
    }

    @org.junit.jupiter.api.Test
    void add() {
        RingBufferImpl<String> b = new RingBufferImpl<>(3);
        b.add(testElem);
        b.add(testElem1);
        b.add(testElem2);
        b.add(testElem3);
        b.add("textElem4");
        Object[] buffer = b.getBuffer();
        var elem = buffer[(b.getHead() + b.getFaсt_count()-1)%buffer.length];
        Assert.assertEquals(elem, "textElem4");
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        RingBufferImpl<String> b = new RingBufferImpl<>(3);
        Assert.assertTrue(b.isEmpty());

        b.add(testElem);
        Assert.assertFalse(b.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void isFull() {
        RingBufferImpl<String> b = new RingBufferImpl<>(3);
        Assert.assertFalse(b.isFull());

        b.add(testElem);
        Assert.assertFalse(b.isFull());

        b.add(testElem1);
        b.add(testElem2);
        Assert.assertTrue(b.isFull());

        b.add(testElem3);
        Assert.assertTrue(b.isFull());
    }

    @org.junit.jupiter.api.Test
    void getSize() {
        RingBufferImpl<String> b = new RingBufferImpl<>(3);
        Assert.assertEquals(0, b.getSize());

        b.add(testElem);
        b.add(testElem1);
        Assert.assertEquals(2, b.getSize());

        b.add(testElem2);
        b.add(testElem3);
        Assert.assertEquals(3, b.getSize());
    }
}