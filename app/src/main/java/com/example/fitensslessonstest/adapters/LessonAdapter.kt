package com.example.fitensslessonstest.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.fitensslessonstest.R
import com.example.fitensslessonstest.models.Lesson
import java.time.Duration
import java.time.LocalTime


class LessonAdapter(private val listLessons: List<Lesson>) :
    RecyclerView.Adapter<LessonAdapter.LessonViewHolder>() {

    inner class LessonViewHolder(itemView: View) : ViewHolder(itemView)


//    private val differCallback = object : DiffUtil.ItemCallback<Lesson>() {
//        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
//            return oldItem.appointmentId == newItem.appointmentId
//        }
//
//        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
//            return newItem == oldItem
//        }
//
//    }
//
//    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        return LessonViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.lesson_item, parent, false
            )
        )

    }


    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val lesson = listLessons[position]
        holder.itemView.apply {
            findViewById<View>(R.id.colorOfLesson).setBackgroundColor(Color.parseColor(lesson.color))
            findViewById<TextView>(R.id.tvNameOfLesson).text = lesson.name
            findViewById<TextView>(R.id.tvStartTime).text = lesson.startTime
            findViewById<TextView>(R.id.tvEndTime).text = lesson.endTime
            findViewById<TextView>(R.id.tvDurationOfLesson).text =
                getDuration(lesson.startTime, lesson.endTime)
            findViewById<TextView>(R.id.tvTrainerFullName).text = lesson.coachId
            findViewById<TextView>(R.id.tvLocationOfLesson).text = lesson.place
        }
    }

    override fun getItemCount(): Int {
        return listLessons.size
    }


    private fun getDuration(startTime: String, endTime: String): String {
        val duration = Duration.between(LocalTime.parse(endTime), LocalTime.parse(startTime))
            .toMinutes() * (-1)
        val hours = duration / 60
        val minutes = duration % 60
        return if (hours == 0L) {
            String.format("%02d мин.", minutes)
        } else if (minutes == 0L) {
            String.format("%d ч.", hours)
        } else {
            String.format("%d ч. %02d мин.", hours, minutes)
        }
    }
}