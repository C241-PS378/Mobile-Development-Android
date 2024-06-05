package com.capstone.cuansampah.view.adapter


import com.capstone.cuansampah.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.os.Handler
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.capstone.cuansampah.data.local.ItemImageSlider

class SliderHomeAdapter(private val context: Context, private var imgList: List<ItemImageSlider>) : PagerAdapter(){
    private var currentPage = 0
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    override fun getCount() = imgList.size
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.image_slider_item, container, false)
        val ivImages = view.findViewById<ImageView>(R.id.imageView)
        ivImages.setImageResource(imgList[position].image)
        val vp = container as ViewPager
        vp.addView(view, 0)
        return view
    }
    fun autoslide(viewPager: ViewPager){
        handler= Handler()
        runnable = object : Runnable {
            override fun run() {
                if (currentPage == viewPager.adapter?.count?.minus(1)) {
                    currentPage = 0
                } else {
                    currentPage++
                }
                viewPager.setCurrentItem(currentPage, true)
                handler.postDelayed(this, 2000)
            }
        }
        Handler().postDelayed(runnable,1000)
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val vp = container as ViewPager
        val view = `object` as View
        vp.removeView(view)
    }
}