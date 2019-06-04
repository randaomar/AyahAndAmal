package jetpack.randa.com.ayahandamal.dataLayer

import android.arch.persistence.room.Database
import android.content.Context
import jetpack.randa.com.ayahandamal.sqlAsset.AssetSQLiteOpenHelperFactory
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import jetpack.randa.com.ayahandamal.model.*


@Database(entities = [Al3amal::class, Ayah::class, Topic::class, AyahDecoration::class, Bookmark::class, Surah::class, A3malNotification::class], version = 1)
abstract class AppDatabase: RoomDatabase(){
    abstract fun ayahsDao(): AyahsDao

    abstract fun al3amalDao(): Al3amalDao

    abstract fun notificationDao(): NotificationDao

    abstract fun bookmarkDao(): BookmarksDao

    abstract fun surahDao(): SurahDao

    abstract fun topicsDao(): TopicsDao

    abstract fun ayahDecorationDao(): AyahDecorationDao

    companion object {
        private var appDatabaseInstance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase?{
            if(appDatabaseInstance == null) {
                appDatabaseInstance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "ayaAndAmalLastSymbol.db")
                    .openHelperFactory(AssetSQLiteOpenHelperFactory())
                    .allowMainThreadQueries()
                    .build()
            }
            return appDatabaseInstance
        }
    }
}