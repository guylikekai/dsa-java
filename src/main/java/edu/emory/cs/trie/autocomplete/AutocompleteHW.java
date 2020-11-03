package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.TrieNode;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class AutocompleteHW extends Autocomplete<List<String>> {
   private int longest;
    public AutocompleteHW(String dict_file, int max) {
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
        TrieNode<List<String>> origPrefixNode = find(prefix);

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

        if (origPrefixNode.getValue().size() >= getMax()) return origPrefixNode.getValue().subList(0, getMax());
        else return origPrefixNode.getValue();
    }


    public void getCandidates(String currPrefix, TrieNode<List<String>> origPrefixNode, int x) {
        if (currPrefix.length() > x) return;
        List <String> temp;
        List<String> origPrefixValue = origPrefixNode.getValue();
        if (origPrefixValue.size() >= getMax()) return;

        if (checkEnd(currPrefix).isEmpty()) {
            temp = checkExisting(currPrefix);
            for (String word : temp) {
                getCandidates(word, origPrefixNode, x);
            }
        } else temp = checkEnd(currPrefix);

            for (String word : temp) {
                if (origPrefixValue.contains(word)) continue;
                origPrefixValue.add(word);
            }

            origPrefixNode.setValue(origPrefixValue);
            if (origPrefixValue.size() >= getMax() || !find(currPrefix).hasChildren()) return;
            else {
                temp = checkExisting(currPrefix);
                for (String word : temp) {
                    getCandidates(word, origPrefixNode, x);
                }
            }

    }

    public List<String> checkEnd(String parent) {
        List<String> result = new ArrayList();
        if (find(parent) == null) return result;
        if (find(parent).isEndState()) result.add(parent);
        for (char alpha = 'a'; alpha < '{'; alpha++) {
            if (find(parent + alpha)!= null && find(parent + alpha).isEndState()) {
                result.add(parent + alpha);
            }
        }
        return result;
    }

    public List<String> checkExisting(String parent) {
        List<String> result = new ArrayList();
        for (char alpha = 'a'; alpha < '{'; alpha++) {
            if (find(parent + alpha) != null) {
                result.add(parent + alpha);
            }
        }
        return result;
    }


    @Override
    public void pickCandidate(String prefix, String candidate) {
        if (find(prefix) == null) {
            put(prefix, List.of(candidate));
            find(prefix).setEndState(false);
        } else {
            if (find(prefix).hasValue()) {
                List<String> temp = find(prefix).getValue();
                if (find(prefix).getValue().contains(candidate)) {
                    temp.remove(candidate);
                }
                temp.add(0, candidate);
                find(prefix).setValue(temp);
            } else {
                find(prefix).setValue(List.of(candidate));
            }
        }

        if (find(candidate) == null) put(candidate, null);
        else find(candidate).setEndState(true);
        if (candidate.length() > longest) longest = candidate.length();
    }

}