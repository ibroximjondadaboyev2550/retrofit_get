package com.example.retrofit.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.databinding.ItemRvBinding
import com.example.retrofit.models.MyTodo

class RvAdapter (var list:List<MyTodo>, var rvAction: RvAction):RecyclerView.Adapter<RvAdapter.Vh>() {


    inner class Vh(val itemRvBinding: ItemRvBinding):RecyclerView.ViewHolder(itemRvBinding.root){
        fun onBind(myTodo: MyTodo){
            itemRvBinding.tvId.text = myTodo.id.toString()
            itemRvBinding.tvTitle.text = myTodo.sarlavha
            itemRvBinding.tvIzoh.text = myTodo.izoh
            itemRvBinding.tvSana.text = myTodo.sana
            itemRvBinding.tvStatus.text= if (myTodo.bajarildi) "Bajarildi" else "Endi bajariladi"

            itemRvBinding.root.setOnLongClickListener {
                rvAction.deleteTodo(myTodo)
                true
            }

            itemRvBinding.root.setOnClickListener {
                rvAction.editTodo(myTodo)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):Vh{
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Vh, position: Int){
        holder.onBind(list[position])
    }

    interface RvAction{
        fun deleteTodo(myTodo: MyTodo)
        fun editTodo(myTodo: MyTodo)
    }
}