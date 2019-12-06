package com.example.lernapp

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class MyFragmentAdapter(fm: FragmentManager, var context:Context, var fragmentList: List<QuestionFragment>):FragmentPagerAdapter(fm)
{
    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return StringBuilder("Frage ").append(position+1).toString()
    }

    internal var instance:MyFragmentAdapter?=null
}