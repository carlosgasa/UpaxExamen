package com.examen.carlosgs.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.examen.carlosgs.R
import com.examen.carlosgs.data.model.FileModel
import com.examen.carlosgs.databinding.ItemFileBinding

class FileAdapter (private var files: List<FileModel> = emptyList()) : RecyclerView.Adapter<FileAdapter.ViewHolder>(){

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_file, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(files[position],context )
    }

    override fun getItemCount(): Int {
        return files.size

    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<FileModel>) {
        files = list
        notifyDataSetChanged()
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemFileBinding.bind(view)

        fun bind(fileModel: FileModel, context : Context) {
            Glide.with(context).load(fileModel.url).into(binding.ivFile)
        }

    }



}