package jetpack.randa.com.ayahandamal.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.Nullable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "ayah_pref")
class Al3amal (@field:ColumnInfo(name = "surah_num")
              var surahNum: String?,
              @field:ColumnInfo(name = "ayah_num")
              var ayahNum: String?,
              @field:ColumnInfo(name = "ayah_desc")
              var al3amal: String?): Serializable {

    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    var id: Long = 0


    //var ayahTxt: String = ""

    fun getSurahIntNum(): Int {
        return try {
            Integer.parseInt(surahNum!!)
        } catch (e: Exception) {
            0
        }
    }
    fun getAyahIntNum(): Int {
        return try {
            Integer.parseInt(ayahNum!!)
        } catch (e: Exception) {
            0
        }
    }
}

data class Al3amalCombined(val al3amal: Al3amal, val ayahText: String): Serializable

@Entity(tableName = "ayah")
class Ayah{
    @field:PrimaryKey
    @field:ColumnInfo(name = "id")
     var id: String = "0"

    @field:ColumnInfo(name = "ayah_tafseer")
    var tafseer: String? = null

    @field:ColumnInfo(name = "ayah_text")
    var ayahText: String? = null

    @field:ColumnInfo(name = "ayah_number")
     var ayahNum: String? = null

    @field:ColumnInfo(name = "surah_id")
     var surahNum: String? = null

    @field:ColumnInfo(name = "ayah_color")
    var ayahColor: String? = null

    fun getSurahIntNum(): Int {
        return try {
            Integer.parseInt(surahNum!!)
        } catch (e: Exception) {
            0
        }
    }
    fun getAyahIntNum(): Int {
        return try {
            Integer.parseInt(ayahNum!!)
        } catch (e: Exception) {
            0
        }
    }

    fun getId(): Int {
        return try {
            Integer.parseInt(id!!)
        } catch (e: Exception) {
            0
        }
    }
}

@Entity(tableName = "ayah_decoration")
data class AyahDecoration(
    @field:PrimaryKey
    @field:ColumnInfo(name = "_id")
    val id: Int,
    @field:ColumnInfo(name = "quart_hezb")
    val quartHezb: Int?,
    @field:ColumnInfo(name = "ayah_num")
    val ayahNum: Int?,
    @field:ColumnInfo(name = "surah_num")
    val surahNum: Int?,
    @field:ColumnInfo(name = "joze")
    val joze: Int?)


@Entity(tableName = "bookmark")
 class Bookmark(
    @field:ColumnInfo(name = "ayah_num")
    var ayahNum: Int,
    @field:ColumnInfo(name = "surah_num")
    var surahNum: Int,
    @field:ColumnInfo(name = "bookmark_description")
    var bookmarkDescription: String,
    @field:ColumnInfo(name = "bookmark_ayah_text")
    var bookmarkAyah: String){
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "_id")
    var id: Int = 0
}


@Entity(tableName = "notification")
 class A3malNotification(
    @field:ColumnInfo(name = "ayah_pref_id")
    val al3amalId: Long,
    @field:ColumnInfo(name = "time")
    val timeStamp: Long){
    @field:PrimaryKey(autoGenerate = true)
    @field:ColumnInfo(name = "id")
    var id: Int = 0

}


@Entity(tableName = "surah")
data class Surah(
    @field:PrimaryKey
    @field:ColumnInfo(name = "id")
    val id: Int,
    @field:ColumnInfo(name = "surah_desc")
    val surahDesc: String,
    @field:ColumnInfo(name = "surah_name")
    val surahName: String):Serializable

@Entity(tableName = "topic")
 class Topic {
    @field:PrimaryKey
    @field:ColumnInfo(name = "topic_id")
    var id: String = "0"
    @field:ColumnInfo(name = "ayah_number")
     var ayahNum: String? = null
    @field:ColumnInfo(name = "topic_text")
    var topicText: String? = null
    @field:ColumnInfo(name = "surah_number")
     var surahNum: String? = null

    fun getSurahIntNum(): Int {
        return try {
            Integer.parseInt(surahNum!!)
        } catch (e: Exception) {
            0
        }
    }
    fun getAyahIntNum(): Int {
        return try {
            Integer.parseInt(ayahNum!!)
        } catch (e: Exception) {
            0
        }
    }

    fun getId(): Int {
        return try {
            Integer.parseInt(id!!)
        } catch (e: Exception) {
            0
        }
    }
}

data class TopicHeads(@SerializedName("topic_id")val topic_id: String, @SerializedName("topic_text")val topic_text: String): Serializable
