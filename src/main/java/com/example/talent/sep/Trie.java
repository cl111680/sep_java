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
	private int size;

	public Trie() {
		this.root = new Node();
		this.size = 0;
	}

	/**
	 * 返回Trie节点数
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * 添加单词
	 * 
	 * @param word
	 */
	public void add(String word) {
		Node curr = root;
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			if (curr.next.get(c) == null) {
				curr.next.put(c, new Node());// 没有该节点，直接添加新的
			}
			curr = curr.next.get(c);// 有该节点，移动curr，继续查找下一节点
		}
		if (!curr.isWord) {// 如果之前已经存在该单词了size不能加1
			curr.isWord = true;
			size++;
		}
	}

	/**
	 * 如果输入字符
	 * @param curr
	 * @param c
	 * @return
	 */
	public  Node getNext(Node curr, char c) {
		if (curr==null) {
			curr = root;
		}
		if (curr.next.get(c) == null) {
			return null;
		}
		return curr.next.get(c);
	}
}