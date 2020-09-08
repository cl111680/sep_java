package com.example.talent.sep;


import java.util.Arrays;
 
public class RingBuffer {
 
    private final static int DEFAULT_SIZE  = 1000;
    private char[] buffer;
    private int head = 0;
    private int tail = 0;
    private int bufferSize;
 
    public RingBuffer(){
        this.bufferSize = DEFAULT_SIZE;
        this.buffer = new char[bufferSize];
    }
 
    private Boolean empty() {
        return head == tail;
    }
 
    private Boolean full() {
        return (tail + 1) % bufferSize == head;
    }
 
    public void clear(){
        Arrays.fill(buffer,'\0');
        this.head = 0;
        this.tail = 0;
    }
 
    public Boolean put(char v) {
        if (full()) {
            return false;
        }
        buffer[tail] = v;
        tail = (tail + 1) % bufferSize;
        return true;
    }
 
    public Object get() {
        if (empty()) {
            return null;
        }
        Object result = buffer[head];
        head = (head + 1) % bufferSize;
        return result;
    }
 
    public Object[] getAll() {
        if (empty()) {
            return new Object[0];
        }
        int copyTail = tail;
        int cnt = head < copyTail ? copyTail - head : bufferSize - head + copyTail;
        Object[] result = new String[cnt];
        if (head < copyTail) {
            for (int i = head; i < copyTail; i++) {
                result[i - head] = buffer[i];
            }
        } else {
            for (int i = head; i < bufferSize; i++) {
                result[i - head] = buffer[i];
            }
            for (int i = 0; i < copyTail; i++) {
                result[bufferSize - head + i] = buffer[i];
            }
        }
        head = copyTail;
        return result;
    }
 
}