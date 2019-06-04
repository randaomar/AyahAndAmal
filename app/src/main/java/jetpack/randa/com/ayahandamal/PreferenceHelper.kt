package jetpack.randa.com.ayahandamal


import android.content.Context
import android.preference.PreferenceManager

class PreferenceHelper(val context: Context) {

    companion object {
        private val SHOW_COACHMARK = "show_coachmark"
        private val TAFSEER_TEXT = "tafseer_text"
        private val TAFSEER_COLOR = "tafseer_color"
        private val SHOW_READ_QURAN_COACHMARK = "show_read_quran_coachmark"
    }

    fun setTafseerText(isOn: Boolean) {
        set(TAFSEER_TEXT, isOn)
    }

    fun getTafseerText(): Boolean = get(TAFSEER_TEXT, false)

    fun setTafseerColor(isOn: Boolean) {
        set(TAFSEER_COLOR, isOn)
    }

    fun getTafseerColor(): Boolean = get(TAFSEER_COLOR, false)

    fun setShowCoachmark(isOn: Boolean) {
        set(SHOW_COACHMARK, isOn)
    }

    fun getShowcoachmark(): Boolean = get(SHOW_COACHMARK, true)

    fun setReadQuranCoachmark(isOn: Boolean) {
        set(SHOW_READ_QURAN_COACHMARK, isOn)
    }

    fun getReadQurancoachmark(): Boolean = get(SHOW_READ_QURAN_COACHMARK, true)

    private operator fun set(key: String, value: String) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putString(key, value)
        editor.commit()
    }

    private operator fun set(key: String, value: Boolean) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    private operator fun get(key: String, defaultValue: Boolean): Boolean {
        val settings = PreferenceManager.getDefaultSharedPreferences(context)
        return settings.getBoolean(key, defaultValue)
    }

    private operator fun get(key: String, defaultValue: String): String {
        val settings = PreferenceManager.getDefaultSharedPreferences(context)
        return settings.getString(key, defaultValue)
    }


    private operator fun set(key: String, value: Int) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putInt(key, value)
        editor.apply()
    }

    private operator fun get(key: String, defaultValue: Int): Int {
        val settings = PreferenceManager.getDefaultSharedPreferences(context)
        try {
            return settings.getInt(key, defaultValue)
        } catch (e: ClassCastException) {
            return defaultValue
        }

    }

    private operator fun get(key: String): MutableSet<String> {
        val settings = PreferenceManager.getDefaultSharedPreferences(context)
        return settings.getStringSet(key, HashSet())
    }

    private operator fun set(key: String, value: Set<String>) {
        val editor = PreferenceManager.getDefaultSharedPreferences(context).edit()
        editor.putStringSet(key, value)
        editor.commit()
    }

    fun flushData() {
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().commit()
    }



}