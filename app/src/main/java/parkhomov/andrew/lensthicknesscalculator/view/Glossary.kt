package parkhomov.andrew.lensthicknesscalculator.view

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.core.view.doOnPreDraw
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.data.GlossaryItem
import parkhomov.andrew.lensthicknesscalculator.databinding.GlossaryBinding
import parkhomov.andrew.lensthicknesscalculator.extencions.argument
import parkhomov.andrew.lensthicknesscalculator.extencions.getDrawableFromId


class Glossary : DialogFragment(R.layout.glossary) {

    private val glossary by argument<GlossaryItem>(GLOSSARY)
    private val binding by viewBinding(GlossaryBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)

        binding.textViewTitle.text = glossary.title
        binding.textViewDescription.text = glossary.description
        binding.imageViewPicture.setImageDrawable(getDrawableFromId(glossary.imageId))

        val screenWidth = Resources.getSystem().displayMetrics.heightPixels
        binding.scrollViewForText.doOnPreDraw {
            if (it.measuredHeight > screenWidth / 2) {
                binding.scrollViewForText.updateLayoutParams<LayoutParams> {
                    height = (screenWidth / 2.6).toInt()
                }
            }
        }
    }

    fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, GLOSSARY)

    companion object {
        private const val GLOSSARY = "GLOSSARY"
        fun getInstance(item: GlossaryItem) = Glossary().apply {
            arguments = Bundle(1).apply {
                putParcelable(GLOSSARY, item)
            }
        }
    }
}
