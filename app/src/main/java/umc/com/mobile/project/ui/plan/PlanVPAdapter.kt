package umc.com.mobile.project.ui.plan

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class PlanVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val fragmentList: MutableList<Fragment> = arrayListOf()
    private val fragmentTitleList: MutableList<String> = arrayListOf()

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

    // 타이틀을 포함하여 프래그먼트를 추가하는 메소드
    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
        notifyItemInserted(fragmentList.size - 1)
    }

    // 프래그먼트를 제거하는 메소드
    fun removeFragment() {
        if (fragmentList.isNotEmpty()) {
            fragmentList.removeLast()
            fragmentTitleList.removeLast()
            notifyItemRemoved(fragmentList.size)
        }
    }

    // 타이틀을 가져오는 메소드
    fun getTabTitle(position: Int): String {
        return fragmentTitleList[position]
    }


}
