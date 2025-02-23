package uz.gita.otabek.a2048game.presentation.game.touchListener

import android.annotation.SuppressLint
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import uz.gita.otabek.a2048game.utils.enums.SideEnum
import kotlin.math.abs

class GameTouchListener(context: Context, private val detectSideListener: ((SideEnum) -> Unit)) :
    View.OnTouchListener {
    private val gestureDetector = GestureDetector(context, MyGestureDetector())

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        gestureDetector.onTouchEvent(event)
        return true
    }

    private inner class MyGestureDetector : GestureDetector.SimpleOnGestureListener() {
        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (abs(e1!!.x - e2.x) >= abs(e1.y - e2.y)) {
                if (e2.x > e1.x) {
                    detectSideListener.invoke(SideEnum.RIGHT)
                } else {
                    detectSideListener.invoke(SideEnum.LEFT)
                }
            } else {
                if (e2.y >= e1.y) {
                    detectSideListener.invoke(SideEnum.DOWN)
                } else {
                    detectSideListener.invoke(SideEnum.UP)
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY)
        }
    }
}
