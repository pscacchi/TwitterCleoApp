package ar.scacchipa.twittercloneapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainViewPagerAdapter(
    manager: Fragment
) : FragmentStateAdapter(manager) {
    private val colorList = arrayListOf("#00ff00", "#ffffff", "#0000ff", "#f0f0f0")
    override fun getItemCount(): Int {
        return colorList.size
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> FragmentHome()
            else -> {
                val fragment = FragmentSearch()
                fragment.arguments = Bundle().apply {
                    putString("color", colorList[position])
                }
                fragment
            }
        }
    }
}