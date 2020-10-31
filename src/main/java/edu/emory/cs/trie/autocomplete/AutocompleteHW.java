package edu.emory.cs.trie.autocomplete;

import edu.emory.cs.trie.TrieNode;


import java.util.ArrayList;
import java.util.List;


/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class AutocompleteHW extends Autocomplete<List<String>> {
    public AutocompleteHW(String dict_file, int max) {
        super(dict_file, max);
    }

    @Override
    public List<String> getCandidates(String prefix) {
        System.out.println("hi");
        List<String> temp;
        TrieNode<List<String>> origPrefixNode = find(prefix);
        System.out.println("out");

        if (origPrefixNode.isEndState()) {
            System.out.println("yes");
            temp = origPrefixNode.getValue();
            origPrefixNode.setValue(temp);
        }

        System.out.println("no");
        getCandidates(prefix, origPrefixNode);
        System.out.println(origPrefixNode.getValue());
        return origPrefixNode.getValue().subList(0, getMax());

    }

    public void getCandidates(String currPrefix, TrieNode<List<String>> origPrefixNode) {
        List<String> origPrefixValue = origPrefixNode.getValue();
        List <String> temp;

        if (origPrefixValue.size() >= getMax()) return;
        if (checkEnd(currPrefix).isEmpty()) return;
        temp = checkEnd(currPrefix);
        for (String x : temp) {
            if (origPrefixValue.contains(x)) continue;
            origPrefixValue.add(x);
        }
        origPrefixNode.setValue(origPrefixValue);
        if (origPrefixValue.size() >= getMax() || !find(currPrefix).hasChildren()) return;
        else {
            temp = checkExisting(currPrefix);
            for (String x : temp) {
                getCandidates(x, origPrefixNode);
            }
        }
    }

    public List<String> checkEnd(String parent) {
        List<String> result = new ArrayList();
        for (char alpha = 'a'; alpha < '{'; alpha++) {
            if (find(parent + alpha).isEndState()) {
                result.add(parent + alpha);
            }
        }
        return result;
    }

    public List<String> checkExisting(String parent) {
        List<String> result = new ArrayList();
        for (char alpha = 'a'; alpha < '{'; alpha++) {
            if (find(parent + alpha) != null && find(parent + alpha).hasChildren()) {
                result.add(parent + alpha);
            }
        }
        return result;
    }


    @Override
    public void pickCandidate(String prefix, String candidate) {
        if (find(prefix) != null) {
            if (find(prefix).hasValue()) {
                List<String> temp = find(prefix).getValue();
                if (find(prefix).getValue().contains(candidate)) {
                    temp.remove(candidate);
                }
                temp.add(0, candidate);
                find(prefix).setValue(temp);
                return;
            }

            find(prefix).setValue(List.of(candidate));
        }
    }

}