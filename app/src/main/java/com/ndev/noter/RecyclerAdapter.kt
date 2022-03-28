package com.ndev.noter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.ContextCompat.startActivity


class RecyclerAdapter(noteList: MutableList<String>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder> (){


    private var notes: MutableList<String> = noteList


    // Dam inflate la card_layout pentru o interfata mai frumasa
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout,parent,false)
        return ViewHolder(v)
    }


    /// Bindium titlul elementelor din recyclerview cu textul din notite in functie de pozitia la care suntem
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = notes[position]
    }

    /// Cat de lunga e lista ? Este egala cu numarul elementelor din vectorul de stringuri primit ca parametru
    override fun getItemCount(): Int {
        return notes.size
    }

    /// Spune cum se va comporta fiecare view
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        lateinit var itemImage: ImageView
        lateinit var itemTitle: TextView

        init{
            itemImage = itemView.findViewById(R.id.itemImage)
            itemTitle = itemView.findViewById(R.id.itemTitle)

            itemView.setOnClickListener {
                val position: Int = adapterPosition
                /// Cand apasam pe o notita se afieaza you ...
                Toast.makeText(itemView.context, "You clicked in ${notes[position]}",Toast.LENGTH_LONG).show()


                /// Cand apasam de asemenea deschidem un intent
                /// Sharesheet intent pentru a da share la notite
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type="text/plain"
                shareIntent.putExtra(Intent.EXTRA_TEXT, notes[position]);
                itemView.context.startActivity(Intent.createChooser(shareIntent,"Send to friends"))

            }

        }

    }



}