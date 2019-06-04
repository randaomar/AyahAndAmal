package jetpack.randa.com.ayahandamal.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import jetpack.randa.com.ayahandamal.AyahAndAmalApplication
import jetpack.randa.com.ayahandamal.R
import jetpack.randa.com.ayahandamal.model.Ayah
import jetpack.randa.com.ayahandamal.model.Surah
import jetpack.randa.com.ayahandamal.viewModel.Add3amalViewModel
import javax.inject.Inject
import kotlinx.android.synthetic.main.activity_add3amal.*
import android.R.attr.data
import android.R.attr.progress
import android.app.AlarmManager
import android.app.PendingIntent
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.widget.*
import jetpack.randa.com.ayahandamal.IntentKeys
import jetpack.randa.com.ayahandamal.IntentKeys.Al3AMAL_ID_FOR_ALARM
import jetpack.randa.com.ayahandamal.broadcastReciever.AlarmReciever
import jetpack.randa.com.ayahandamal.model.Al3amal
import jetpack.randa.com.ayahandamal.model.Al3amalCombined
import java.lang.Exception
import java.util.*


class Add3amalActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: Add3amalViewModel
    private var selectedAyahNumStr = "1"
    private var selectedSurahNumStr = "1"
    private var surahsList: List<Surah> = ArrayList()
    private var ayahsList: List<Ayah> = ArrayList()

    private var viewMode: Boolean = false
    private var progressDialog: ProgressDialog? = null
    private var isRedirectedFromQuranAndFirstSetup: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add3amal)
        supportActionBar?.hide()
        (application as AyahAndAmalApplication).component.inject(this)

        getAllSurahs()
        setListeners()
        Log.d("","")
       if (intent.extras != null && intent.getIntExtra(IntentKeys.SELECTED_SURAH_ID, 0) != 0 &&
           intent.getIntExtra(IntentKeys.SELECTED_AYAH_ID, 0) != 0) {
           isRedirectedFromQuranAndFirstSetup = true
           //getAyahsOfSurah(intent.getIntExtra(IntentKeys.SELECTED_SURAH_ID, 0).toString())
           surah_spinner.post{surah_spinner.setSelection(intent.getIntExtra(IntentKeys.SELECTED_SURAH_ID, 0)-1)}

        } else  if(intent.extras != null && intent.getSerializableExtra(IntentKeys.COMBINED_3AMAL_OBJECT) != null){
           button_save.visibility = View.GONE
           //notification_switch.visibility = View.GONE
           ayah_txt.visibility = View.VISIBLE
           ayah_spinner.visibility = View.GONE
           surah_spinner.visibility = View.GONE
           try{
               val amal = intent.getSerializableExtra(IntentKeys.COMBINED_3AMAL_OBJECT) as Al3amalCombined
               al3amal_et.setText(amal.al3amal.al3amal)
               al3amal_et.isFocusable = false
               al3amal_et.isEnabled = false
               ayah_txt.text = amal.ayahText
               viewMode = true
           }catch(ex: Exception){}
       } else {
           getAyahsOfSurah("1")
       }
    }


    private var timestamp: Long = 0

    private fun setListeners() {
        back_btn.setOnClickListener{onBackPressed()}
        surah_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val surahId = position+1
                selectedSurahNumStr = surahId.toString()
                getAyahsOfSurah(surahId.toString())

            }
        }
        ayah_spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val ayahId = position+1
                selectedAyahNumStr = ayahId.toString()
            }
        }

        notification_switch.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked){
                    val mcurrentTime = Calendar.getInstance()
                    val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
                    val minute = mcurrentTime.get(Calendar.MINUTE)
                    val mTimePicker: TimePickerDialog
                     mTimePicker = TimePickerDialog(this@Add3amalActivity,
                        TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                            val newCal = Calendar.getInstance()
                            newCal.set(Calendar.HOUR_OF_DAY, selectedHour)
                            newCal.set(Calendar.MINUTE, selectedMinute)
                            if(mcurrentTime.timeInMillis > newCal.timeInMillis) {
                                newCal.add(Calendar.DATE,1)
                            }
                            timestamp = newCal.timeInMillis
                        }, hour, minute, true
                    )
                    mTimePicker.setOnCancelListener {
                       timestamp = 0
                    }//Yes 24 hour time
                    mTimePicker.setTitle(resources.getString(R.string.choose_alarm))
                    mTimePicker.setCancelable(false)
                    mTimePicker.show()
                }
            }

        })
        button_save.setOnClickListener { it ->
            //save 3amal
            viewModel.insert3amal(Al3amal(selectedSurahNumStr, selectedAyahNumStr, al3amal_et.text.toString()), timestamp).subscribe({
                if (timestamp > 0)
                    setAlarm(it)
                finish()
            },{
                Log.d("Throwable", "Error")
            })
        }
    }

    private fun setAlarm(id: Long) {
        val intent = Intent(applicationContext, AlarmReciever::class.java)
        intent.putExtra(Al3AMAL_ID_FOR_ALARM, id)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext,
            0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP, timestamp, pendingIntent
        )
    }


    private fun getAyahsOfSurah(id: String) {
        viewModel.getAyahsOfSurah(id).subscribe({
          ayahsList = it
            setupAyahs()
        },{
            Log.d("Throwable", "error")
        })
    }

    private fun setupAyahs() {

        val adapter = object :ArrayAdapter<Ayah>(this, android.R.layout.simple_spinner_dropdown_item, ayahsList){
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val textView = View.inflate(context, android.R.layout.simple_spinner_item, null) as TextView
                val ayahNum = position+1
                textView.text = ayahNum.toString()+" "+ayahsList[position].ayahText
                textView.setTextColor(resources.getColor(R.color.colorPrimary))
                return textView
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val tv = super.getView(position, convertView, parent) as TextView
                val ayahNum = position+1
                tv.text = ayahNum.toString()+" "+ayahsList[position].ayahText
                tv.setTextColor(resources.getColor(R.color.colorPrimary))
                return tv
            }
        }

        ayah_spinner.adapter = adapter
        if(isRedirectedFromQuranAndFirstSetup){
            ayah_spinner.post {ayah_spinner.setSelection(intent.getIntExtra(IntentKeys.SELECTED_AYAH_ID, 0)-1)}
            isRedirectedFromQuranAndFirstSetup = false
        }

    }

    private fun getAllSurahs() {
        viewModel.allSurahs.subscribe({
            surahsList = it
            setupSurahs()
        },{
            Log.d("Throwable", "error")
        })
    }

    private fun setupSurahs() {

        val adapter = object :ArrayAdapter<Surah>(this, android.R.layout.simple_spinner_dropdown_item, surahsList){
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val textView = View.inflate(context, android.R.layout.simple_spinner_item, null) as TextView
                textView.text = surahsList[position].surahName
                textView.setTextColor(resources.getColor(R.color.colorPrimary))
                return textView
            }
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val tv = super.getView(position, convertView, parent) as TextView
                tv.text = surahsList[position].surahName
                tv.setTextColor(resources.getColor(R.color.colorPrimary))
                return tv
            }
        }

        surah_spinner.adapter = adapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(intent.extras != null && intent.getSerializableExtra(IntentKeys.COMBINED_3AMAL_OBJECT) != null && timestamp>0){
            val amal = intent.getSerializableExtra(IntentKeys.COMBINED_3AMAL_OBJECT) as Al3amalCombined
            setAlarm(amal.al3amal.id)
        }
        finish()

    }


}
