package com.iknife.todo

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.e("TEST", "Fragment")

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager)
    }

    class ViewPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager){
        override fun getItem(position: Int): Fragment {
            return Page()
        }

        override fun getCount(): Int {
            return 2
        }
    }


}
