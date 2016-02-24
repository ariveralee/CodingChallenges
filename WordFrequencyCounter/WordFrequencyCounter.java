package codinginterview;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 *
 * @author ariveralee
 */
class WordFrequencyCounter {

    private WordFrequencyCounter() {
        // prevent instantiations
    }

    // create a class for a word object with it's associated count
    public static class Word {

        public String word;
        public int count;

        public Word(String word) {
            this.word = word;
            this.count = 1;
        }
    }

    // GetWordFrequency Class Variables
    private static final String DELIMITER = " ";

    /**
     * Stores words and their respective frequencies and returns top k most
     * frequent words.
     *
     * @param input, a given string consisting of words
     * @param k, number of most frequent words to be found
     * @return a list of the k most frequent occurring words.
     */
    public static String[] getWordFrequency(String input, int k) {

        // make sure we have proper input.
        if (input == null || k <= 0) {
            return new String[0];
        }
        // create Priority Queue to hold the top k words requested and override 
        // the comparator to compare the word objects based on the word count 
        // in reverse order this insures that the least occuring word is at 
        // the top of the queue.
        PriorityQueue<Word> topWords = new PriorityQueue<Word>(k, Collections.reverseOrder(new Comparator<Word>() {
            @Override
            public int compare(Word o1, Word o2) {
                if (o1 == null && o2 == null) {
                    return -1;
                } else if (o1 == null && o2 != null) {
                    return 1;
                } else if (o1 != null && o2 == null) {
                    return -1;
                } else {
                    return o2.count - o1.count;

                }
            }
        }));

        // create Hash map for storing of words and respective freqeuency
        HashMap<String, Word> wordFrequency = new HashMap<>();

        String[] words = input.toLowerCase().split(DELIMITER);
        for (String w : words) {
            Word word = wordFrequency.get(w);

            // if the word is null, it does not exist in the map. create a new word object
            // with the string from the input file and place it into the hash map.
            if (word == null) {
                word = new Word(w);
                wordFrequency.put(w, word);
            } else {
                word.count++;
                wordFrequency.put(w, word);
            }
            // Check to see if the word object exists within our priority queue
            if (topWords.contains(word)) {

                // if topWords has the word already, remove the word then add it back
                // with the word objects count incremented.
                topWords.remove(word);
                topWords.add(word);

                // else the word is not in queue so just increment
            } else if (topWords.size() < k || word.count >= topWords.peek().count) {

                // If the size of our top words priority queue is less than K OR
                // the top element's count of topWords is less than our current
                // word objects count, then we add the word object to topWords
                topWords.add(word);
            }
            // if the topWords size is larger than k, we remove the head. Because
            // the word objects in topWords is sorted on the word count in natural
            // ordering, we are guaranteed to remove the lowest occurring word each time.
            if (topWords.size() > k) {
                topWords.poll();
            }
        }
        // top words is arranged in ascending order, so we start at index k - 1
        // and decrement to the beginning of the topKWords to put the words in
        // descending order.
        k = Math.min(k, topWords.size());
        String[] topKWords = new String[k];
        int i = k - 1;
        while (!topWords.isEmpty() && i >= 0) {
            topKWords[i] = topWords.poll().word;
            i--;
        }
        return topKWords;
    }

    public static void main(String[] args) {
        String str = "i love to hAtE midterms I hate MiDTeRms SEASON";
        int k = 3;
        String top[] = WordFrequencyCounter.getWordFrequency(str, k);

        for (String string : top) {
            System.out.println(string);
        }
    }
}
