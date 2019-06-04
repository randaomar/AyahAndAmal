package jetpack.randa.com.ayahandamal.sqlAsset;


import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

/**
 * Implements {@link SupportSQLiteOpenHelper.Factory} using the SQLite implementation in the
 * framework.
 */
@SuppressWarnings("unused")
public class AssetSQLiteOpenHelperFactory implements SupportSQLiteOpenHelper.Factory {
    @Override
    public SupportSQLiteOpenHelper create(SupportSQLiteOpenHelper.Configuration configuration) {
        return new AssetSQLiteOpenHelper(
                configuration.context, configuration.name, null,
                1, dbObj -> {

                }, configuration.callback
        );
    }
}
