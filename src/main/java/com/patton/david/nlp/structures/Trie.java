package com.patton.david.nlp.structures;

import java.util.*;

public class Trie {

    private static Trie endOfWord;
    public static Trie EndOfWord() { //Save space with singleton EOW
        if (endOfWord == null) {
            endOfWord = new Trie('\0');
        }
        return endOfWord;
    }

    private Character element;
    public Character getElement() {
        return this.element;
    }

    private Map<Trie, Trie> edges;

    private Trie previousEdge;
    private Trie getPreviousEdge() {
        return this.previousEdge;
    }
    private void setPreviousEdge(Trie previousEdge) {
        this.previousEdge = previousEdge;
    }

    public Trie(Character element) {
        this.element = element;
        this.edges = new HashMap<Trie, Trie>();
    }

    public boolean isEndOfWord() {
        return hasNext(Trie.EndOfWord());
    }

    public boolean isWhiteSpace() {
        return this.element != null && Character.isWhitespace(this.element);
    }

    public Trie put(Character c) {
        return put(new Trie(c));
    }

    public Trie put(Trie targetEdge) {
        if (!hasNext(targetEdge)) {
            this.edges.put(targetEdge, targetEdge);
            targetEdge.setPreviousEdge(this);
        }
        Trie result = getNext(targetEdge);
        for (Trie targetEdgeOfEdge : targetEdge.edges.values()) {
            result.put(targetEdgeOfEdge); //recursively add edges of edge
        }
        return result;
    }

    public boolean hasNext() {
        return this.edges.size() > 1 || (this.edges.size() > 0 && !isEndOfWord());
    }

    public boolean hasNext(Character element) {
        return hasNext(element, false);
    }
    public boolean hasNext(Character element, boolean ignoreCase) {
        return hasNext(new Trie(element), ignoreCase);
    }

    public boolean hasNext(Trie edge) {
        return hasNext(edge, false);
    }

    public boolean hasNext(Trie edge, boolean ignoreCase) {
        if (this.edges.containsKey(edge)) {
            return true;
        } else if (ignoreCase) {
            //ignoreCase == true, so check opposite character case
            if (Character.isUpperCase(edge.getElement())) {
                return this.edges.containsKey(new Trie(Character.toLowerCase(edge.getElement())));
            } else {
                return this.edges.containsKey(new Trie(Character.toUpperCase(edge.getElement())));
            }
        } else {
            return false;
        }
    }

    public Trie getNext(Character element) {
        return getNext(element, false);
    }

    public Trie getNext(Character element, boolean ignoreCase) {
        return getNext(new Trie(element), ignoreCase);
    }

    public Trie getNext(Trie edge) {
        return getNext(edge, false);
    }

    public Trie getNext(Trie edge, boolean ignoreCase) {
        if (!hasNext(edge, ignoreCase)) {
            return null;
        }
        Trie result = this.edges.get(edge);
        if (result == null) {
            //hasNext == true, so get opposite character case
            if (Character.isUpperCase(edge.getElement())) {
                result = this.edges.get(new Trie(Character.toLowerCase(edge.getElement())));
            } else {
                result = this.edges.get(new Trie(Character.toUpperCase(edge.getElement())));
            }
        }
        return result;
    }

    public Collection<Trie> getNextCollection() {
        return this.edges.values();
    }

    public List<Trie> getNextList() {
        return new ArrayList<Trie>(getNextCollection());
    }

    public Set<Trie> getNextSet() {
        return new HashSet<Trie>(getNextCollection());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        Trie target = (Trie) obj;
        return (this.element != null && this.element.equals(target.getElement())) || //general equality check
                this.element == target.getElement(); //check if both are null
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        if (this.element == null) {
            return prime - 1; //null == -1
        }
        return prime + this.element.charValue(); //Add 0-65535
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Trie last = this;
        do {
            if (last.getElement() != null) {
                builder.append(last.getElement());
            }
        } while ((last = last.getPreviousEdge()) != null && last.getElement() != null);
        builder.reverse();
        return builder.toString();
    }
}
