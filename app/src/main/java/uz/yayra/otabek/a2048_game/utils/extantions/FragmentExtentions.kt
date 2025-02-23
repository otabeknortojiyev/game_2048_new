package uz.gita.otabek.a2048game.utils.extantions

import android.app.Dialog
import android.widget.LinearLayout
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import uz.gita.otabek.a2048game.R

fun Fragment.moveTo(fragment: Fragment) {
    parentFragmentManager.beginTransaction()
        .replace(R.id.main_container, fragment)
        .commit()
}

fun Fragment.createDialog(
    binding: ViewBinding,
    @StyleRes styleRes: Int,
    cancelable: Boolean = false
): Dialog {
    val dialog = Dialog(requireContext(), styleRes)
    dialog.setContentView(binding.root)
    dialog.show()
    dialog.window?.setLayout(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    dialog.setCancelable(cancelable)

    return dialog
}