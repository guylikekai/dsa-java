package edu.emory.cs.trie.autocomplete;

public class Word {
    private String key;
    private int frequency;

    public Word(String key, int frequency) {
        setKey(key);
        setFrequency(frequency);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getFrequency() {
        return frequency;
    }

    public static Word toWord (String word) {
        return new Word(word, 0);
    }

    public int length() {
        return key.length();
    }

    public void increment() {
        frequency++;
    }

    public boolean equals(Word word) {
        return word.getKey() == key;
    }
}
