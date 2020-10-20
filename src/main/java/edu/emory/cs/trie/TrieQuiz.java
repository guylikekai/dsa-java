package edu.emory.cs.trie;

import java.util.ArrayList;
import java.util.List;

public class TrieQuiz extends Trie<Integer> {
    /**
     * PRE: this trie contains all country names as keys and their unique IDs as values
     * (e.g., this.get("United States") -> 0, this.get("South Korea") -> 1).
     * @param input the input string in plain text
     *              (e.g., "I was born in South Korea and raised in the United States").
     * @return the list of entities (e.g., [Entity(14, 25, 1), Entity(44, 57, 0)]).
     */

    List<Entity> getEntities(String input) {
        List<Entity> result = new ArrayList<>();
        char[] inputArray = input.toCharArray();
        TrieNode<Integer> node = getRoot();
        for (int i = 0; i < inputArray.length; i++){
            if (node != null) {
                node = node.getChild(inputArray[i]);
                i = check(node, i, inputArray, result);
                node = getRoot();
            }

        }


        //  this is a dummy return statement
        //return List.of(new Entity(44, 57, 0), new Entity(14, 25, 1));
        return result;
    }


    public int check(TrieNode<Integer> node, int beginIndex, char[] inputArray, List<Entity> result) {
        int i = beginIndex + 1;
        while (node != null && i < inputArray.length) {
            if (node.getChild(inputArray[i]) != null) {
                node = node.getChild(inputArray[i]);
                if (node.isEndState()) {
                    result.add(new Entity(beginIndex, i + 1, node.getValue()));
                }
            } else {
                return i;
            }
            i++;
        }
        return i;
    }

}