package m.gal.mydictionary;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * This class represents a word and it's translation on the user's preferred language.
 *
 * Created by gal on 29/01/2017.
 */
@Entity
public class Word {

    private String originalWord;
    private String translation;


    @Generated(hash = 3342184)
    public Word() {
    }
    @Generated(hash = 1657609006)
    public Word(String originalWord, String translation) {
        this.originalWord = originalWord;
        this.translation = translation;
    }
    public String getOriginalWord() {
        return this.originalWord;
    }
    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }
    public String getTranslation() {
        return this.translation;
    }
    public void setTranslation(String translation) {
        this.translation = translation;
    }


}
