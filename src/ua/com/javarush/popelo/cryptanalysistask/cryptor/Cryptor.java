package ua.com.javarush.popelo.cryptanalysistask.cryptor;

import ua.com.javarush.popelo.cryptanalysistask.vocabulary.Vocabulary;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Cryptor {

    private Vocabulary vocabulary;

    private List<Character> sourceAlphabet = new ArrayList<>();
    private List<Character> shiftedAlphabet = new ArrayList<>();

    private static char[] originalString;
    private BufferedWriter writer;

    private static final String FILE_IN = "in.txt";
    private static final String FILE_OUT = "out.txt";
    private static final String DIR = System.getProperty("user.dir") + "/result/";

    private static final List<Character> ALPHABET_SYMBOLS = Arrays.asList('.', ',', ':', '!', '?', ' ', '1', '2', '3',
            '4', '5', '6', '7', '8', '9', '0', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '+', '{', '}', '"',
            '\'', ';', '\\', '/', '~', '`');

    /**
     * @throws Exception
     */
    public Cryptor() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(new File(DIR + FILE_IN)));
        writer = new BufferedWriter(new FileWriter(new File(DIR + FILE_OUT)));

        originalString = reader.lines().collect(Collectors.joining(System.lineSeparator())).toLowerCase().toCharArray();

        initVocabulary();
    }

    /**
     *
     */
    public void encryptData() {
        String result = this.encrypt();

        saveResult(result);
    }

    /**
     *
     */
    public void decryptData() {
        String result = this.decrypt();

        saveResult(result);
    }

    public void bruteForceData() {
        String result = this.bruteForce();

        saveResult(result);
    }

    /**
     *
     * @return
     */
    public int getMaxShift() {
        return this.sourceAlphabet.size();
    }

    /**
     *
     * @return
     */
    private String encrypt() {
        char[] encryptedString = new char[originalString.length];

        for (int i = 0; i < originalString.length; i++) {
            int originalIndex = sourceAlphabet.indexOf(originalString[i]);

            encryptedString[i] = originalIndex > -1 ? shiftedAlphabet.get(originalIndex) : originalString[i];
        }

        return String.valueOf(encryptedString);
    }

    /**
     * @throws Exception
     */
    private String decrypt() {
        char[] decryptedString = new char[originalString.length];

        for (int i = 0; i < originalString.length; i++) {
            int shiftedIndex = shiftedAlphabet.indexOf(originalString[i]);

            decryptedString[i] = shiftedIndex > -1 ? sourceAlphabet.get(shiftedIndex) : originalString[i];
        }

        return String.valueOf(decryptedString);
    }

    /**
     *
     * @return
     */
    private String bruteForce() {
        List<String> popularWords = vocabulary.getPopularWords();
        int[] counts = new int[sourceAlphabet.size()];

        for (int i = 0; i < sourceAlphabet.size(); i++) {
            try {
                this.setShift(i);

                String decrypted = this.decrypt();
                String[] words = decrypted.split("([\\s]+)");

                for (String word: words) {
                    word = word.trim();

                    if (word.length() < 1 || !popularWords.contains(word)) {
                        continue;
                    }

                    counts[i] += 1;
                }

            } catch (Exception ex) {
            }
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
        try {
            this.setShift(index);
            decryptedString = this.decrypt();

            writer.write(decryptedString);
            writer.flush();

        } catch (Exception ex) {
        }

        return String.valueOf(decryptedString);
    }

    /**
     * @return
     * @throws Exception
     */
    private Vocabulary getLanguageVocabulary() {
        Vocabulary vocabulary = null;

        for (char symbol : originalString) {
            try {
                vocabulary = Vocabulary.getVocabulary(symbol);

                if (vocabulary != null) {
                    break;
                }

            } catch (Exception ex) {
            }
        }

        return vocabulary;
    }

    /**
     * @param shift
     * @throws Exception
     */
    public void setShift(int shift) throws Exception {
        if (shift < 1 || shift > (sourceAlphabet.size())) {
            throw new Exception("Incorrect shift value. Should be between 1 and " + sourceAlphabet.size() + " inclusive");
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
     *
     * @param result
     */
    private void saveResult(String result) {
        try {
            writer.write(result);
            writer.flush();

        } catch (Exception ex) {

        }
    }

}
