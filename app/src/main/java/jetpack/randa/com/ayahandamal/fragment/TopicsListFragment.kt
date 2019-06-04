package jetpack.randa.com.ayahandamal.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jetpack.randa.com.ayahandamal.AyahAndAmalApplication

import jetpack.randa.com.ayahandamal.R
import jetpack.randa.com.ayahandamal.adapter.TopicsAdapter
import jetpack.randa.com.ayahandamal.viewModel.Add3amalViewModel
import jetpack.randa.com.ayahandamal.viewModel.TopicsViewModel
import kotlinx.android.synthetic.main.fragment_topics_list.*
import javax.inject.Inject

class TopicsListFragment : Fragment() {
    @Inject
lateinit var add3amalViewModel: Add3amalViewModel
    @Inject
    lateinit var topicsViewModel: TopicsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity?.application as AyahAndAmalApplication).component.inject(this)
        return inflater.inflate(R.layout.fragment_topics_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /* add3amalViewModel.saveTafsirs().subscribe({
            Log.d("","");
        },{
            Log.d("","");
        })*/
        topicsViewModel.allTopics.subscribe({
            Log.d("","")
            topics_rv.layoutManager = LinearLayoutManager(activity)
            topics_rv.adapter = TopicsAdapter(it)

        },{
           Log.d("","")
        })

    }
}
