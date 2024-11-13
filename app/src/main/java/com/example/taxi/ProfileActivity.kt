package com.example.taxi

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import android.location.Location
import android.location.LocationManager
import android.util.Log

class ProfileActivity : ComponentActivity() {

    private lateinit var tvName: TextView
    private lateinit var tvSurname: TextView
    private lateinit var tvPhone: TextView
    private lateinit var tvRoute: TextView
    private lateinit var btnSetPath: Button
    private lateinit var btnCallTaxi: Button



    private val requestLocationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                getLocation()
            } else {
                Toast.makeText(this, "Нет разрешений на доступ к геолокации", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvName = findViewById(R.id.tvName)
        tvSurname = findViewById(R.id.tvSurname)
        tvPhone = findViewById(R.id.tvPhone)
        tvRoute = findViewById(R.id.tvRoute)
        btnSetPath = findViewById(R.id.btnSetPath)
        btnCallTaxi = findViewById(R.id.btnCallTaxi)


        // Получаем данные из SharedPreferences, если они сохранены
        val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val name = sharedPreferences.getString("name", "Нет данных")
        val surname = sharedPreferences.getString("surname", "Нет данных")
        val phone = sharedPreferences.getString("phone", "Нет данных")
        val route = sharedPreferences.getString("route", "Нет маршрута")

        tvName.text = name
        tvSurname.text = surname
        tvPhone.text = phone
        tvRoute.text = route



btnCallTaxi.setOnClickListener {
    Toast.makeText(this, "Ожидайте Такси! Удачи!", Toast.LENGTH_SHORT).show()
}
        btnSetPath.setOnClickListener {
            val intent = Intent(this, RouteActivity::class.java)
            startActivityForResult(intent, 100)
        }

        btnCallTaxi.isEnabled = false

        // Запрос разрешения на геолокацию
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED) {
            // Разрешение уже предоставлено
            getLocation()
        } else {
            // Запрос разрешения
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun getLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        try {
            val location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                // Можете использовать latitude и longitude для отображения или других действий
                Log.d("GeoLocation", "Latitude: $latitude, Longitude: $longitude")
            }
        } catch (e: SecurityException) {
            Toast.makeText(this, "Ошибка при получении данных местоположения", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val route = data?.getStringExtra("route")
            // Отображаем маршрут и активируем кнопку
            tvRoute.text = route

            btnCallTaxi.isEnabled = route != null && route.isNotEmpty()

            // Сохраняем маршрут в SharedPreferences
            val sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("route", route)
            editor.apply()
        }
    }
}
