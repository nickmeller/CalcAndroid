package ru.nickmeller.calc

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.mozilla.javascript.Context
import org.mozilla.javascript.EvaluatorException
import org.mozilla.javascript.ImporterTopLevel

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "MainActivity"
        const val CALC_FIELD_KEY = "CALC_FIELD_KEY"
    }

    private var calcField = ""
    private lateinit var textButtons: Array<Button>

    private fun updateCalcField() {
        calcFieldLabel.text = calcField
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(CALC_FIELD_KEY, calcField)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        calcField = savedInstanceState.getString(CALC_FIELD_KEY)?: ""
        updateCalcField()
        super.onRestoreInstanceState(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textButtons = arrayOf(buttonZero, buttonOne, buttonTwo, buttonThree, buttonFour, buttonFive, buttonSix, buttonSeven, buttonEight, buttonNine,
            buttonPlus, buttonMinus, buttonMul, buttonDiv, buttonDot)
        for (button in textButtons) {
            button.setOnClickListener {
                calcField += button.text
                updateCalcField()
            }
        }

        buttonClear.setOnClickListener {
            calcField = ""
            updateCalcField()
        }

        buttonEval.setOnClickListener {


            val context = Context.enter()
            context.optimizationLevel = -1
            val scope = ImporterTopLevel(context)
            try {
                calcField = context.evaluateString(scope, calcField, "test", 1, null).toString()
                if (calcField.equals("Infinity") || calcField.equals("NaN") ) {
                    Toast.makeText(this, calcField, Toast.LENGTH_SHORT).show()
                    calcField = ""
                } else if ( calcField.contains("Undefined") || calcField.contains("Error")) {
                    Toast.makeText(this, R.string.undefinedError, Toast.LENGTH_SHORT).show()
                    calcField = ""
                }
            } catch (e: EvaluatorException) {
                Toast.makeText(this, R.string.unexpectedInputError, Toast.LENGTH_SHORT).show()
                calcField = ""
            }
            updateCalcField()
        }
    }
}