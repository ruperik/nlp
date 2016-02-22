package com.patton.david.nlp.structures;

import java.util.Set;

public class Dictionary {

    private Trie root;
    public Trie getRoot() {
        return this.root;
    }

    public Dictionary() {
        root = new Trie(null);
    }

    public void put(String token) {
        Trie last = root;
        for (char c : token.toCharArray()) {
            last = last.put(c);
        }
        last.put(Trie.EndOfWord());
    }

    /*
     *  Finds the potential suffixes for the given prefix within this dictionary.
     *  Ex. Dictionary contains barn, base, and brain.
     *      If you pass ba, you will get rn, se.
     *      If you pass b, you will get arn, ase, rain.
     *      If you pass br, you will get ain.
     */
    public Set<Trie> getSuffixes(String prefix, boolean ignoreCase) {
        Trie last = root;
        int i = 0;
        for (char c : prefix.toCharArray()) {
            if (!last.hasNext(c, ignoreCase)) {
                return null;
            }
            last = last.getNext(c, ignoreCase);
        }
        return last.getNextSet();
    }
}
