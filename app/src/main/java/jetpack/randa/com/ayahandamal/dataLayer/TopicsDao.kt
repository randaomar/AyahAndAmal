package jetpack.randa.com.ayahandamal.dataLayer

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import jetpack.randa.com.ayahandamal.model.Topic
import jetpack.randa.com.ayahandamal.model.TopicHeads

@Dao
interface TopicsDao{
    @Query("SELECT DISTINCT topic_text, topic_id from topic")
    fun getTopics(): Flowable<List<TopicHeads>>

    @Query("SELECT  * from topic where topic_id = :id")
    fun getOneTopics(id: String): Flowable<List<Topic>>
}
