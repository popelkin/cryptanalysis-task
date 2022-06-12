package ua.com.javarush.popelo.cryptanalysistask.vocabulary;

import java.util.Arrays;
import java.util.List;

class RuVocabulary implements Vocabulary {

    private static final int FIRST_LETTER_CODE = 1040;
    private static final int LAST_LETTER_CODE = 1105;

    @Override
    public List<Character> getAlphabet() {
        return Arrays.asList('а', 'б', 'в', 'г', 'д', 'е', 'ж', 'з', 'и', 'й', 'к', 'л', 'м', 'н', 'о', 'п', 'р', 'с',
                'т', 'у', 'ф', 'х', 'ц', 'ч', 'ш', 'щ', 'ъ', 'ы', 'ь', 'э', 'ю', 'я');
    }

    /**
     *
     * @return
     */
    public List<String> getPopularWords() {
        return Arrays.asList("и", "в", "не", "на", "я", "быть", "он", "с", "что", "а", "по", "это", "она", "этот", "к",
                "но", "они", "мы", "как", "из", "у", "который", "то", "за", "свой", "что", "весь", "год", "от", "так",
                "о", "для", "ты", "же", "все", "тот", "мочь", "вы", "человек", "такой", "его", "сказать", "только",
                "или", "ещё", "бы", "себя", "один", "как", "уже", "до", "время", "если", "сам", "когда", "другой",
                "вот", "говорить", "наш", "мой", "знать", "стать", "при", "чтобы", "дело", "жизнь", "кто", "первый",
                "очень", "два", "день", "её", "новый", "рука", "даже", "во", "со", "раз", "где", "там", "под", "можно",
                "ну", "какой", "после", "их", "работа", "без", "самый", "потом", "надо", "хотеть", "ли", "слово",
                "идти", "большой", "должен", "место", "иметь", "ничто");
    }

    @Override
    public boolean isThisLanguage(char character) {
        return (character >= FIRST_LETTER_CODE && character <= LAST_LETTER_CODE);
    }


}
