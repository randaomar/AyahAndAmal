package jetpack.randa.com.ayahandamal.dataLayer

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

import io.reactivex.Flowable
import jetpack.randa.com.ayahandamal.model.Ayah
import jetpack.randa.com.ayahandamal.model.Surah

@Dao
interface AyahsDao {

    @get:Query("SELECT * FROM  ayah")
    val ayas: Flowable<List<Ayah>>

    @get:Query("SELECT * FROM  surah")
    val surahs: Flowable<List<Surah>>

    @Query("SELECT * FROM  ayah WHERE surah_id = :id")
    fun getAyahsOfSurah(id: String): Flowable<List<Ayah>>

    @Query("SELECT * FROM  ayah WHERE surah_id = :surah AND ayah_number = :ayah")
    fun getOneAyah(surah: String, ayah: String): Flowable<Ayah>

    @Update
    fun updateTafsir(ayah: Ayah)

    @Query("UPDATE ayah SET ayah_text=:tafseerString WHERE ayah_number = :ayahNum AND surah_id = :surahNum")
    fun updateTafseer(tafseerString: String, ayahNum: String, surahNum: String)

}
