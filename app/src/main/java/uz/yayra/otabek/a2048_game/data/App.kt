package uz.gita.otabek.a2048game.data

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import uz.gita.otabek.a2048game.domain.repository.GameRepository
import uz.gita.otabek.a2048game.utils.enums.PrefEnum.GAME_NAME

class App : Application() {
    private var sharedPreferences: SharedPreferences? = null

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this

        sharedPreferences = getSharedPreferences(GAME_NAME.value, MODE_PRIVATE)

        GameRepository.getInstance().setSharedPreferences1(sharedPreferences!!)
    }
}