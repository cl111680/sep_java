package com.example.talent.sep;

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
    	ring=new RingBuffer();
    }

    public boolean query(char ch) {
    	ring.put(ch);
        return false;
    }
}
