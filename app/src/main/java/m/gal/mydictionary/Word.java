package m.gal.mydictionary;

/**
 * This class represents a word and it's translation on the user's preferred language.
 *
 * Created by gal on 29/01/2017.
 */

public class Word {
    public String getOriginalWord() {
        return originalWord;
    }

    public String getTranslation() {
        return translation;
    }

    private String originalWord;
    private String translation;

    public Word(String originalWord, String translation) {
        this.originalWord = originalWord;
        this.translation = translation;
    }
}
