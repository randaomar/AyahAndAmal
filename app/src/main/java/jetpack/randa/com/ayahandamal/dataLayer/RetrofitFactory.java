package jetpack.randa.com.ayahandamal.dataLayer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import io.reactivex.Flowable;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class RetrofitFactory {
    public static <T> T createService(Class<T> clazz) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
             //   .baseUrl("http://staging.quran.com:3000/api/v3/")
                .baseUrl("http://api.alquran.cloud/v1/surah/")
                .client(okHttpClient)
                .build();

        return retrofit.create(clazz);
    }
    public class TafsirResponse{
        @SerializedName("tafsirs")
        private List<TafsirTextResponse>tafsirTextResponseList;

        public List<TafsirTextResponse> getTafsirTextResponseList() {
            return tafsirTextResponseList;
        }
    }
    public class TafsirTextResponse{
        @SerializedName("text")
        private String textTafsir;

        public String getTextTafsir() {
            return textTafsir;
        }
    }
public class AyahDataResponse{
        @SerializedName("data")
        private VerseData verseData;

    public VerseData getVerseData() {
        return verseData;
    }
}

    public class VerseData{
        @SerializedName("ayahs")
        private List<AyahData> ayahData;

        public List<AyahData> getAyahData() {
            return ayahData;
        }
    }

    public class AyahData{
        @SerializedName("text")
        private String ayahTxt;
        @SerializedName("numberInSurah")
        private int verseNum;

        public String getAyahTxt() {
            return ayahTxt;
        }

        public int getVerseNum() {
            return verseNum;
        }
    }

    public interface RetroService{
        @GET("chapters/{aid}/verses/{vid}/tafsirs")
        Flowable<TafsirResponse> getTafsir(@Path("aid") int ayahId,@Path("vid") int verseId, @Query("tafsirs")int tafsir);

        @GET("{aid}")
        Flowable<AyahDataResponse> getAyahs(@Path("aid") int surah);


    }


}
