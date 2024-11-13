package com.example.taxi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity

class RouteActivity : ComponentActivity() {

    private lateinit var edtStreetFrom: EditText
    private lateinit var edtHouseFrom: EditText
    private lateinit var edtFloorFrom: EditText
    private lateinit var edtStreetTo: EditText
    private lateinit var edtHouseTo: EditText
    private lateinit var edtFloorTo: EditText
    private lateinit var btnOk: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        edtStreetFrom = findViewById(R.id.edtStreetFrom)
        edtHouseFrom = findViewById(R.id.edtHouseFrom)
        edtFloorFrom = findViewById(R.id.edtFloorFrom)
        edtStreetTo = findViewById(R.id.edtStreetTo)
        edtHouseTo = findViewById(R.id.edtHouseTo)
        edtFloorTo = findViewById(R.id.edtFloorTo)
        btnOk = findViewById(R.id.btnOk)

        btnOk.setOnClickListener {
            val streetFrom = edtStreetFrom.text.toString().trim()
            val houseFrom = edtHouseFrom.text.toString().trim()
            val floorFrom = edtFloorFrom.text.toString().trim()
            val streetTo = edtStreetTo.text.toString().trim()
            val houseTo = edtHouseTo.text.toString().trim()
            val floorTo = edtFloorTo.text.toString().trim()

            if (streetFrom.isEmpty() || houseFrom.isEmpty() || floorFrom.isEmpty() ||
                streetTo.isEmpty() || houseTo.isEmpty() || floorTo.isEmpty()) {
                Toast.makeText(this, "Заполните все поля!", Toast.LENGTH_SHORT).show()
            } else {
                // Формируем маршрут и отправляем данные обратно в ProfileActivity
                val from = "$streetFrom $houseFrom, Этаж: $floorFrom"
                val to = "$streetTo $houseTo, Этаж: $floorTo"

                val resultIntent = Intent()
                resultIntent.putExtra("route", "Откуда: $from, Куда: $to")
                setResult(RESULT_OK, resultIntent)
                finish()
            }
        }
    }
}
