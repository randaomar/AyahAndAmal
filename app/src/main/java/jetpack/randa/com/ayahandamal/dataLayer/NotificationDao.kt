package jetpack.randa.com.ayahandamal.dataLayer

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import jetpack.randa.com.ayahandamal.model.A3malNotification

@Dao
interface NotificationDao {
    @Query("SELECT * FROM  notification WHERE ayah_pref_id = :am3amlId ")
    fun getAl3amalNotification(am3amlId: Int): Flowable<A3malNotification>

    @Insert
    fun addNotification(notification: A3malNotification): Long
}