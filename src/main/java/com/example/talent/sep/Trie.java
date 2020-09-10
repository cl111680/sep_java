package com.example.talent.sep;

import java.util.HashMap;

public class Trie {
	public class Node {
		public boolean isWord;
		public HashMap<Character, Node> next;

		public Node(boolean isWord) {
			this.isWord = isWord;
			this.next = new HashMap<>();
		}

		public Node() {
			this(false);
		}
	}

	private Node root;

	public Trie() {
		this.root = new Node();
	}

	public void add(String word) {
		Node curr = root;
		Node next = null;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			next = curr.next.get(c);
			if (next == null) {
				next = new Node();
				curr.next.put(c, next);
			}
			curr =next;
		}
		if (!curr.isWord) {
			curr.isWord = true;
		}
	}

	public Node getNext(Node curr, char c) {
		return curr.next.get(c);
	}
	public Node getNewNext(char c) {
		return root.next.get(c);
	}
}