package com.example.mvvm_basics.ui.quotes

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity

open class BaseActivity : FragmentActivity() {

    fun isKeyboardOnScreen(activityRootView: View): Boolean{
        val r = Rect()
        activityRootView.getWindowVisibleDisplayFrame(r)
        val heightDiff = activityRootView.rootView.height - (r.bottom - r.top)
        return heightDiff > activityRootView.rootView.height / 4
    }

    fun hideSoftKeyboard(view: View) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    fun clearFocus(vararg editText: EditText) {
        for (item in editText){
            item.clearFocus()
        }
    }
}