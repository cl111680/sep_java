package com.example.talent.sep;

import com.example.talent.sep.Trie.Node;

public class StreamAlerter {
    private RingBuffer ring;
    private Trie trie;

    public StreamAlerter(String[] strings) {
    	trie = new Trie();
    	if (strings!=null) {
    		for (String word:strings) {
    			trie.add(word);
    		}
    	}
    	ring=new RingBuffer(trie);
    }

    public boolean query(char ch) {
    	boolean result = false;
    	if (ring.hasWord(ch)) {
    		result = true; 
    	}
    	Node newNode = trie.getNewNext( ch);
    	if (newNode!=null) {
        	ring.put(newNode);
        	if (newNode.isWord) {
        		result = true;
        	}
    	}
    	return result;
    }
}
