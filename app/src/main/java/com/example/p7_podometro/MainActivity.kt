package com.example.p7_podometro


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.p7_podometro.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED){

            requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),1)
        }
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensorPasos: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        Log.d("Sensor",sensorPasos.toString())

      var pasos: Float = 0.0F
        val sensorEventListener = object : SensorEventListener {


            override fun onSensorChanged(sensorEvent: SensorEvent) {
                pasos += sensorEvent.values[0]
                binding.tv.text = "pasos: $pasos"
                Log.d("sensor","Pasos: $pasos")
            }
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

            }



        }

        sensorManager.registerListener(sensorEventListener, sensorPasos, SensorManager.SENSOR_DELAY_FASTEST)

    }
}