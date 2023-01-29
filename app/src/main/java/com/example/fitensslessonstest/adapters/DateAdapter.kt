package com.example.fitensslessonstest.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.fitensslessonstest.R
import com.example.fitensslessonstest.models.Lesson
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DateAdapter(
//    private val listLessons: List<Lesson>
) : Adapter<DateAdapter.DateViewHolder>() {
    inner class DateViewHolder(itemView: View) : ViewHolder(itemView)

    var lessonsForDate = mapOf<String, List<Lesson>>()
    private val differCallback = object : DiffUtil.ItemCallback<LocalDate>() {
        override fun areItemsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
            return newItem == oldItem
        }

        override fun areContentsTheSame(oldItem: LocalDate, newItem: LocalDate): Boolean {
            return newItem == oldItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        return DateViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.date_item, parent, false)
        )
    }


    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val currentDate = differ.currentList[position]
        holder.itemView.apply {
            val dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM")
            val recyclerView = findViewById<RecyclerView>(R.id.rvLessons)
            findViewById<TextView>(R.id.tvDate).text = dateTimeFormatter.format(currentDate)
            recyclerView.adapter = LessonAdapter(lessonsForDate[currentDate.toString()]!!)
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}