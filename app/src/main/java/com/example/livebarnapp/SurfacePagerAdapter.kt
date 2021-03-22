package com.example.livebarnapp

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.livebarnapp.framents.SurfaceSoccerFragment

class SurfacePagerAdapter(activity: AppCompatActivity, val itemsCount: Int) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return itemsCount
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return SurfaceSoccerFragment.newInstance(0)
            1 -> return SurfaceSoccerFragment.newInstance(1)
            2 -> return SurfaceSoccerFragment.newInstance(2)
            3 -> return SurfaceSoccerFragment.newInstance(3)
            5 ->return SurfaceSoccerFragment.newInstance(4)
            else -> return SurfaceSoccerFragment.newInstance(0)
        }
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

}