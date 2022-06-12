package ua.com.javarush.popelo.cryptanalysistask.cryptor;

import ua.com.javarush.popelo.cryptanalysistask.vocabulary.Vocabulary;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Cryptor {

    private Vocabulary vocabulary;

    private List<Character> sourceAlphabet = new ArrayList<>();
    private List<Character> shiftedAlphabet = new ArrayList<>();

    private static char[] originalString;
    private BufferedWriter writer;

    private static final String RESULT_DIRECTORY = System.getProperty("user.dir") + "/result/";
    private static final String FILE_INPUT = RESULT_DIRECTORY + "in.txt";
    private static final String FILE_OUTPUT = RESULT_DIRECTORY + "out.txt";


    private static final List<Character> ALPHABET_SYMBOLS = Arrays.asList('.', ',', ':', '!', '?', ' ', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '0', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '+', '{', '}', '"',
            '\'', ';', '\\', '/', '~', '`');

    /**
     *
     */
    public Cryptor() {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(FILE_INPUT)))) {
            originalString = reader
                    .lines()
                    .collect(Collectors.joining(System.lineSeparator()))
                    .toLowerCase()
                    .toCharArray();

        } catch (IOException e) {
            throw new RuntimeException("Can't open input file with path: " + FILE_INPUT, e);
        }

        try {
            writer = new BufferedWriter(new FileWriter(new File(FILE_OUTPUT)));

        } catch (IOException e) {
            throw new RuntimeException("Can't open output file: " + FILE_OUTPUT, e);

        } finally {
            if (writer != null) {
                try {
                    writer.close();

                } catch (IOException e2) {
                    throw new RuntimeException("Can't close output file: " + FILE_OUTPUT, e2);
                }
            }
        }

        initVocabulary();
    }

    /**
     *
     */
    public void encryptData() {
        saveToFile(this.encrypt());
    }

    /**
     *
     */
    public void decryptData() {
        saveToFile(this.decrypt());
    }

    public void bruteForceData() {
        saveToFile(this.bruteForce());
    }

    /**
     * @return
     */
    public int getMaxShift() {
        return this.sourceAlphabet.size();
    }

    /**
     *
     * @param alphabet
     * @return
     */
    private String encryptionAndDecryption(List<Character> alphabet) {
        char[] result = new char[originalString.length];

        for (int i = 0; i < originalString.length; i++) {
            int index = alphabet.indexOf(originalString[i]);

            result[i] = index > -1 ? alphabet.get(index) : originalString[i];
        }

        return String.valueOf(result);
    }

    /**
     * @return
     */
    private String encrypt() {
        return encryptionAndDecryption(sourceAlphabet);
    }

    /**
     * @return
     */
    private String decrypt() {
        return encryptionAndDecryption(shiftedAlphabet);
    }

    /**
     *
     * @param words
     * @return
     */
    private int getPopularWordsMatchesCount(String[] words) {
        List<String> popularWords = vocabulary.getPopularWords();
        int[] counts = new int[sourceAlphabet.size()];
        int count = 0;

        for (String word : words) {
            word = word.trim();

            if (word.length() < 1 || !popularWords.contains(word)) {
                continue;
            }

            count++;
        }

        return count;
    }

    /**
     * @return
     */
    private String bruteForce() {
        int[] counts = new int[sourceAlphabet.size()];
        String[] words;

        for (int i = 0; i < sourceAlphabet.size(); i++) {
            this.setShift(i);

            words = this.decrypt().split("([\\s]+)");

            counts[i] = getPopularWordsMatchesCount(words);
        }

        int index = 0;
        int max = counts[index];
        for (int i = 1; i < counts.length; i++) {
            if (max < counts[i]) {
                max = counts[i];
                index = i;
            }
        }

        String decryptedString = "";

        this.setShift(index);
        decryptedString = this.decrypt();

        saveToFile(decryptedString);

        return decryptedString;
    }

    /**
     * @return
     * @throws Exception
     */
    private Vocabulary getLanguageVocabulary() {
        Vocabulary vocabulary = null;

        for (char symbol : originalString) {
            vocabulary = Vocabulary.getVocabulary(symbol);

            if (vocabulary != null) {
                break;
            }
        }

        return vocabulary;
    }

    /**
     * @param shift
     */
    public void setShift(int shift) {
        if (shift < 1 || shift > (sourceAlphabet.size())) {
            throw new RuntimeException("Incorrect shift value. Should be between 1 and " + sourceAlphabet.size() + " inclusive");
        }

        shiftedAlphabet.clear();
        shiftedAlphabet.addAll(sourceAlphabet.subList(shift, sourceAlphabet.size()));
        shiftedAlphabet.addAll(sourceAlphabet.subList(0, shift));
    }

    /**
     *
     */
    private void initVocabulary() {
        vocabulary = getLanguageVocabulary();

        sourceAlphabet.addAll(vocabulary.getAlphabet());
        sourceAlphabet.addAll(ALPHABET_SYMBOLS);
    }

    /**
     * @param result
     */
    private void saveToFile(String result) {
        try {
            writer.write(result);
            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException("Can't write to file: " + FILE_OUTPUT, e);

        } finally {
            try {
                writer.close();

            } catch (IOException e2) {
                throw new RuntimeException("Can't close output file: " + FILE_OUTPUT, e2);
            }
        }
    }

}
