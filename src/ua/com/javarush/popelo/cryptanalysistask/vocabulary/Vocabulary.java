package ua.com.javarush.popelo.cryptanalysistask.vocabulary;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface Vocabulary {

    /**
     * @return
     */
    List<Character> getAlphabet();

    /**
     * @return
     */
    List<String> getPopularWords();

    /**
     * @param character
     * @return
     */
    boolean isThisLanguage(char character);

    /**
     * @param code
     * @return
     */
    static Vocabulary getVocabulary(char code) {
        Set<Class> classes = getImplementedClasses();

        for (Class currentClass : classes) {
            try {
                Vocabulary prospect = (Vocabulary) Vocabulary
                        .class
                        .getClassLoader()
                        .loadClass(currentClass.getName())
                        .newInstance();

                if (prospect.isThisLanguage(code)) {
                    return prospect;
                }

            } catch (Exception e) {
                throw new RuntimeException("Can't load Vocabulary class", e);
            }
        }

        return null;
    }

    /**
     * @return
     */
    private static Set<Class> getImplementedClasses() {
        String packageName = null;

        try {
            packageName = Class.forName(Vocabulary.class.getName()).getPackageName();

        } catch (Exception e) {
            throw new RuntimeException("Can't get package name", e);
        }

        InputStream stream = ClassLoader.getSystemClassLoader()
                .getResourceAsStream(packageName.replaceAll("[.]", "/"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        String finalPackageName = packageName;

        return reader
                .lines()
                .filter(line -> line.endsWith("Vocabulary.class"))
                .filter(line -> !line.equals("Vocabulary.class"))
                .map(line -> getClass(line, finalPackageName))
                .collect(Collectors.toSet());
    }

    /**
     * @param className
     * @param packageName
     * @return
     */
    private static Class getClass(String className, String packageName) {
        String name = packageName + "." + className.substring(0, className.lastIndexOf('.'));

        try {
            return Class.forName(name);

        } catch (Exception e) {
            throw new RuntimeException("Can't get load class: " + name, e);
        }
    }

}
