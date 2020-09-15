package ru.nickmeller.calc

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val CALC_FIELD_KEY = "CALC_FIELD_KEY"
    }
    var calcField = "Empty"
    val textButtons = arrayOf(buttonZero, buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix, buttonSeven, buttonEight, buttonNine,
        buttonPlus, buttonMinus, buttonMul, buttonDiv)

    private fun updateCalcField() {
        calcFieldLabel.text = calcField
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(CALC_FIELD_KEY, calcField)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        calcField = savedInstanceState.getString(CALC_FIELD_KEY)!!
        updateCalcField()
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (button in textButtons) {
            button.setOnClickListener {
                calcField += button.text
                updateCalcField()
            }
        }
        buttonClear.setOnClickListener {
            calcField = ""
        }
    }
}