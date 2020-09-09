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
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (curr.next.get(c) == null) {
				curr.next.put(c, new Node());
			}
			curr = curr.next.get(c);
		}
		if (!curr.isWord) {
			curr.isWord = true;
		}
	}

	public Node getNext(Node curr, char c) {
		if (curr == null) {
			curr = root;
		}
		if (curr.next.get(c) == null) {
			return null;
		}
		return curr.next.get(c);
	}
}