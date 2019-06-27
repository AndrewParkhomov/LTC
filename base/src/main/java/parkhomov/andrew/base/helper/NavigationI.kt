package parkhomov.andrew.base.helper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import parkhomov.andrew.base.data.result.CalculatedData

interface NavigationI {
    fun getSelectedFragment(position: Int): Fragment
    fun showLanguageDialog(supportFragmentManager: FragmentManager)
    fun showResultDialog(childFragmentManager: FragmentManager, caseThickness: CalculatedData)
}