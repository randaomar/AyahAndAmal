package jetpack.randa.com.ayahandamal.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import jetpack.randa.com.ayahandamal.AyahAndAmalApplication
import jetpack.randa.com.ayahandamal.IntentKeys
import jetpack.randa.com.ayahandamal.PreferenceHelper
import jetpack.randa.com.ayahandamal.R
import jetpack.randa.com.ayahandamal.adapter.AyahListAdapter
import jetpack.randa.com.ayahandamal.adapter.AyahTopicListAdapter
import jetpack.randa.com.ayahandamal.adapter.BookmarkAdapter
import jetpack.randa.com.ayahandamal.model.Ayah
import jetpack.randa.com.ayahandamal.model.Bookmark
import jetpack.randa.com.ayahandamal.model.TopicHeads
import jetpack.randa.com.ayahandamal.viewModel.BookmarkViewModel
import jetpack.randa.com.ayahandamal.viewModel.TopicsViewModel
import kotlinx.android.synthetic.main.activity_list.*
import javax.inject.Inject

class ListActivity : BaseActivity() {
    @Inject
    lateinit var viewModel: BookmarkViewModel
    @Inject
    lateinit var ayahTopicsViewModel: TopicsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        (application as AyahAndAmalApplication).component.inject(this)


    }

    private var topicHead: TopicHeads? = null

    override fun onResume() {
        super.onResume()
        val isBookmark = intent.getBooleanExtra(IntentKeys.IS_BOOKMARK, false)
        list_rv.layoutManager = LinearLayoutManager(this)
        if(isBookmark){
            supportActionBar?.title = getString(R.string.bookmark)
         subscribeToGetBookmarks()
        } else {
            val helper = PreferenceHelper(this@ListActivity)
            list_rv.adapter = AyahTopicListAdapter(ArrayList(), helper.getTafseerText(), helper.getTafseerColor(), function, ArrayList())

            topicHead = intent.getSerializableExtra(IntentKeys.TOPIC_HEAD) as TopicHeads
            supportActionBar?.title = topicHead?.topic_text
            ayahTopicsViewModel.getAyahsOfTopics(topicHead?.topic_id).subscribe({
                (list_rv.adapter as AyahTopicListAdapter).addAyahAndSurahName(it.first, it.second)
            },{
                Log.d("","")
            })

        }
    }

    private fun subscribeToGetBookmarks() {
        viewModel.bookmarks.subscribe({
            bookmark_empty_state_layout.visibility = View.GONE
            list_rv.adapter = BookmarkAdapter(it, deleteFunction)
            if(it == null || it.isEmpty()){
                bookmark_empty_state_layout.visibility = View.VISIBLE
                list_rv.visibility  = View.GONE
            }
        },{
            Log.d("error", "error")
        })
    }

    private val deleteFunction: (Bookmark)->Unit = {bookmark ->
        viewModel.deleteBookmark(bookmark).subscribe({
            subscribeToGetBookmarks()
        },{
            Log.d("","")
        })
    }
    private val function: (Int, Int, String)->Unit = {i1: Int, i2: Int, s1: String ->

            val ayahDesc = "من موضوع " + topicHead?.topic_text
            val bookmark = Bookmark(i1,i2,ayahDesc, s1 )
            viewModel.insertBookmark(bookmark).subscribe({
                Toast.makeText(this@ListActivity, resources.getString(R.string.bookmark_saved), Toast.LENGTH_SHORT).show()
            },{
                Log.d("Error","Error")
            })

    }
}
