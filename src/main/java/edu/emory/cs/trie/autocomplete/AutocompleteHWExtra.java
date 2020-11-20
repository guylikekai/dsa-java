package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.TrieNode;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class AutocompleteHWExtra extends Autocomplete<List<Word>> {
    private int longest;
    public AutocompleteHWExtra(String dict_file, int max) {
        super(dict_file, max);

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dict_file)));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.length() > longest) longest = line.length();
            }

        } catch (IOException ignored) {}
    }


    @Override
    public List<String> getCandidates(String prefix) {
        prefix = prefix.trim();
        TrieNode<List<Word>> origPrefixNode = find(prefix);

        if (origPrefixNode == null) {
            put(prefix, null);
            find(prefix).setEndState(false);
            return List.of("This prefix does not exist in the dictionary; has been inputted endState = false");
        }

        if (origPrefixNode.getValue() == null) {
            origPrefixNode.setValue(new ArrayList<>());
        }
        int x = prefix.length();
        while (origPrefixNode.getValue().size() < getMax() && x < longest) {
            getCandidates(prefix, origPrefixNode, x);
            x++;
        }

            List<String> sub = new ArrayList<>();
            int size = origPrefixNode.getValue().size() >= getMax() ? getMax() : origPrefixNode.getValue().size();

            System.out.println(origPrefixNode.getValue().size());
            for (int i = 0; i < size ; i++) {
                sub.add(origPrefixNode.getValue().get(i).getKey());
            }
            return sub;
    }


    public void getCandidates(String currPrefix, TrieNode<List<Word>> origPrefixNode, int x) {
        if (currPrefix.length() > x) return;
        List<Word> temp;
        List<Word> origPrefixValue = origPrefixNode.getValue();
        if (origPrefixValue.size() >= getMax()) return;

        if (checkEnd(currPrefix).isEmpty()) {
            temp = checkExisting(currPrefix);
            for (Word word : temp) {
                getCandidates(word.getKey(), origPrefixNode, x);
            }
        } else temp = checkEnd(currPrefix);

        for (Word word : temp) {
            boolean doIt = true;
            for (Word k : origPrefixValue) {
                if (k.getKey().equals(word.getKey())) {
                    doIt = false;
                    break;
                }

            }
            if (doIt && find(word.getKey()).isEndState()) origPrefixValue.add(word);
        }

        origPrefixNode.setValue(origPrefixValue);

            temp = checkExisting(currPrefix);
            for (Word word : temp) {
                getCandidates(word.getKey(), origPrefixNode, x);
        }

    }

    public List<Word> checkEnd(String parent) {
        List<Word> result = new ArrayList();
        if (find(parent) == null) return result;
        if (find(parent).isEndState()) result.add(Word.toWord(parent));
        for (char alpha = 'a'; alpha < '{'; alpha++) {
            if (find(parent + alpha)!= null && find(parent + alpha).isEndState()) {
                result.add(Word.toWord(parent + alpha));
            }
        }
        return result;
    }

    public List<Word> checkExisting(String parent) {
        List<Word> result = new ArrayList();
        for (char alpha = 'a'; alpha < '{'; alpha++) {
            if (find(parent + alpha) != null) {
                result.add(Word.toWord(parent + alpha));
            }
        }
        return result;
    }


    @Override
    public void pickCandidate(String prefix, String cand) {
        Word candidate = Word.toWord(cand);
        List<Word> lst = new ArrayList<>();
        lst.add(candidate);
        if (find(prefix) == null) {
            put(prefix, lst);
            find(prefix).setEndState(false);
        } else {
            if (find(prefix).hasValue()) {
                List<Word> temp = find(prefix).getValue();
                for (Word word : temp) {
                    if (candidate.equals(word)) {
                        candidate = word;

                        temp.remove(word);
                        break;
                    }
                }
                int index = 0;
                for (Word word: temp) {
                    if (candidate.getFrequency() + 1 < word.getFrequency()) {
                        index = temp.indexOf(word) + 1;
                    } else {
                        break;
                    }
                }
                temp.add(index, candidate);
                find(prefix).setValue(temp);
            } else {
                find(prefix).setValue(lst);
            }
        }

        if (find(candidate.getKey()) == null) put(candidate.getKey(), null);
        else find(candidate.getKey()).setEndState(true);
        if (candidate.length() > longest) longest = candidate.length();

        candidate.increment();
    }

}



class Word {
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