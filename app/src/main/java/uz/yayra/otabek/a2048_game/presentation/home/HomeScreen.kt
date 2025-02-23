package uz.gita.otabek.a2048game.presentation.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import uz.gita.otabek.a2048game.R
import uz.gita.otabek.a2048game.databinding.ScreenHomeBinding
import uz.gita.otabek.a2048game.domain.repository.GameRepository
import uz.gita.otabek.a2048game.presentation.game.GameScreen
import uz.gita.otabek.a2048game.presentation.home.mvp.HomeContract
import uz.gita.otabek.a2048game.presentation.home.mvp.HomePresenter
import uz.gita.otabek.a2048game.presentation.info.InfoScreen
import uz.gita.otabek.a2048game.utils.enums.PrefEnum
import uz.gita.otabek.a2048game.utils.extantions.moveTo

class HomeScreen : Fragment(R.layout.screen_home), HomeContract.View {
    private var _binding: ScreenHomeBinding? = null
    private val binding: ScreenHomeBinding get() = _binding!!
    private lateinit var repository: GameRepository
    private lateinit var presenter: HomeContract.Presenter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = ScreenHomeBinding.bind(view)
        init()
        setActions()
    }

    private fun init() {
        presenter = HomePresenter(this)
        repository = GameRepository.getInstance()
        if (GameRepository.getInstance().getSharedPreferences1()!!.getInt(PrefEnum.STEPS.value, 0) == 0) {
            binding.acbContinue.visibility = View.GONE
        } else {
            binding.acbContinue.visibility = View.VISIBLE
        }
    }

    private fun setActions() {
        binding.acbPlay.setOnClickListener {
            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.CURR_SCORE.value, 0).apply()
            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.STEPS.value, 0).apply()
            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.PREV_SCORE.value, 0).apply()
            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE1.value, -1).apply()
            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE2.value, -1).apply()
            repository.setSteps()
            repository.setPrevScore()
            repository.setMaxScore()
            repository.setCurrScore()
            repository.clearCurrMatrix()
            repository.clearPrevMatrix()
            moveTo(GameScreen())
        }
        binding.acbContinue.setOnClickListener {
            moveTo(GameScreen())
        }
        binding.acbInfo.setOnClickListener {
            moveTo(InfoScreen())
        }
        binding.acbQuit.setOnClickListener {
            requireActivity().finishAffinity()
        }
    }
}