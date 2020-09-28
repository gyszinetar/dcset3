package hu.prooktatas.dcset3.adapter


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import hu.prooktatas.dcset3.R
import hu.prooktatas.dcset3.Uzenet


class MyAdapter(var list: List<Uzenet>,var lastid:Int,var spinner: Spinner):RecyclerView.Adapter<MsgVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MsgVH {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.myrow, parent, false)
        return MsgVH(rootView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder: MsgVH, position: Int) {
        holder.text1.text=list[position].uzenet
        holder.text3.text=list[position].fogado
        holder.text2.text=list[position].kuldo
        if(list[position].id<=lastid) {
            holder.backgrnd.setBackgroundColor(Color.parseColor("#BDBDBD"));
        }
        if(list[position].id>lastid) {
            holder.backgrnd.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        holder.itemView.setOnClickListener {
            for(i in 0..spinner.adapter.count-1){
                if(spinner.adapter.getItem(i).toString()==list[position].fogado){spinner.setSelection(i)}
            }
        }



    }
}


class MsgVH(v: View): RecyclerView.ViewHolder(v) {
    val backgrnd : ConstraintLayout = itemView.findViewById(R.id.rowconlay)
    val text1: TextView = itemView.findViewById(R.id.textView)
    val text2: TextView = itemView.findViewById(R.id.textView2)
    val text3: TextView = itemView.findViewById(R.id.textView3  )

}