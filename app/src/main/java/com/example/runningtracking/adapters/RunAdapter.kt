package com.example.runningtracking.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.runningtracking.R
import com.example.runningtracking.db.Run
import com.example.runningtracking.others.TrackingUtility
import java.text.SimpleDateFormat
import java.util.*

class RunAdapter : RecyclerView.Adapter<RunAdapter.RunViewHolder>() {
    class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    val diffCallback = object : DiffUtil.ItemCallback<Run>(){
        override fun areItemsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Run, newItem: Run): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    val differ = AsyncListDiffer(this, diffCallback)
    fun sumbitList(list: List<Run>) = differ.submitList(list)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_run,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        val img = holder.itemView.findViewById<ImageView>(R.id.ivRunImage)
        val tvDate = holder.itemView.findViewById<TextView>(R.id.tvDate)
        val tvAvgSpeed = holder.itemView.findViewById<TextView>(R.id.tvAvgSpeed)
        val tvDistance = holder.itemView.findViewById<TextView>(R.id.tvDistance)
        val tvTime = holder.itemView.findViewById<TextView>(R.id.tvTime)
        val tvCalories = holder.itemView.findViewById<TextView>(R.id.tvCalories)
        holder.itemView.apply {
            Glide.with(this)
                .load(run.img)
                .into(img)
            val calendar = Calendar.getInstance().apply {
                timeInMillis = run.timestamp
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            tvDate.text = dateFormat.format(calendar.time)

            val avgSpeed = "${run.avgSpeedInKMH}km/h"
            tvAvgSpeed.text = avgSpeed

            val distance = "${run.distanceInMeters / 1000f}km"
            tvDistance.text = distance

            tvTime.text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

            val caloriesBurned = "${run.caloriesBurned}kcal"
            tvCalories.text = caloriesBurned

        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}