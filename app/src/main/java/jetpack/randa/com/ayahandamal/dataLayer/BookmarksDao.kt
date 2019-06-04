package jetpack.randa.com.ayahandamal.dataLayer

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import jetpack.randa.com.ayahandamal.model.Bookmark

@Dao
interface BookmarksDao {
    @get:Query("SELECT * FROM  bookmark")
    val bookmarks: Flowable<List<Bookmark>>

    @Insert
    fun insertBookmark(bookmark: Bookmark)

    @Delete
    fun deleteBookmark(bookmark: Bookmark)
}