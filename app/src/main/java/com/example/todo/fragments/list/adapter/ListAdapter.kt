package com.example.todo.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.models.ToDoData
import com.example.todo.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.ToDoDataViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<ToDoData>() {
        override fun areItemsTheSame(oldItem: ToDoData, newItem: ToDoData): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ToDoData, newItem: ToDoData): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,diffCallBack)

    class ToDoDataViewHolder(var binding: RowLayoutBinding)
        : RecyclerView.ViewHolder(binding.root)
        {
            fun bind(toDoData: ToDoData){
                binding.toDoData=toDoData
                binding.executePendingBindings()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoDataViewHolder {
        val binding = RowLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ToDoDataViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoDataViewHolder, position: Int) {
        holder.bind(differ.currentList.get(position))
    }

    override fun getItemCount(): Int = differ.currentList.size

}