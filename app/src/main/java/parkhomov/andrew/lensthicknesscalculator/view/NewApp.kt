package parkhomov.andrew.lensthicknesscalculator.view

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import by.kirich1409.viewbindingdelegate.viewBinding
import parkhomov.andrew.lensthicknesscalculator.R
import parkhomov.andrew.lensthicknesscalculator.databinding.NewAppBinding


class NewApp : DialogFragment(R.layout.new_app) {

    private val binding by viewBinding(NewAppBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )

        binding.closeIV.setOnClickListener {
            dismiss()
        }
        binding.buttonOpenOpticLL.setOnClickListener {
            openPlayMarket("app.spectra.optic")
        }
        binding.buttonOpenWorkshopLL.setOnClickListener {
            openPlayMarket("app.spectra.workshop")
        }
    }

    private fun openPlayMarket(appId: String) {
        try {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=$appId")
                )
            )
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=$appId")
                )
            )
        }
    }

    fun show(fragmentManager: FragmentManager) = super.show(fragmentManager, NEW_APP)

    companion object {
        private const val NEW_APP = "NEW_APP"
        val instance = NewApp()
    }
}
