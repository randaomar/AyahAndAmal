package jetpack.randa.com.ayahandamal.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jetpack.randa.com.ayahandamal.AyahAndAmalApplication
import jetpack.randa.com.ayahandamal.IntentKeys

import jetpack.randa.com.ayahandamal.R
import jetpack.randa.com.ayahandamal.activity.ListActivity
import jetpack.randa.com.ayahandamal.adapter.SurahListAdapter
import jetpack.randa.com.ayahandamal.viewModel.Add3amalViewModel
import kotlinx.android.synthetic.main.fragment_surah_list.*
import javax.inject.Inject


class SurahListFragment : Fragment() {

    @Inject
    lateinit var viewModel: Add3amalViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_surah_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as AyahAndAmalApplication).component.inject(this)
        getAllSurahs()
        bookmarks_tv.setOnClickListener {
            val intent = Intent(activity, ListActivity::class.java)
            intent.putExtra(IntentKeys.IS_BOOKMARK, true)
            startActivity(intent)
        }

    }

    private fun getAllSurahs() {
        viewModel.allSurahs.subscribe({
            surahz_rv.layoutManager = LinearLayoutManager(activity)
            surahz_rv.adapter = SurahListAdapter(it)
        },{
            Log.d("Throwable", "error")
        })
    }

}
