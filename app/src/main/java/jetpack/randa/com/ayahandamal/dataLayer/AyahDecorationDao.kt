package jetpack.randa.com.ayahandamal.dataLayer

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import jetpack.randa.com.ayahandamal.model.AyahDecoration

@Dao
interface AyahDecorationDao {
    @Query("select * from ayah_decoration where ayah_num = :surahNum")
    fun getSurahDecorations(surahNum: Int): Flowable<List<AyahDecoration>>
}