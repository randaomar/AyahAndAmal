package jetpack.randa.com.ayahandamal.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import jetpack.randa.com.ayahandamal.AyahAndAmalApplication
import jetpack.randa.com.ayahandamal.PreferenceHelper

import jetpack.randa.com.ayahandamal.R
import jetpack.randa.com.ayahandamal.viewModel.Add3amalViewModel
import kotlinx.android.synthetic.main.fragment_settings.*
import java.lang.Error
import javax.inject.Inject


class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val helper = PreferenceHelper(activity!!)
        tafseer_color_switch.isChecked = helper.getTafseerColor()
        tafseer_switch.isChecked = helper.getTafseerText()

        tafseer_color_switch.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                helper.setTafseerColor(isChecked)
            }
        })

        tafseer_switch.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                helper.setTafseerText(isChecked)
            }
        })
    }
}
