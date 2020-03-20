package com.example.introduccion

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_kotlin_main.*

class KotlinMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin_main)

        onTextChange(etGrados,currentFocus)
        onRadioButtonSelected(rgGrados,currentFocus)
        onCheckboxChange(chkMostrarImagen,currentFocus)
        chkMostrarImagen.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked)
                imgGrados.visibility = View.VISIBLE
            else
                imgGrados.visibility = View.GONE
        }
    }

    fun cambiarImagen(resultado: Double) {
        if (resultado in 10.0..25.0)
            imgGrados.setImageResource(R.drawable.templado);
        if (resultado < 10)
            imgGrados.setImageResource(R.drawable.frio);
        if (resultado > 25)
            imgGrados.setImageResource(R.drawable.caliente);
    }

    fun convertir(view: View?): String {
        var grados = 0.0;
        var resultado = 0.0;

        grados = if(etGrados.text.isEmpty())
            0.0
        else
            etGrados.text.toString().toDouble()

        resultado = if(rgGrados.checkedRadioButtonId == R.id.rbCentigradosFahrenheit)
            (grados * (9.0 / 5.0)) + 32
        else
            (grados - 32) * (5.0 / 9.0)

        if (resultado.isNaN() || resultado.isInfinite())
            tvResultado.text="0.0"
        else
            tvResultado.text = resultado.toString();

        cambiarImagen(resultado);
        return resultado.toString()
    }

    fun cambiarActivity(view : View?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        this.finish()
    }

    fun sendMessage(view : View){
        // Create the text message with a string
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, convertir(view))
            type = "text/plain"
        }

        // Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(packageManager) != null) {
            startActivity(sendIntent)
        }
    }

    fun onTextChange(editText: EditText, view: View?) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convertir(view)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    fun onCheckboxChange(checkbox: CheckBox, view :View?){
        checkbox.setOnClickListener {
            if (checkbox.isChecked) {
                imgGrados.visibility=View.VISIBLE
            } else {
                imgGrados.visibility=View.GONE
            }
        }
    }

    fun onRadioButtonSelected(radioGroup: RadioGroup, view:View?){
        convertir(view)
        radioGroup.setOnCheckedChangeListener{ _, _ ->
            convertir(view)
        }
    }
}
