package com.example.talent.sep;


import java.util.Arrays;

import com.example.talent.sep.Trie.Node;
 
public class RingBuffer {
 
    private final static int DEFAULT_SIZE  = 1000;
    private Node[] buffer;
    private int head = 0;
    private int tail = 0;
    private int bufferSize;
    private Trie trie;
    
    public RingBuffer(Trie trie){
        this.bufferSize = DEFAULT_SIZE;
        this.buffer = new Node[bufferSize];
        this.trie = trie;
    }
 
    private Boolean empty() {
        return head == tail;
    }
 
    private Boolean full() {
        return (tail + 1) % bufferSize == head;
    }
 
    public void clear(){
        Arrays.fill(buffer,null);
        this.head = 0;
        this.tail = 0;
    }
 
    public Boolean put(Node v) {
        if (full()) {
        	get() ;
        }
        buffer[tail] = v;
        tail = (tail + 1) % bufferSize;
        return true;
    }
 
    public Node get() {
        if (empty()) {
            return null;
        }
        Node result = buffer[head];
        head = (head + 1) % bufferSize;
        return result;
    }
 
    public Node[] getAll() {
        if (empty()) {
            return new Node[0];
        }
        int copyTail = tail;
        int cnt = head < copyTail ? copyTail - head : bufferSize - head + copyTail;
        Node[] result = new Node[cnt];
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
 
    public boolean hasWord(char ch) {
    	if (empty()) {
            return false;
        }
    	//System.out.println("head="+head +";tail=" + tail);
    	boolean hasWord =false;
        int copyTail = tail;
        Node newNode = null;
        if (head < copyTail) {
            for (int i = head; i < copyTail; i++) {
                if (buffer[i]!=null) {
                	newNode = trie.getNext(buffer[i], ch);
                	if (newNode!=null&&newNode.isWord) {
                		hasWord = true;
                		buffer[i]=null;
                		if (i==head)head=(head + 1) % bufferSize;
                	}else {
                		buffer[i]=newNode;
                		 get();
                	}
                }
            }
        } else {
            for (int i = head; i < bufferSize; i++) {
            	 if (buffer[i]!=null) {
                 	newNode = trie.getNext(buffer[i], ch);
                 	if (newNode!=null&&newNode.isWord) {
                 		hasWord = true;
                 		buffer[i]=null;
                 		if (i==head)head=(head + 1) % bufferSize;
                 	}else {
                 		buffer[i]=newNode;
                 		 get();
                 	}
                 }
            }
            for (int i = 0; i < copyTail; i++) {
            	 if (buffer[i]!=null) {
                 	newNode = trie.getNext(buffer[i], ch);
                 	if (newNode!=null&&newNode.isWord) {
                 		hasWord = true;
                 		buffer[i]=null;
                 		if (i==head)head=(head + 1) % bufferSize;
                 	}else {
                 		buffer[i]=newNode;
                 		 get();
                 	}
                 }
            }
        }
		return hasWord;
    }
}