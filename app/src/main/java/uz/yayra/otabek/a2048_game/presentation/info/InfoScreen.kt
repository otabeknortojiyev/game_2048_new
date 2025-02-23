package uz.gita.otabek.a2048game.presentation.info

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import uz.gita.otabek.a2048game.R
import uz.gita.otabek.a2048game.databinding.ScreenInfoBinding
import uz.gita.otabek.a2048game.presentation.home.HomeScreen
import uz.gita.otabek.a2048game.utils.extantions.moveTo


class InfoScreen : Fragment(R.layout.screen_info) {
    private var _binding: ScreenInfoBinding? = null
    private val binding: ScreenInfoBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = ScreenInfoBinding.bind(view)

        setActions()
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    moveTo(HomeScreen())
                }
            })

    }

    private fun setActions() {
        binding.tvTelegram.setOnClickListener {
            val url = "https://t.me/future_software_developer"
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(url))
            startActivity(i)
        }
        binding.ivInfoBack.setOnClickListener {
            moveTo(HomeScreen())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}