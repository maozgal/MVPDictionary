package m.gal.mydictionary.serverModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Wrapper for the web request.
 *
 * Created by gal on 25/01/2017.
 */

public interface Retro {
    @GET("translate")
    Call <Yundex> translate(@Query("key")String key,@Query("text")String text,@Query("lang")String lang);
}
