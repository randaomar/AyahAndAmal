package jetpack.randa.com.ayahandamal.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import jetpack.randa.com.ayahandamal.AyahAndAmalApplication
import jetpack.randa.com.ayahandamal.IntentKeys
import jetpack.randa.com.ayahandamal.PreferenceHelper
import jetpack.randa.com.ayahandamal.R
import jetpack.randa.com.ayahandamal.adapter.AyahListAdapter
import jetpack.randa.com.ayahandamal.model.AyahDecoration
import jetpack.randa.com.ayahandamal.model.Bookmark
import jetpack.randa.com.ayahandamal.model.Surah
import jetpack.randa.com.ayahandamal.viewModel.Add3amalViewModel
import jetpack.randa.com.ayahandamal.viewModel.BookmarkViewModel
import jetpack.randa.com.ayahandamal.viewModel.SurahViewModel
import kotlinx.android.synthetic.main.activity_read_quran.*
import javax.inject.Inject

class ReadQuranActivity : BaseActivity() {
    @Inject
    lateinit var viewModel: Add3amalViewModel
    @Inject
    lateinit var bookmarkViewModel: BookmarkViewModel
    @Inject
    lateinit var surahViewModel: SurahViewModel

    private  var surah: Surah? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_quran)
        supportActionBar?.hide()
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        (application as AyahAndAmalApplication).component.inject(this)
        val surahId = intent.getIntExtra(IntentKeys.SELECTED_SURAH_ID, 1)
        val ayahId = intent.getIntExtra(IntentKeys.SELECTED_AYAH_ID,1)
        subscribeToGetSurah(surahId, ayahId)
        setCoachMark()

    }

    private fun setCoachMark() {
        val helper = PreferenceHelper(this@ReadQuranActivity)
        coach_mark.visibility = if(helper.getReadQurancoachmark()){
            helper.setReadQuranCoachmark(false)
            View.VISIBLE
        } else View.GONE
        coachmark_btn.setOnClickListener {
            coach_mark.visibility = View.GONE
        }
    }

    private fun subscribeToGetSurah(surahId: Int, ayahNum: Int) {
        surahViewModel.getSurah(surahId).subscribe({
           surah = it.first
            setSurah(ayahNum, it.second)
        },{
            Log.d("","")
        })
    }

    private fun setSurah(ayahNum: Int, ayahDecList: List<AyahDecoration>) {
        surah_name_tv.text = "سورة " + surah?.surahName
        if(ayahNum == 1){
            try {
                joze_tv.visibility = View.VISIBLE
                val jozeNum = ayahDecList.first { it.quartHezb == 1 }.joze
                joze_tv.text = "الجزء " + jozeNum
            }catch(e: Exception){
                joze_tv.visibility = View.GONE
            }
        }else {
            joze_tv.visibility = View.GONE
        }

        getAyahsOfSurah(surah?.id.toString(), ayahNum, ayahDecList)

        val layoutManager = LinearLayoutManager(this@ReadQuranActivity)
        ayah_rv.layoutManager = layoutManager
        ayah_rv.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val jozeNum = try{ ayahDecList.first { it.quartHezb == 1 }.joze} catch(e: Exception) {30}
                joze_tv.text = "الجزء " + jozeNum
                basmalah.visibility = if(layoutManager.findFirstVisibleItemPosition() == 0 &&
                    (surah?.id != 1 && surah?.id != 9)) View.VISIBLE else View.GONE
                joze_tv.visibility = if(layoutManager.findFirstVisibleItemPosition() == 0) View.VISIBLE else View.GONE
            }
        })
    }

    private fun getAyahsOfSurah(id: String, ayahNum: Int, list: List<AyahDecoration>) {
        viewModel.getAyahsOfSurah(id).subscribe({
            val helper = PreferenceHelper(this@ReadQuranActivity)
            ayah_rv.adapter = AyahListAdapter(it, helper.getTafseerText(), helper.getTafseerColor(), function)
            (ayah_rv.adapter as AyahListAdapter).setAyahDecoration(list)
            ayah_rv.layoutManager?.scrollToPosition((ayahNum-1))
            if(ayahNum != 1){
                basmalah.visibility = View.GONE
                joze_tv.visibility = View.GONE
            }
            if (surah?.id == 1 || surah?.id == 9) {
                basmalah.visibility = View.GONE
            }
        },{
            Log.d("Throwable", "error")
        })
    }
    private val function: (Int, Int, String)->Unit = {i1: Int, i2: Int, s1: String ->
        surah?.let {
            val ayahDesc = it.surahName +" "+ resources.getString(R.string.ayah)+" "+i1
            val bookmark = Bookmark(i1,i2,ayahDesc, s1 )
            bookmarkViewModel.insertBookmark(bookmark).subscribe({
                Toast.makeText(this@ReadQuranActivity, resources.getString(R.string.bookmark_saved), Toast.LENGTH_SHORT).show()
            },{
                Log.d("Error","Error")
            })
        }
    }

    
}
