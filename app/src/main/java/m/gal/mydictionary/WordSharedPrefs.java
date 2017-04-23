package m.gal.mydictionary;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * SharedPrefs facility that holds list of several 'static' words.
 *
 * Created by gal on 21/03/2017.
 */

public class WordSharedPrefs {
    public static final int STATIC_WORDS_LIST_SIZE = 10;
    private final SharedPreferences sharedPreferences;
    private static final String WORD_SHARED_PREFS_NAME = "WORD_SHARED_PREFS_NAME";
    private static final String FIRST_WORDS = "FIRST_WORDS";

    public WordSharedPrefs(Context context) {
        sharedPreferences = context.getSharedPreferences(WORD_SHARED_PREFS_NAME,Context.MODE_PRIVATE);
    }

    public Set<String> getFirstWords(){
        return sharedPreferences.getStringSet(FIRST_WORDS,null);
    }

    public void addFirstWords(String word){
        Set<String> wordsSet = sharedPreferences.getStringSet(FIRST_WORDS,null);
        if(wordsSet == null){
            wordsSet = new HashSet<>();
        }
        if(wordsSet.size() < STATIC_WORDS_LIST_SIZE){
            wordsSet = new HashSet<>(wordsSet); //https://developer.android.com/reference/android/content/SharedPreferences.html#getStringSet(java.lang.String,%20java.util.Set%3Cjava.lang.String%3E)
            wordsSet.add(word);
        }
        sharedPreferences.edit().putStringSet(FIRST_WORDS,wordsSet).commit();
    }

}
