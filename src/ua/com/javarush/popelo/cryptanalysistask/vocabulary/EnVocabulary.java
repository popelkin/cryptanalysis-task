package ua.com.javarush.popelo.cryptanalysistask.vocabulary;

import java.util.Arrays;
import java.util.List;

class EnVocabulary implements Vocabulary {

    @Override
    public List<Character> getAlphabet() {
        return Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
                's', 't', 'u', 'v', 'w', 'x', 'y');
    }

    /**
     *
     * @return
     */
    public List<String> getPopularWords() {
        return Arrays.asList("the", "of", "and", "a", "to", "in", "is", "you", "that", "it", "he", "was", "for", "on",
                "are", "as", "with", "his", "they", "I", "at", "be", "this", "have", "from", "or", "one", "had", "by",
                "word", "but", "not", "what", "all", "were", "we", "when", "your", "can", "said", "there", "use", "an",
                "each", "which", "she", "do", "how", "their", "if", "will", "up", "other", "about", "out", "many",
                "then", "them", "these", "so", "some", "her", "would", "make", "like", "him", "into", "time", "has",
                "look", "two", "more", "write", "go", "see", "number", "no", "way", "could", "people", "my", "than",
                "first", "water", "been", "call", "who", "oil", "its", "now", "find", "long", "down", "day", "did",
                "get", "come", "made", "may", "part");
    }

    @Override
    public boolean isThisLanguage(char character) {
        return ((character >= 65 && character <= 90) || (character >= 97 && character <= 122));
    }

}
