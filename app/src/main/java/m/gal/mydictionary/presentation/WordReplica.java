package m.gal.mydictionary.presentation;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Replication of the real word on the DB, so we can open new sessions and delete words without damaging the
 * real words.
 *
 * Created by gal on 15/03/2017.
 */
@Entity
public class WordReplica {
    @Id(autoincrement = true)
    private Long id;

    private String originalWord;

    private String translation;

    @Generated(hash = 2013675793)
    public WordReplica(Long id, String originalWord, String translation) {
        this.id = id;
        this.originalWord = originalWord;
        this.translation = translation;
    }
    @Generated(hash = 124124205)
    public WordReplica() {
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
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
