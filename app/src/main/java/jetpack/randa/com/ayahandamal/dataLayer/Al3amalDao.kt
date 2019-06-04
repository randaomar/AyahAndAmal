package jetpack.randa.com.ayahandamal.dataLayer

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import jetpack.randa.com.ayahandamal.model.Al3amal

@Dao
interface Al3amalDao {
    @Insert
    fun insert3amal(al3amal: Al3amal): Long

    @get:Query("SELECT * FROM  ayah_pref")
    val al3amal: Flowable<List<Al3amal>>

    @Query("SELECT * FROM  ayah_pref WHERE id = :id")
    fun get3amal(id: Long): Flowable<Al3amal>

    @Delete
    fun delet3amal(al3amal: Al3amal)
}