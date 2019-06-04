package jetpack.randa.com.ayahandamal.fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.Disposable
import jetpack.randa.com.ayahandamal.AyahAndAmalApplication
import jetpack.randa.com.ayahandamal.PreferenceHelper

import jetpack.randa.com.ayahandamal.R
import jetpack.randa.com.ayahandamal.activity.Add3amalActivity
import jetpack.randa.com.ayahandamal.adapter.A3mlyAdapter
import jetpack.randa.com.ayahandamal.model.Al3amal
import jetpack.randa.com.ayahandamal.model.Al3amalCombined
import jetpack.randa.com.ayahandamal.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModel: HomeViewModel
    private var disposable: Disposable? = null
    private var list: MutableList<Al3amalCombined> = mutableListOf()
    private  var adapter: A3mlyAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as AyahAndAmalApplication).component.inject(this)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fab.setOnClickListener {
            startActivity(Intent(activity, Add3amalActivity::class.java))
        }
        work_list_rv.layoutManager = LinearLayoutManager(activity)
    }

    override fun onResume() {
       setCoachmark()
        super.onResume()
       subscribeToGetA3mal()
    }

    private fun setCoachmark() {
        val helper = PreferenceHelper(activity!!)
        coachmark.visibility = if(helper.getShowcoachmark()) {
            helper.setShowCoachmark(false)
            View.VISIBLE
        } else View.GONE
        coachmark_btn.setOnClickListener { coachmark.visibility = View.GONE }
    }

    private fun subscribeToGetA3mal() {
        showEmptyState()
        adapter = A3mlyAdapter{
            viewModel.delete3amal(it).subscribe({
                subscribeToGetA3mal()
            },{
                Log.d("","")
            })
        }
        work_list_rv.adapter = adapter
        list = mutableListOf()
        disposable = viewModel.all3amal.subscribe({
            adapter?.addItem(it)
            showList()
        },{
            Log.d("ERROR", "ERROR")
        })
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun showEmptyState() {
        work_list_rv.visibility = View.GONE
        home_empty_state_layout.visibility = View.VISIBLE
    }
    private fun showList(){
        work_list_rv.visibility = View.VISIBLE
        home_empty_state_layout.visibility = View.GONE
    }


}
