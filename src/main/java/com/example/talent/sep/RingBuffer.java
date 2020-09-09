package com.example.talent.sep;

import com.example.talent.sep.Trie.Node;

public class RingBuffer {

	private final static int DEFAULT_SIZE = 1000;
	private Node[] buffer;
	private int head = 0;
	private int tail = 0;
	private int bufferSize;
	private Trie trie;

	public RingBuffer(Trie trie) {
		this.bufferSize = DEFAULT_SIZE;
		this.buffer = new Node[bufferSize];
		this.trie = trie;
	}

	private boolean empty() {
		return head == tail;
	}

	private boolean full() {
		return (tail + 1) % bufferSize == head;
	}

	public boolean put(Node v) {
		if (full()) {
			get();
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

	public boolean hasWord(char ch) {
		if (empty()) {
			return false;
		}
		// System.out.println("head="+head +";tail=" + tail);
		boolean hasWord = false;
		if (head < tail) {
			for (int i = head; i < tail; i++) {
				if (doNextWord(i, ch)) {
					hasWord = true;
				}
			}
		} else {
			for (int i = head; i < bufferSize; i++) {
				if (buffer[i] != null) {
					if (doNextWord(i, ch)) {
						hasWord = true;
					}
				}
			}
			for (int i = 0; i < tail; i++) {
				if (buffer[i] != null) {
					if (doNextWord(i, ch)) {
						hasWord = true;
					}
				}
			}
		}
		return hasWord;
	}

	private boolean doNextWord(int pos, char ch) {
		boolean hasWord = false;

		if (buffer[pos] != null) {
			Node newNode = trie.getNext(buffer[pos], ch);
			if (newNode != null) {
				if (newNode.isWord) {
					hasWord = true;
					buffer[pos] = null;
					moveHead(pos);
				} else {
					buffer[pos] = newNode;
				}
			} else {
				buffer[pos] = null;
				moveHead(pos);
			}
		}
		return hasWord;
	}

	private void moveHead(int pos) {
		if (pos == head)
			head = (head + 1) % bufferSize;
		if (head < tail) {
			for (int i = head; i < tail; i++) {
				if (buffer[i] == null) {
					head = (head + 1) % bufferSize;
				} else {
					break;
				}
			}
		} else {
			for (int i = head; i < bufferSize; i++) {
				if (buffer[i] == null) {
					head = (head + 1) % bufferSize;
				} else {
					break;
				}
			}
			for (int i = 0; i < tail; i++) {
				if (buffer[i] == null) {
					head = (head + 1) % bufferSize;
				} else {
					break;
				}
			}
		}
	}
}