package uz.gita.otabek.a2048game.presentation.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.gita.otabek.a2048game.R
import uz.gita.otabek.a2048game.databinding.ScreenSettingsBinding

class SettingsScreen : Fragment(R.layout.screen_settings) {
    private var _binding: ScreenSettingsBinding? = null
    private val binding: ScreenSettingsBinding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = ScreenSettingsBinding.bind(view)
    }
}