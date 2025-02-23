package uz.gita.otabek.a2048game.presentation.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import uz.gita.otabek.a2048game.R
import uz.gita.otabek.a2048game.databinding.DialogGameOverBinding
import uz.gita.otabek.a2048game.databinding.DialogIs2048Binding
import uz.gita.otabek.a2048game.databinding.ScreenGameBinding
import uz.gita.otabek.a2048game.domain.repository.GameRepository
import uz.gita.otabek.a2048game.presentation.game.mvp.GameContract
import uz.gita.otabek.a2048game.presentation.game.touchListener.GameTouchListener
import uz.gita.otabek.a2048game.presentation.home.HomeScreen
import uz.gita.otabek.a2048game.utils.enums.PrefEnum
import uz.gita.otabek.a2048game.utils.enums.SideEnum
import uz.gita.otabek.a2048game.utils.extantions.createDialog
import uz.gita.otabek.a2048game.utils.extantions.moveTo

class GameScreen : Fragment(R.layout.screen_game), GameContract.View {
    private var _binding: ScreenGameBinding? = null
    private val binding: ScreenGameBinding get() = _binding!!
    private lateinit var repository: GameRepository
    private val view = ArrayList<TextView>(16)
    private var isWin = false
    private var isOver = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = ScreenGameBinding.bind(view)
        init()
        loadViews()
        loadData()
        setActions()
        setCurrScore()
        setMaxScore()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                moveTo(HomeScreen())
            }
        })
    }

    private fun init() {
        //setBackground()
        repository = GameRepository.getInstance()
        if (repository.is2048()) {
            isWin = true
        }
    }

    private fun loadViews() {
        view.add(binding.btn1)
        view.add(binding.btn2)
        view.add(binding.btn3)
        view.add(binding.btn4)
        view.add(binding.btn5)
        view.add(binding.btn6)
        view.add(binding.btn7)
        view.add(binding.btn8)
        view.add(binding.btn9)
        view.add(binding.btn10)
        view.add(binding.btn11)
        view.add(binding.btn12)
        view.add(binding.btn13)
        view.add(binding.btn14)
        view.add(binding.btn15)
        view.add(binding.btn16)
    }

    private fun loadData() {
        val handler = Handler(Looper.getMainLooper())
        val fadeInAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_alpha)
        val scaleUpAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_scale_up)
        val scaleDownAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_scale_down)
        val scaleUpColorChangeAnimation = AnimationUtils.loadAnimation(requireContext(), R.anim.anim_scale_up_color_change)
        for (i in 0..3) {
            for (j in 0..3) {
                repository.matrix[i][j].apply {
                    view[i * 4 + j].text = if (this == 0) "" else this.toString()
                    when (view[i * 4 + j].text) {
                        "" -> {
                            if (repository.prevMatrix[i][j] != 0 && !repository.isOverUp && !repository.isOverDown && !repository.isOverLeft && !repository.isOverRight && repository.matrix[i][j] != repository.prevMatrix[i][j]) {
                                view[i * 4 + j].startAnimation(scaleDownAnimation)
                                handler.postDelayed({
                                    view[i * 4 + j].setBackgroundResource(R.drawable.bg_0)
                                    view[i * 4 + j].startAnimation(scaleUpColorChangeAnimation)
                                }, 100)
                            } else {
                                view[i * 4 + j].setBackgroundResource(R.drawable.bg_0)
                            }
                        }

                        "2" -> {
                            if (repository.getSharedPreferences1()!!.getInt(PrefEnum.STEPS.value, 0) == 0) {
                                view[i * 4 + j].setBackgroundResource(R.drawable.b_2)
                                view[i * 4 + j].startAnimation(scaleUpAnimation)
                            } else if (repository.getSharedPreferences1()!!.getInt(PrefEnum.STEPS.value, 0) != 0
                                && ((i * 10 + j) == repository.getSharedPreferences1()!!.getInt(PrefEnum.NEW_ELEMENT_COORDINATE2.value, -1))
                                && repository.matrix[i][j] == repository.prevMatrix[i][j]
                            ) {
                                view[i * 4 + j].setBackgroundResource(R.drawable.b_2)
                                view[i * 4 + j].startAnimation(scaleUpAnimation)
                                repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE2.value, -1).apply()
                            } else if (repository.matrix[i][j] != repository.prevMatrix[i][j]
                                && !repository.isOverUp
                                && !repository.isOverDown
                                && !repository.isOverLeft
                                && !repository.isOverRight
                            ) {
                                view[i * 4 + j].setBackgroundResource(R.drawable.b_2)
                                view[i * 4 + j].startAnimation(scaleUpAnimation)
                            } else {
                                view[i * 4 + j].setBackgroundResource(R.drawable.b_2)
                            }
                        }

                        "4" -> {
                            view[i * 4 + j].setBackgroundResource(R.drawable.b_4)
                            if (!repository.isOverUp && !repository.isOverDown && !repository.isOverLeft && !repository.isOverRight && repository.matrix[i][j] != repository.prevMatrix[i][j]) {
                                view[i * 4 + j].startAnimation(scaleUpAnimation)
                            }
                        }

                        "8" -> {
                            view[i * 4 + j].setBackgroundResource(R.drawable.b_8)
                            if (!repository.isOverUp && !repository.isOverDown && !repository.isOverLeft && !repository.isOverRight && repository.matrix[i][j] != repository.prevMatrix[i][j]) {
                                view[i * 4 + j].startAnimation(scaleUpAnimation)
                            }
                        }

                        "16" -> {
                            view[i * 4 + j].setBackgroundResource(R.drawable.b_16)
                            if (!repository.isOverUp && !repository.isOverDown && !repository.isOverLeft && !repository.isOverRight && repository.matrix[i][j] != repository.prevMatrix[i][j]) {
                                view[i * 4 + j].startAnimation(scaleUpAnimation)
                            }
                        }

                        "32" -> {
                            view[i * 4 + j].setBackgroundResource(R.drawable.b_32)
                            if (!repository.isOverUp && !repository.isOverDown && !repository.isOverLeft && !repository.isOverRight && repository.matrix[i][j] != repository.prevMatrix[i][j]) {
                                view[i * 4 + j].startAnimation(scaleUpAnimation)
                            }
                        }

                        "64" -> {
                            view[i * 4 + j].setBackgroundResource(R.drawable.b_64)
                            if (!repository.isOverUp && !repository.isOverDown && !repository.isOverLeft && !repository.isOverRight && repository.matrix[i][j] != repository.prevMatrix[i][j]) {
                                view[i * 4 + j].startAnimation(scaleUpAnimation)
                            }
                        }

                        "128" -> {
                            view[i * 4 + j].setBackgroundResource(R.drawable.b_128)
                            if (!repository.isOverUp && !repository.isOverDown && !repository.isOverLeft && !repository.isOverRight && repository.matrix[i][j] != repository.prevMatrix[i][j]) {
                                view[i * 4 + j].startAnimation(scaleUpAnimation)
                            }
                        }

                        "256" -> {
                            view[i * 4 + j].setBackgroundResource(R.drawable.b_256)
                            if (!repository.isOverUp && !repository.isOverDown && !repository.isOverLeft && !repository.isOverRight && repository.matrix[i][j] != repository.prevMatrix[i][j]) {
                                view[i * 4 + j].startAnimation(scaleUpAnimation)
                            }
                        }

                        "512" -> {
                            view[i * 4 + j].setBackgroundResource(R.drawable.b_512)
                            if (!repository.isOverUp && !repository.isOverDown && !repository.isOverLeft && !repository.isOverRight && repository.matrix[i][j] != repository.prevMatrix[i][j]) {
                                view[i * 4 + j].startAnimation(scaleUpAnimation)
                            }
                        }

                        "1024" -> {
                            view[i * 4 + j].setBackgroundResource(R.drawable.b_1024)
                            if (!repository.isOverUp && !repository.isOverDown && !repository.isOverLeft && !repository.isOverRight && repository.matrix[i][j] != repository.prevMatrix[i][j]) {
                                view[i * 4 + j].startAnimation(scaleUpAnimation)
                            }
                        }

                        "2048" -> {
                            view[i * 4 + j].setBackgroundResource(R.drawable.b_2048)
                            if (!repository.isOverUp && !repository.isOverDown && !repository.isOverLeft && !repository.isOverRight && repository.matrix[i][j] != repository.prevMatrix[i][j]) {
                                view[i * 4 + j].startAnimation(scaleUpAnimation)
                            }
                        }

                        else -> {
                            view[i * 4 + j].setBackgroundResource(R.drawable.b_4096)
                            if (!repository.isOverUp && !repository.isOverDown && !repository.isOverLeft && !repository.isOverRight && repository.matrix[i][j] != repository.prevMatrix[i][j]) {
                                view[i * 4 + j].startAnimation(scaleUpAnimation)
                            }
                        }
                    }
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setActions() {
        binding.clMain.setOnTouchListener(
            GameTouchListener(context = requireContext(), detectSideListener = {
                if (isOver) {
                    when (it) {
                        SideEnum.UP -> {
                            repository.moveToUp()
                            if (!repository.isOverUp) {
                                binding.ivBack.visibility = View.VISIBLE
                                binding.tvBack.visibility = View.VISIBLE
                            }
                        }

                        SideEnum.DOWN -> {
                            repository.moveToDown()
                            if (!repository.isOverDown) {
                                binding.ivBack.visibility = View.VISIBLE
                                binding.tvBack.visibility = View.VISIBLE
                            }
                        }

                        SideEnum.LEFT -> {
                            repository.moveToLeft()
                            if (!repository.isOverLeft) {
                                binding.ivBack.visibility = View.VISIBLE
                                binding.tvBack.visibility = View.VISIBLE
                            }
                        }

                        SideEnum.RIGHT -> {
                            repository.moveToRight()
                            if (!repository.isOverRight) {
                                binding.ivBack.visibility = View.VISIBLE
                                binding.tvBack.visibility = View.VISIBLE
                            }
                        }
                    }
                }
                //setBackground()
                loadData()
                setCurrScore()
                setMaxScore()
                if (repository.isOver()) {
                    if (isOver) {
                        val binding = DialogGameOverBinding.inflate(LayoutInflater.from(requireContext()))
                        val dialog = createDialog(binding = binding, R.style.TransparentDialog, true)
                        isOver = false
                        dialog.setCancelable(false)
                        binding.acbHome.setOnClickListener {
                            //setBackground()
                            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.CURR_SCORE.value, 0).apply()
                            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.PREV_SCORE.value, 0).apply()
                            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.STEPS.value, 0).apply()
                            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE1.value, -1).apply()
                            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE2.value, -1).apply()
                            repository.clearPrevMatrix()
                            repository.clearCurrMatrix()
                            repository.setCurrScore()
                            repository.setMaxScore()
                            repository.setSteps()
                            repository.setPrevScore()
                            moveTo(HomeScreen())
                            isWin = false
                            dialog.dismiss()
                        }
                        binding.acbRestart.setOnClickListener {
                            //setBackground()
                            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.CURR_SCORE.value, 0).apply()
                            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.PREV_SCORE.value, 0).apply()
                            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.STEPS.value, 0).apply()
                            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE1.value, -1).apply()
                            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE2.value, -1).apply()
                            repository.clearPrevMatrix()
                            repository.clearCurrMatrix()
                            loadData()
                            setCurrScore()
                            setMaxScore()
                            repository.setCurrScore()
                            repository.setMaxScore()
                            repository.setSteps()
                            repository.setPrevScore()
                            this.binding.ivBack.visibility = View.VISIBLE
                            this.binding.tvBack.visibility = View.VISIBLE
                            isOver = true
                            isWin = false
                            dialog.dismiss()
                        }
                    }
                } else if (!isWin && repository.is2048()) {
                    val binding = DialogIs2048Binding.inflate(LayoutInflater.from(requireContext()))
                    val dialog = createDialog(binding, R.style.TransparentDialog, true)
                    dialog.setCancelable(false)
                    binding.acbRestart2048.setOnClickListener {
                        //setBackground()
                        repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.CURR_SCORE.value, 0).apply()
                        repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.PREV_SCORE.value, 0).apply()
                        repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.STEPS.value, 0).apply()
                        repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE1.value, -1).apply()
                        repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE2.value, -1).apply()
                        repository.clearPrevMatrix()
                        repository.clearCurrMatrix()
                        loadData()
                        setCurrScore()
                        setMaxScore()
                        repository.setCurrScore()
                        repository.setMaxScore()
                        repository.setSteps()
                        repository.setPrevScore()
                        this.binding.ivBack.visibility = View.VISIBLE
                        this.binding.tvBack.visibility = View.VISIBLE
                        isWin = false
                        dialog.dismiss()
                    }
                    binding.acbContinue2048.setOnClickListener {
                        //setBackground()
                        isWin = true
                        dialog.dismiss()
                    }
                }
            })
        )

        binding.ivRestart.setOnClickListener {
            //setBackground()
            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.CURR_SCORE.value, 0).apply()
            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.PREV_SCORE.value, 0).apply()
            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.STEPS.value, 0).apply()
            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE1.value, -1).apply()
            repository.getSharedPreferences1()!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE2.value, -1).apply()
            repository.clearPrevMatrix()
            repository.clearCurrMatrix()
            loadData()
            setCurrScore()
            setMaxScore()
            repository.setCurrScore()
            repository.setMaxScore()
            repository.setSteps()
            repository.setPrevScore()
            binding.ivBack.visibility = View.VISIBLE
            binding.tvBack.visibility = View.VISIBLE
            isWin = false
        }
        binding.ivBack.setOnClickListener {
            //setBackground()
            if (repository.getSharedPreferences1()!!.getInt(PrefEnum.STEPS.value, 0) != 0) {
                repository.setPrevMatrixToCurrMatrix()
                repository.getSharedPreferences1()!!.edit().putInt(
                    PrefEnum.STEPS.value, repository.getSharedPreferences1()!!.getInt(PrefEnum.STEPS.value, 0) - 1
                ).apply()
                repository.getSharedPreferences1()!!.edit()
                    .putInt(PrefEnum.CURR_SCORE.value, repository.getSharedPreferences1()!!.getInt(PrefEnum.PREV_SCORE.value, 0)).apply()
                repository.setPrevScore()
                repository.setMaxScore()
                repository.setCurrScore()
                repository.setSteps()
                loadData()
                setCurrScore()
                setMaxScore()
                binding.ivBack.visibility = View.INVISIBLE
                binding.tvBack.visibility = View.INVISIBLE
            }
        }
        binding.ivHome.setOnClickListener {
            //setBackground()
            moveTo(HomeScreen())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setCurrScore() {
        binding.currScore.text = repository.getSharedPreferences1()!!.getInt(PrefEnum.CURR_SCORE.value, 0).toString()
    }

    @SuppressLint("SetTextI18n")
    private fun setMaxScore() {
        binding.maxScore.text = repository.getSharedPreferences1()!!.getInt(PrefEnum.MAX_SCORE.value, 0).toString()
    }

    /*private fun setBackground() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val backgroundResource = when (hour) {
            in 4..6 -> R.drawable.morning
            in 7..17 -> R.drawable.day
            in 18..20 -> R.drawable.evening
            in 21..22 -> R.drawable.night
            else -> R.drawable.mid_night
        }
        binding.gameMain.setImageResource(backgroundResource)
        binding.gameMain.scaleType = ImageView.ScaleType.CENTER_CROP
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}