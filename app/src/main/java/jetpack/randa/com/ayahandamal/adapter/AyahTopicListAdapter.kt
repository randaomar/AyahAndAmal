package jetpack.randa.com.ayahandamal.adapter

import android.view.View
import jetpack.randa.com.ayahandamal.model.Ayah
import kotlinx.android.synthetic.main.aya_item_layout.view.*


class AyahTopicListAdapter(
    override val list: MutableList<Ayah>,
    isTafseerOn: Boolean,
    isTafseerColorOn: Boolean,
    addToBookmarksFun: Function3<Int, Int, String, Unit>, val surahNameList: MutableList<String>
) : AyahListAdapter(list, isTafseerOn, isTafseerColorOn, addToBookmarksFun) {

    override fun onBindViewHolder(holder: AyahListAdapter.AyahListHolder, position: Int, payloads: List<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        holder.itemView.surah_name.visibility = View.VISIBLE
        holder.itemView.surah_name.text = "سورة "+surahNameList[position]+ " آيه رقم " +list[position].ayahNum

    }

    fun addAyahAndSurahName(ayah: Ayah, surahName: String){
        list.add(ayah)
        surahNameList.add(surahName)
        notifyDataSetChanged()
    }
}
