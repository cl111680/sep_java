package com.example.talent.sep;

import java.util.TreeMap;

public class Trie {
    private class Node{
        public boolean isWord;
        public TreeMap<Character,Node> next;

        public Node(boolean isWord){
            this.isWord = isWord;
            this.next = new TreeMap<>();
        }

        public Node(){
            this(false);
        }
    }

    private Node root;
    private int size;

    public Trie(){
        this.root = new Node();
        this.size = 0;
    }

	/**
	 * 返回Trie节点数
	 */
    public int getSize(){
        return this.size;
    }
    /**
     * 添加单词
     * @param word
     */
    public void add(String word){
        Node curr = root;
        for(int i=0;i<word.length();i++){
            char c = word.charAt(i);
            if(curr.next.get(c)==null){
                curr.next.put(c,new Node());//没有该节点，直接添加新的
            }
            curr = curr.next.get(c);//有该节点，移动curr，继续查找下一节点
        }
        if(!curr.isWord){//如果之前已经存在该单词了size不能加1
            curr.isWord = true;
            size++;
        }
    }
    /**
     * 查询单词
     */
    public boolean contains(String word){
        Node curr = root;
        for(int i=0;i<word.length();i++){
            char c = word.charAt(i);
            if(curr.next.get(c)==null){
                return false;//无下一个值的节点，直接返回false
            }
            curr = curr.next.get(c);
        }
        return curr.isWord;//单词到达末尾，根据isWord标志判断
    }
    /**
     * 查询是否在Trie中有单词以prefix为前缀
     * @param prefix
     * @return
     */
    public boolean isPrefix(String prefix){
        Node curr = root;
        for(int i=0;i<prefix.length();i++){
            char c = prefix.charAt(i);
            if(curr.next.get(c)==null){
                return false;
            }
            curr = curr.next.get(c);
        }
        return true;
    }
}