package jetpack.randa.com.ayahandamal.dataLayer

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import jetpack.randa.com.ayahandamal.model.Surah

@Dao
interface SurahDao {
    @Query("SELECT * FROM  surah WHERE id = :surahId")
    fun getSurah(surahId: Int): Flowable<Surah>
}