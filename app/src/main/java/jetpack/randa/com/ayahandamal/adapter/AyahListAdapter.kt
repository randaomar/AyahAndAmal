package jetpack.randa.com.ayahandamal.adapter

import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jetpack.randa.com.ayahandamal.R
import jetpack.randa.com.ayahandamal.model.Ayah
import kotlinx.android.synthetic.main.aya_item_layout.view.*
import android.widget.Toast
import android.R.attr.button
import android.content.Intent
import android.support.v7.widget.PopupMenu
import android.view.MenuItem
import jetpack.randa.com.ayahandamal.IntentKeys
import jetpack.randa.com.ayahandamal.activity.Add3amalActivity
import jetpack.randa.com.ayahandamal.model.AyahDecoration
import jetpack.randa.com.ayahandamal.model.Bookmark


open class AyahListAdapter (open val list: List<Ayah>, val isTafseerOn: Boolean, val isTafseerColorOn: Boolean, val addToBookmarksFun: (ayahNum:Int, surahNum:Int, ayahText: String)->Unit) : RecyclerView.Adapter<AyahListAdapter.AyahListHolder>() {

    private var ayahDecoList: List<AyahDecoration>? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AyahListAdapter.AyahListHolder =
        AyahListHolder(LayoutInflater.from(p0.context).inflate(R.layout.aya_item_layout, p0, false))

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AyahListAdapter.AyahListHolder, position: Int) {
        if(!isTafseerOn){
            holder.itemView.tafseer_text.visibility = View.GONE
        }
        if(isTafseerColorOn){
            setBgColor(holder,list[position])
        }
        ayahDecoList?.let{ list1 ->
           if(list1.any { it.quartHezb == list[position].getAyahIntNum() && it.surahNum ==1 }){
               holder.itemView.hezb_img.visibility = View.VISIBLE
           } else {
               holder.itemView.hezb_img.visibility = View.GONE
           }
        }
        holder.itemView.tafseer_text.text = list[position].tafseer
        holder.itemView.ayah_text.text = list[position].ayahText
        holder.itemView.ayah_num.text = list[position].ayahNum
        val tfAyahNum = Typeface.createFromAsset(holder.itemView.context.assets, "fonts/BDavat.ttf")
        holder.itemView.ayah_num.typeface = tfAyahNum
        holder.itemView.ayah_num_layout.setOnClickListener{ view ->


            //
            val popup = PopupMenu(holder.itemView.context, holder.itemView.ayah_num_layout)
            popup.menuInflater.inflate(R.menu.ayah_popup_menu, popup.menu)
            if (!isTafseerOn){
                popup.menu.findItem(R.id.show_tafseer).isVisible = true
            }
            popup.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.show_tafseer -> holder.itemView.tafseer_text.visibility =
                            if (holder.itemView.tafseer_text.visibility == View.VISIBLE) View.GONE else View.VISIBLE
                    R.id.add_deed ->{
                        val intent = Intent(holder.itemView.context, Add3amalActivity::class.java)
                        intent.putExtra(IntentKeys.SELECTED_SURAH_ID, list[position].getSurahIntNum())
                        intent.putExtra(IntentKeys.SELECTED_AYAH_ID, list[position].getAyahIntNum())
                        holder.itemView.context.startActivity(intent)
                         }
                    R.id.add_to_bookmarks ->{
                        addToBookmarksFun.invoke(list[position].ayahNum!!.toInt(), list[position].surahNum!!.toInt(), list[position].ayahText!!)
                    }
                }
                true
            }

            popup.show()//showing popup menu
        }

    }
    fun setAyahDecoration(ayahDec: List<AyahDecoration>){
        this.ayahDecoList = ayahDec
    }
//TODO: refactor this shit code to enum
    internal fun setBgColor(ayahViewHolder: AyahListHolder, ayah: Ayah) {
        if (ayah.ayahColor != null) {
            if (ayah.ayahColor.equals("E")) {
                ayahViewHolder.itemView.ayahRowLayout.setBackgroundColor(ayahViewHolder.itemView.context.resources.getColor(R.color.espresso))
            } else if (ayah.ayahColor.equals("O")) {
                ayahViewHolder.itemView.ayahRowLayout.setBackgroundColor(ayahViewHolder.itemView.context.resources.getColor(R.color.orange))
            } else if (ayah.ayahColor.equals("Y")) {
                ayahViewHolder.itemView.ayahRowLayout.setBackgroundColor(ayahViewHolder.itemView.context.resources.getColor(R.color.yellow))
            } else if (ayah.ayahColor.equals("R")) {
                ayahViewHolder.itemView.ayahRowLayout.setBackgroundColor(ayahViewHolder.itemView.context.resources.getColor(R.color.red))
            } else if (ayah.ayahColor.equals("B")) {
                ayahViewHolder.itemView.ayahRowLayout.setBackgroundColor(ayahViewHolder.itemView.context.resources.getColor(R.color.blue))
            } else if (ayah.ayahColor.equals("P")) {
                ayahViewHolder.itemView.ayahRowLayout.setBackgroundColor(ayahViewHolder.itemView.context.resources.getColor(R.color.purple))
            } else if (ayah.ayahColor.equals("G")) {
                ayahViewHolder.itemView.ayahRowLayout.setBackgroundColor(ayahViewHolder.itemView.context.resources.getColor(R.color.green))
            }
        }
    }

    class AyahListHolder (val view : View)  : RecyclerView.ViewHolder(view)
}