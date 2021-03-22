package com.example.livebarnapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.widget.ViewPager2
import com.example.livebarnapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity(), OnShareWithSportInterface {
    private lateinit var binding : ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        lifecycleOwner = this
        var tabName = arrayListOf<String>()
        tabName.add("Hokey")
        tabName.add("Baseball")
        tabName.add("Soccer")
        tabName.add("Volleyball")
        tabName.add("Baseball")

        binding.surfacePerViewPager.adapter = SurfacePagerAdapter(this, tabName.size)
        binding.surfacePerViewPager.registerOnPageChangeCallback(surfacePagerAdaptercallback)
        binding.surfacePerViewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.tabLayout, binding.surfacePerViewPager) { tab, position ->
            tab.text = tabName[position].substringBefore(' ')
        }.attach()
        binding.surfacePerViewPager.layoutDirection = ViewPager2.LAYOUT_DIRECTION_LTR
        binding.tabLayout.layoutDirection = View.LAYOUT_DIRECTION_LTR

    }
    lateinit var lifecycleOwner: LifecycleOwner
    var surfacePagerAdaptercallback = object : ViewPager2.OnPageChangeCallback() {
        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun onPageSelected(position: Int) {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.surfacePerViewPager.unregisterOnPageChangeCallback(surfacePagerAdaptercallback)
    }

    override fun onShowSoccer(soccer: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun onShowBaseBall(baseball: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun onShowBasketball(basketball: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun onShowHokey(hokey: ArrayList<String>) {
        TODO("Not yet implemented")
    }

    override fun onShowVolleyball(volleyball: ArrayList<String>) {
        TODO("Not yet implemented")
    }

}