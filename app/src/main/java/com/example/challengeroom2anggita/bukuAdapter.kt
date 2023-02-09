package com.example.challengeroom2anggita

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.challengeroom2anggita.room.tbbuku
import kotlinx.android.synthetic.main.activity_buku_adapter.view.*

class bukuAdapter (private val buku: ArrayList<tbbuku>, private val listener: OnadapterListener):RecyclerView.Adapter<bukuAdapter.bukuViewHolder>(){
    class bukuViewHolder ( val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): bukuViewHolder {
        return bukuViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_buku_adapter,parent,false)
        )
    }

    override fun onBindViewHolder(holder: bukuViewHolder, position: Int) {
        val book = buku[position]
        holder.view.tv_ktg.text = book.kategori
        holder.view.tv_judul.text = book.judul
        //Mengklik
        holder.view.tv_judul.setOnClickListener{
            listener.onCLick(book)
        }
        holder.view.imedit.setOnClickListener{
            listener.onUpdate(book)
        }
        holder.view.imdelete.setOnClickListener{
            listener.onDelete(book)
        }
    }

    override fun getItemCount()= buku.size
    fun setData(list: List<tbbuku>){
        buku.clear()
        buku.addAll(list)
        notifyDataSetChanged()
    }
    interface OnadapterListener{
        fun onCLick(tbBuku: tbbuku)
        fun onUpdate(tbBuku: tbbuku)
        fun onDelete(tbBuku: tbbuku)
    }
}