package com.spacex.rockets.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spacex.rockets.databinding.RocketRowBinding
import com.spacex.repository.entities.rockets.RocketEntity

class RocketsAdapter(
    private val context: Context,
    val selectedRocketCallback: (RocketEntity) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //region Functions

    private var rockets: List<RocketEntity> = listOf()

    fun updateData(rockets: List<RocketEntity>, isActiveOnly: Boolean) {

        if (isActiveOnly)
            this.rockets = rockets.filter { it.active }
        else
            this.rockets = rockets

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        RocketsViewHolder(
            context,
            RocketRowBinding.inflate(
                LayoutInflater.from(
                    context
                ), parent, false
            )
        )

    override fun getItemCount(): Int = rockets.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as RocketsViewHolder).bind(rockets[position]) {
            selectedRocketCallback(it)
        }

    }
    //endregion
}

class RocketsViewHolder(private val context: Context, private val binding: RocketRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    //region Functions

    @SuppressLint("SetTextI18n")
    fun bind(rocket: RocketEntity, clickListener: (RocketEntity) -> Unit) {

        binding.entity = rocket
        binding.vpRocketImages.adapter = ImageSliderAdapter(context, rocket.flickr_images)
        binding.dots.attachViewPager(binding.vpRocketImages)
        binding.root.setOnClickListener { clickListener(rocket) }
        binding.tvEngineCount.text = "Engines Count : ${rocket.engines.number}"
        binding.executePendingBindings()

    }
    //endregion
}
