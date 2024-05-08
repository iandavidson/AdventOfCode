package PrefixTree;

import java.util.HashMap;
import java.util.Map;


public class PrefixTree {

    public static void main(String[] args) {
        Trie trie = new Trie();
        trie.insert("apple");
        System.out.println(trie.search("apple"));   // return True
        System.out.println(trie.search("app"));     // return False
        System.out.println(trie.startsWith("app")); // return True
        trie.insert("app");
        System.out.println(trie.search("app"));     // return True
    }

    static class Trie {
        private Boolean validWord;
        private final Map<Character, Trie> children;

        public Trie() {
            this.children = new HashMap<>();
            this.validWord = false;
        }

        public void insert(String word) {
//            System.out.println("insert, word: " + word);
            if (word.isEmpty()) {
                this.validWord = true;
                return;
            }

            // get first character,
            Character first = word.charAt(0);
            if (children.containsKey(first)) {
                // recursively call
                children.get(first).insert(word.substring(1));
            } else {
                children.put(first, new Trie());
                children.get(first).insert(word.substring(1));
            }
        }

        public boolean search(String word) {
            if (word.isEmpty()) {
                return validWord;
            }

            Character first = word.charAt(0);

            if (children.containsKey(first)) {
                return children.get(first).search(word.substring(1));
            } else {
                return false;
            }
        }

        public boolean startsWith(String prefix) {
            // System.out.println("starts with: current prefix: " + prefix);

            if (prefix.isEmpty()) {
                return true;
            }

            Character first = prefix.charAt(0);

            if (children.containsKey(first)) {
                return children.get(first).startsWith(prefix.substring(1));
            } else {
                return false;
            }
        }
    }

}
