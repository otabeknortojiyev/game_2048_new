package uz.gita.otabek.a2048game.domain.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import uz.gita.otabek.a2048game.utils.enums.PrefEnum
import uz.gita.otabek.a2048game.utils.enums.PrefEnum.CURR_SCORE
import uz.gita.otabek.a2048game.utils.enums.PrefEnum.MAX_SCORE
import uz.gita.otabek.a2048game.utils.enums.PrefEnum.PREV_SCORE
import uz.gita.otabek.a2048game.utils.enums.PrefEnum.STEPS
import kotlin.random.Random

class GameRepository {
    private var currScore: Int = 0
    private var prevScore: Int = 0
    private var maxScore: Int = 0
    private var steps: Int = 0
    private var sharedPreferences: SharedPreferences? = null
    var isOverLeft = false
    var isOverRight = false
    var isOverUp = false
    var isOverDown = false
    var matrix = arrayOf(
        arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0)
    )
    var prevMatrix = arrayOf(
        arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0), arrayOf(0, 0, 0, 0)
    )

    init {
        addTwoElements()
        addTwoElements()
    }

    companion object {
        private lateinit var instance: GameRepository

        fun getInstance(): GameRepository {
            if (!(::instance.isInitialized)) {
                instance = GameRepository()
            }
            return instance
        }

        private const val ELEMENT = 2
    }

    fun moveToLeft() {
        var isGood = false
        loop1@ for (i in 0..3) {
            for (j in 0..2) {
                if (matrix[i][j] == matrix[i][j + 1] && matrix[i][j] != 0) {
                    isGood = true
                    break@loop1
                }
                if ((j == 1 || j == 2) && (matrix[i][j] != 0) && (matrix[i][j - 1] == 0)) {
                    isGood = true
                    break@loop1
                }
                if (j == 2 && matrix[i][j + 1] != 0 && matrix[i][j] == 0) {
                    isGood = true
                    break@loop1
                }
            }
        }
        if (isGood) {
            setCurrMatrixToPrevMatrix()
            val ls = ArrayList<Int>(16)
            for (i in 0..3) {
                for (j in 0..3) {
                    ls.add(prevMatrix[i][j])
                }
            }
            saveArraylist(PrefEnum.PREV_MATRIX.value, ls)
            setCurrScoreToPrevScore()
            sharedPreferences!!.edit().putInt(PREV_SCORE.value, prevScore).apply()
            for (i in 0..3) {
                var ptr1 = 0
                var ptr2 = ptr1 + 1
                while (ptr1 != 3) {
                    if (matrix[i][ptr1] == 0) {
                        ptr1++
                        ptr2 = ptr1 + 1
                    } else if (matrix[i][ptr1] != 0) {
                        while (ptr2 != 4 && matrix[i][ptr2] == 0) {
                            ptr2++
                        }
                        if (ptr2 != 4 && matrix[i][ptr1] == matrix[i][ptr2]) {
                            matrix[i][ptr1] = matrix[i][ptr1] * 2
                            currScore += matrix[i][ptr1]
                            if (currScore > maxScore) {
                                maxScore = currScore
                            }
                            sharedPreferences!!.edit().putInt(CURR_SCORE.value, currScore).apply()
                            sharedPreferences!!.edit().putInt(MAX_SCORE.value, maxScore).apply()
                            setCurrScore()
                            setMaxScore()
                            matrix[i][ptr2] = 0
                            ptr1++
                            ptr2 = ptr1 + 1
                        } else if (ptr2 != 4 && matrix[i][ptr1] != matrix[i][ptr2]) {
                            ptr1++
                            ptr2 = ptr1 + 1
                        } else if (ptr2 == 4) {
                            ptr1++
                        }
                    }
                }
                var index = 0
                for (j in 0..3) {
                    if (matrix[i][j] != 0) {
                        val temp = matrix[i][j]
                        matrix[i][j] = 0
                        matrix[i][index++] = temp
                    }
                }
            }
            val x = addTwoElements()
            if (sharedPreferences!!.getInt(STEPS.value, 0) == 0 && sharedPreferences!!.getInt(PrefEnum.NEW_ELEMENT_COORDINATE1.value, -1) == -1) {
                sharedPreferences!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE1.value, x).apply()
            } else {
                sharedPreferences!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE2.value, x).apply()
            }
            val list = ArrayList<Int>(16)
            for (i in 0..3) {
                for (j in 0..3) {
                    list.add(matrix[i][j])
                }
            }
            saveArraylist(PrefEnum.MATRIX.value, list)
            steps++
            sharedPreferences!!.edit().putInt(STEPS.value, steps).apply()
        } else isOverLeft = true
    }

    fun moveToRight() {
        var isGood = false
        loop1@ for (i in 0..3) {
            for (j in 3 downTo 1) {
                if (matrix[i][j] == matrix[i][j - 1] && matrix[i][j] != 0) {
                    isGood = true
                    break@loop1
                }
                if ((j == 1 || j == 2) && (matrix[i][j] != 0) && (matrix[i][j + 1] == 0)) {
                    isGood = true
                    break@loop1
                }
                if (j == 1 && matrix[i][0] != 0 && matrix[i][j] == 0) {
                    isGood = true
                    break@loop1
                }
            }
        }
        if (isGood) {
            setCurrMatrixToPrevMatrix()
            val ls = ArrayList<Int>(16)
            for (i in 0..3) {
                for (j in 0..3) {
                    ls.add(prevMatrix[i][j])
                }
            }
            saveArraylist(PrefEnum.PREV_MATRIX.value, ls)
            setCurrScoreToPrevScore()
            sharedPreferences!!.edit().putInt(PREV_SCORE.value, prevScore).apply()
            for (i in 0..3) {
                var ptr1 = 3
                var ptr2 = ptr1 - 1
                while (ptr1 != 0) {
                    if (matrix[i][ptr1] == 0) {
                        ptr1--
                        ptr2 = ptr1 - 1
                    } else if (matrix[i][ptr1] != 0) {
                        while (ptr2 != -1 && matrix[i][ptr2] == 0) {
                            ptr2--
                        }
                        if (ptr2 != -1 && matrix[i][ptr1] == matrix[i][ptr2]) {
                            matrix[i][ptr1] = matrix[i][ptr1] * 2
                            currScore += matrix[i][ptr1]
                            if (currScore > maxScore) {
                                maxScore = currScore
                            }
                            sharedPreferences!!.edit().putInt(CURR_SCORE.value, currScore).apply()
                            sharedPreferences!!.edit().putInt(MAX_SCORE.value, maxScore).apply()
                            setCurrScore()
                            setMaxScore()
                            matrix[i][ptr2] = 0
                            ptr1--
                            ptr2 = ptr1 - 1
                        } else if (ptr2 != -1 && matrix[i][ptr1] != matrix[i][ptr2]) {
                            ptr1--
                            ptr2 = ptr1 - 1
                        } else if (ptr2 == -1) {
                            ptr1--
                        }
                    }
                }
                var index = 3
                for (j in 3 downTo 0) {
                    if (matrix[i][j] != 0) {
                        val temp = matrix[i][j]
                        matrix[i][j] = 0
                        matrix[i][index--] = temp
                    }
                }
            }
            val x = addTwoElements()
            if (sharedPreferences!!.getInt(STEPS.value, 0) == 0 && sharedPreferences!!.getInt(PrefEnum.NEW_ELEMENT_COORDINATE1.value, -1) == -1) {
                sharedPreferences!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE1.value, x).apply()
            } else {
                sharedPreferences!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE2.value, x).apply()
            }
            val list = ArrayList<Int>(16)
            for (i in 0..3) {
                for (j in 0..3) {
                    list.add(matrix[i][j])
                }
            }
            saveArraylist(PrefEnum.MATRIX.value, list)
            steps++
            sharedPreferences!!.edit().putInt(STEPS.value, steps).apply()
        } else isOverRight = true
    }

    fun moveToUp() {
        var isGood = false
        loop1@ for (i in 0..3) {
            for (j in 0..2) {
                if (matrix[j][i] == matrix[j + 1][i] && matrix[j][i] != 0) {
                    isGood = true
                    break@loop1
                }
                if ((j == 1 || j == 2) && (matrix[j][i] != 0) && (matrix[j - 1][i] == 0)) {
                    isGood = true
                    break@loop1
                }
                if (j == 2 && matrix[j + 1][i] != 0 && matrix[j][i] == 0) {
                    isGood = true
                    break@loop1
                }
            }
        }
        if (isGood) {
            setCurrMatrixToPrevMatrix()
            val ls = ArrayList<Int>(16)
            for (i in 0..3) {
                for (j in 0..3) {
                    ls.add(prevMatrix[i][j])
                }
            }
            saveArraylist(PrefEnum.PREV_MATRIX.value, ls)
            setCurrScoreToPrevScore()
            sharedPreferences!!.edit().putInt(PREV_SCORE.value, prevScore).apply()
            for (i in 0..3) {
                var ptr1 = 0
                var ptr2 = ptr1 + 1
                while (ptr1 != 3) {
                    if (matrix[ptr1][i] == 0) {
                        ptr1++
                        ptr2 = ptr1 + 1
                    } else if (matrix[ptr1][i] != 0) {
                        while (ptr2 != 4 && matrix[ptr2][i] == 0) {
                            ptr2++
                        }
                        if (ptr2 != 4 && matrix[ptr1][i] == matrix[ptr2][i]) {
                            matrix[ptr1][i] = matrix[ptr1][i] * 2
                            currScore += matrix[ptr1][i]
                            if (currScore > maxScore) {
                                maxScore = currScore
                            }
                            sharedPreferences!!.edit().putInt(CURR_SCORE.value, currScore).apply()
                            sharedPreferences!!.edit().putInt(MAX_SCORE.value, maxScore).apply()
                            setCurrScore()
                            setMaxScore()
                            matrix[ptr2][i] = 0
                            ptr1++
                            ptr2 = ptr1 + 1
                        } else if (ptr2 != 4 && matrix[ptr1][i] != matrix[ptr2][i]) {
                            ptr1++
                            ptr2 = ptr1 + 1
                        } else if (ptr2 == 4) {
                            ptr1++
                        }
                    }
                }
                var index = 0
                for (j in 0..3) {
                    if (matrix[j][i] != 0) {
                        val temp = matrix[j][i]
                        matrix[j][i] = 0
                        matrix[index++][i] = temp
                    }
                }
            }
            val x = addTwoElements()
            if (sharedPreferences!!.getInt(STEPS.value, 0) == 0 && sharedPreferences!!.getInt(PrefEnum.NEW_ELEMENT_COORDINATE1.value, -1) == -1) {
                sharedPreferences!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE1.value, x).apply()
            } else {
                sharedPreferences!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE2.value, x).apply()
            }
            val list = ArrayList<Int>(16)
            for (i in 0..3) {
                for (j in 0..3) {
                    list.add(matrix[i][j])
                }
            }
            saveArraylist(PrefEnum.MATRIX.value, list)
            steps++
            sharedPreferences!!.edit().putInt(STEPS.value, steps).apply()
        } else isOverUp = true
    }

    fun moveToDown() {
        var isGood = false
        loop1@ for (i in 0..3) {
            for (j in 3 downTo 1) {
                if (matrix[j][i] == matrix[j - 1][i] && matrix[j - 1][i] != 0) {
                    isGood = true
                    break@loop1
                }
                if ((j == 1 || j == 2) && (matrix[j][i] != 0) && (matrix[j + 1][i] == 0)) {
                    isGood = true
                    break@loop1
                }
                if (j == 1 && matrix[0][i] != 0 && matrix[j][i] == 0) {
                    isGood = true
                    break@loop1
                }
            }
        }
        if (isGood) {
            setCurrMatrixToPrevMatrix()
            val ls = ArrayList<Int>(16)
            for (i in 0..3) {
                for (j in 0..3) {
                    ls.add(prevMatrix[i][j])
                }
            }
            saveArraylist(PrefEnum.PREV_MATRIX.value, ls)
            setCurrScoreToPrevScore()
            sharedPreferences!!.edit().putInt(PREV_SCORE.value, prevScore).apply()
            for (i in 0..3) {
                var ptr1 = 3
                var ptr2 = ptr1 - 1
                while (ptr1 != 0) {
                    if (matrix[ptr1][i] == 0) {
                        ptr1--
                        ptr2 = ptr1 - 1
                    } else if (matrix[ptr1][i] != 0) {
                        while (ptr2 != -1 && matrix[ptr2][i] == 0) {
                            ptr2--
                        }
                        if (ptr2 != -1 && matrix[ptr1][i] == matrix[ptr2][i]) {
                            matrix[ptr1][i] = matrix[ptr1][i] * 2
                            currScore += matrix[ptr1][i]
                            if (currScore > maxScore) {
                                maxScore = currScore
                            }
                            sharedPreferences!!.edit().putInt(CURR_SCORE.value, currScore).apply()
                            sharedPreferences!!.edit().putInt(MAX_SCORE.value, maxScore).apply()
                            setCurrScore()
                            setMaxScore()
                            matrix[ptr2][i] = 0
                            ptr1--
                            ptr2 = ptr1 - 1
                        } else if (ptr2 != -1 && matrix[ptr1][i] != matrix[ptr2][i]) {
                            ptr1--
                            ptr2 = ptr1 - 1
                        } else if (ptr2 == -1) {
                            ptr1--
                        }
                    }
                }
                var index = 3
                for (j in 3 downTo 0) {
                    if (matrix[j][i] != 0) {
                        val temp = matrix[j][i]
                        matrix[j][i] = 0
                        matrix[index--][i] = temp
                    }
                }
            }
            val x = addTwoElements()
            if (sharedPreferences!!.getInt(STEPS.value, 0) == 0 && sharedPreferences!!.getInt(PrefEnum.NEW_ELEMENT_COORDINATE1.value, -1) == -1) {
                sharedPreferences!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE1.value, x).apply()
            } else {
                sharedPreferences!!.edit().putInt(PrefEnum.NEW_ELEMENT_COORDINATE2.value, x).apply()
            }
            val list = ArrayList<Int>(16)
            for (i in 0..3) {
                for (j in 0..3) {
                    list.add(matrix[i][j])
                }
            }
            saveArraylist(PrefEnum.MATRIX.value, list)
            steps++
            sharedPreferences!!.edit().putInt(STEPS.value, steps).apply()
        } else isOverDown = true
    }

    fun is2048(): Boolean {
        for (i in 0..3) {
            for (j in 0..3) {
                if (matrix[i][j] == 2048) {
                    return true
                }
            }
        }
        return false
    }

    fun isOver(): Boolean {
        var isGood = false
        loop1@ for (i in 0..3) {
            for (j in 0..2) {
                if (matrix[i][j] == matrix[i][j + 1] && matrix[i][j] != 0) {
                    isGood = true
                    break@loop1
                }
                if ((j == 1 || j == 2) && (matrix[i][j] != 0) && (matrix[i][j - 1] == 0)) {
                    isGood = true
                    break@loop1
                }
                if (j == 2 && matrix[i][j + 1] != 0 && matrix[i][j] == 0) {
                    isGood = true
                    break@loop1
                }
            }
        }
        if (!isGood) isOverLeft = true

        isGood = false
        loop1@ for (i in 0..3) {
            for (j in 3 downTo 1) {
                if (matrix[i][j] == matrix[i][j - 1] && matrix[i][j] != 0) {
                    isGood = true
                    break@loop1
                }
                if ((j == 1 || j == 2) && (matrix[i][j] != 0) && (matrix[i][j + 1] == 0)) {
                    isGood = true
                    break@loop1
                }
                if (j == 1 && matrix[i][0] != 0 && matrix[i][j] == 0) {
                    isGood = true
                    break@loop1
                }
            }
        }
        if (!isGood) isOverRight = true

        isGood = false
        loop1@ for (i in 0..3) {
            for (j in 0..2) {
                if (matrix[j][i] == matrix[j + 1][i] && matrix[j][i] != 0) {
                    isGood = true
                    break@loop1
                }
                if ((j == 1 || j == 2) && (matrix[j][i] != 0) && (matrix[j - 1][i] == 0)) {
                    isGood = true
                    break@loop1
                }
                if (j == 2 && matrix[j + 1][i] != 0 && matrix[j][i] == 0) {
                    isGood = true
                    break@loop1
                }
            }
        }
        if (!isGood) isOverUp = true

        isGood = false
        loop1@ for (i in 0..3) {
            for (j in 3 downTo 1) {
                if (matrix[j][i] == matrix[j - 1][i] && matrix[j - 1][i] != 0) {
                    isGood = true
                    break@loop1
                }
                if ((j == 1 || j == 2) && (matrix[j][i] != 0) && (matrix[j + 1][i] == 0)) {
                    isGood = true
                    break@loop1
                }
                if (j == 1 && matrix[0][i] != 0 && matrix[j][i] == 0) {
                    isGood = true
                    break@loop1
                }
            }
        }
        if (!isGood) isOverDown = true
        if (isOverLeft && isOverRight && isOverUp && isOverDown && isFull()) {
            return true
        } else {
            isOverRight = false
            isOverUp = false
            isOverDown = false
            isOverLeft = false
            return false
        }
    }

    private fun isFull(): Boolean {
        for (i in 0..3) {
            for (j in 0..3) {
                if (matrix[i][j] == 0) {
                    return false
                }
            }
        }
        return true
    }

    fun setSharedPreferences1(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
        currScore = sharedPreferences.getInt(CURR_SCORE.value, 0)
        prevScore = sharedPreferences.getInt(PREV_SCORE.value, 0)
        maxScore = sharedPreferences.getInt(MAX_SCORE.value, 0)
        steps = sharedPreferences.getInt(STEPS.value, 0)
        var list = getArrayList(PrefEnum.MATRIX.value)
        if (list.isNotEmpty()) {
            for (i in 0..3) {
                for (j in 0..3) {
                    matrix[i][j] = list[i * 4 + j]
                }
            }
        }
        list.clear()
        list = getArrayList(PrefEnum.PREV_MATRIX.value)
        if (list.isNotEmpty()) {
            for (i in 0..3) {
                for (j in 0..3) {
                    prevMatrix[i][j] = list[i * 4 + j]
                }
            }
        }
    }

    fun getSharedPreferences1(): SharedPreferences? {
        return sharedPreferences
    }

    private fun saveArraylist(key: String, list: ArrayList<Int>) {
        val temp = Gson().toJson(list)
        sharedPreferences!!.edit().putString(key, temp).apply()
    }

    private fun getArrayList(key: String): ArrayList<Int> {
        val temp = sharedPreferences!!.getString(key, "")
        val type = object : TypeToken<ArrayList<Int>>() {}.type
        return Gson().fromJson(temp, type) ?: ArrayList()
    }

    private fun addTwoElements(): Int {
        val list = ArrayList<Pair<Int, Int>>()
        for (i in 0..3) {
            for (j in 0..3) {
                if (matrix[i][j] == 0) {
                    list.add(Pair(i, j))
                }
            }
        }
        if (list.isEmpty()) {
            return -1
        }
        val randomIndex = Random.nextInt(0, list.size)
        val pair = list[randomIndex]
        matrix[pair.first][pair.second] = ELEMENT
        return pair.first * 10 + pair.second
    }

    fun clearPrevMatrix() {
        for (i in 0..3) {
            for (j in 0..3) {
                prevMatrix[i][j] = 0
            }
        }
    }

    fun clearCurrMatrix() {
        for (i in 0..3) {
            for (j in 0..3) {
                matrix[i][j] = 0
            }
        }
        addTwoElements()
        addTwoElements()
    }

    fun setCurrScore() {
        currScore = sharedPreferences!!.getInt(CURR_SCORE.value, 0)
    }

    fun setMaxScore() {
        maxScore = sharedPreferences!!.getInt(MAX_SCORE.value, 0)
    }

    fun setSteps() {
        steps = sharedPreferences!!.getInt(STEPS.value, 0)
    }

    fun setPrevScore() {
        prevScore = sharedPreferences!!.getInt(PREV_SCORE.value, 0)
    }

    private fun setCurrMatrixToPrevMatrix() {
        for (i in 0..3) {
            for (j in 0..3) {
                prevMatrix[i][j] = matrix[i][j]
            }
        }
    }

    fun setPrevMatrixToCurrMatrix() {
        for (i in 0..3) {
            for (j in 0..3) {
                matrix[i][j] = prevMatrix[i][j]
            }
        }
    }

    private fun setCurrScoreToPrevScore() {
        prevScore = currScore
    }
}