package edu.emory.cs.trie.autocomplete;

import org.junit.Test;

import java.util.List;
import java.util.Random;

/**
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class AutocompleteTest {
    static class Eval {
        int correct = 0;
        int total = 0;
    }

    @Test
    public void test() {
        final String dict_file = "src/main/resources/dict.txt";
        final int max = 20;

        Autocomplete<?> ac = new AutocompleteHWExtra(dict_file, max);
        Eval eval = new Eval();
        testAutocomplete(ac, eval);
    }

    private void testAutocomplete(Autocomplete<?> ac, Eval eval) {
        String prefix = new String();
        List<String> expected;

//        int length = 2;
//
//
//        String alphabet = "abcdefghijklmnopqrstuvwxyz";
//
////        Random random = new Random();
////        for (int i = 0; i < length; i++) {
////            char rand = alphabet.charAt(random.nextInt(alphabet.length()));
////            prefix += rand;
////        }


        prefix = "wk";
        expected = List.of("she", "ship", "shell");
        testGetCandidates(ac, eval, prefix, expected);

//        prefix = "sf";
//        expected = List.of("she", "ship", "shell", "school");
//        testGetCandidates(ac, eval, prefix, expected);
//
//        prefix = "sj";
//        expected = List.of("ship", "she", "shell");
//        ac.pickCandidate(prefix, "ship");
//        ac.pickCandidate(prefix, "ship");
//        ac.pickCandidate(prefix, "ship");
//        testGetCandidates(ac, eval, prefix, expected);
//        ac.pickCandidate(prefix, "lkjh");
//        ac.pickCandidate(prefix, "lkjh");
//        ac.pickCandidate(prefix, "yes");
//        ac.pickCandidate(prefix, "yes");
//        testGetCandidates(ac, eval, prefix, expected);

        System.out.printf("Score: %d/%d\n", eval.correct, eval.total);
    }

    private void testGetCandidates(Autocomplete<?> ac, Eval eval, String prefix, List<String> expected) {
        String log = String.format("%2d: ", eval.total);
        eval.total++;

        try {
            List<String> candidates = ac.getCandidates(prefix);

            if (expected.equals(candidates)) {
                eval.correct++;
                log += "PASS";
            } else
                log += String.format("FAIL -> expected = %s, returned = %s", expected, candidates);
        } finally {

        }
//        catch (Exception e) {
//            log += "ERROR";
//        }

        System.out.println(log);
    }
}