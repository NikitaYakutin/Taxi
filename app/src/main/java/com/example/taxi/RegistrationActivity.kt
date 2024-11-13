package com.example.taxi

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class RegisterActivity : ComponentActivity() {

    private lateinit var edtName: EditText
    private lateinit var edtSurname: EditText
    private lateinit var edtPhone: EditText
    private lateinit var btnRegisterOrLogin: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        edtName = findViewById(R.id.edtName)
        edtSurname = findViewById(R.id.edtSurname)
        edtPhone = findViewById(R.id.edtPhone)
        btnRegisterOrLogin = findViewById(R.id.btnRegister)

        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)

        // Проверка и установка сохраненных данных в поля
        val savedName = sharedPreferences.getString("name", "")
        val savedSurname = sharedPreferences.getString("surname", "")
        val savedPhone = sharedPreferences.getString("phone", "")

        if (!savedName.isNullOrEmpty() && !savedSurname.isNullOrEmpty() && !savedPhone.isNullOrEmpty()) {
            edtName.setText(savedName)
            edtSurname.setText(savedSurname)
            edtPhone.setText(savedPhone)
            btnRegisterOrLogin.text = "Log In"
        } else {
            btnRegisterOrLogin.text = "Registration"
        }

        btnRegisterOrLogin.setOnClickListener {
            if (savedName.isNullOrEmpty() || savedSurname.isNullOrEmpty() || savedPhone.isNullOrEmpty()) {
                // Если поля пустые, сохраняем данные
                val name = edtName.text.toString()
                val surname = edtSurname.text.toString()
                val phone = edtPhone.text.toString()

                if (name.isNotEmpty() && surname.isNotEmpty() && phone.isNotEmpty()) {
                    val editor = sharedPreferences.edit()
                    editor.putString("name", name)
                    editor.putString("surname", surname)
                    editor.putString("phone", phone)
                    editor.apply()

                    navigateToProfile(name, surname, phone)
                } else {
                    Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Если данные уже есть, сразу переходим к профилю
                navigateToProfile(savedName, savedSurname, savedPhone)
            }
        }
    }

    private fun navigateToProfile(name: String, surname: String, phone: String) {
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("name", name)
        intent.putExtra("surname", surname)
        intent.putExtra("phone", phone)
        startActivity(intent)
    }
}
