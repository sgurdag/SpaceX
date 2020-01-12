package com.spacex.rockets.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.viewpager.widget.PagerAdapter
import com.spacex.rockets.R
import com.squareup.picasso.Picasso

class ImageSliderAdapter constructor(private val context: Context, private val images: List<String>) :
    PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean =
        view === `object` as RelativeLayout


    override fun getCount(): Int = images.size

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val v = inflater.inflate(R.layout.item_slider_image, container, false)
        val img: ImageView = v.findViewById(R.id.image)

        Picasso.get().load(images[position]).into(img)

        container.addView(v)

        return v
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}