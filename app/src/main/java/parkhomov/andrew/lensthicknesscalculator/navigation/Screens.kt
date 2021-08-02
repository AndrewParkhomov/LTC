package parkhomov.andrew.lensthicknesscalculator.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import parkhomov.andrew.lensthicknesscalculator.view.*

fun compareList() = FragmentScreen("CompareList") {
    CompareList()
}

fun diameter() = FragmentScreen("Diameter") {
    Diameter()
}

fun glossary() = FragmentScreen("Glossary") {
    Glossary()
}

fun thickness() = FragmentScreen("Thickness") {
    Thickness()
}

fun transposition() = FragmentScreen("Transposition") {
    Transposition()
}