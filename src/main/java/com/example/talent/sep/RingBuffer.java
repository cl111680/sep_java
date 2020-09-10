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
		int newTail = tail + 1;

		return (newTail == bufferSize?0:newTail) == head;
	}

	public boolean put(Node v) {
		if (full()) {
			get();
		}
		buffer[tail] = v;
		int newTail = tail + 1;
		tail = newTail == bufferSize?0:newTail;
		return true;
	}

	public Node get() {
		if (empty()) {
			return null;
		}
		Node result = buffer[head];
		int newHead = head + 1;
		head = newHead == bufferSize?0:newHead;
		return result;
	}

	public boolean hasWord(char ch) {
		if (empty()) {
			return false;
		}
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
		int newHead = 0;
		if (pos == head) {
			newHead = head + 1;
			head = newHead == bufferSize?0:newHead;
			if (head < tail) {
				for (int i = head; i < tail; i++) {
					if (buffer[i] == null) {
						newHead = head + 1;
						head = newHead == bufferSize?0:newHead;
					} else {
						break;
					}
				}
			} else {
				for (int i = head; i < bufferSize; i++) {
					if (buffer[i] == null) {
						newHead = head + 1;
						head = newHead == bufferSize?0:newHead;
					} else {
						break;
					}
				}
				for (int i = 0; i < tail; i++) {
					if (buffer[i] == null) {
						newHead = head + 1;
						head = newHead == bufferSize?0:newHead;
					} else {
						break;
					}
				}
			}
		}
	}
}