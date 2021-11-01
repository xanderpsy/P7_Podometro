package com.example.p7_podometro


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
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
        val gyre: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
        
       // Log.d("Sensorx",sensorPasos.toString())
     //   Log.d("Sensorx","Estos es el giroscopio:${gyre.toString()}")

        var pasos: Float = 0.0F
        val sensorEventListener = object : SensorEventListener {

            override fun onSensorChanged(sensorEvent: SensorEvent) {
                pasos += sensorEvent.values[0]
                binding.tv.text = "pasos: $pasos"
               // Log.d("sensor","Pasos: $pasos")
            }
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            }
        }

        sensorManager.registerListener(sensorEventListener, sensorPasos, SensorManager.SENSOR_DELAY_FASTEST)



        var gyr: Float = 0.0F
        val sensorEventListener2 = object : SensorEventListener {

            override fun onSensorChanged(sensorEvent: SensorEvent) {
                sensorEvent.values

                if(sensorEvent.values[0]<0.5f) {
                    Log.d("Sensorx", "giro2: ${sensorEvent.values[0]}")
                    binding.cl.setBackgroundColor(Color.CYAN)
                }
                if(sensorEvent.values[0]>0.5f){
                    binding.cl.setBackgroundColor(Color.RED)
                }

            }
            override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            }
        }
        sensorManager.registerListener(sensorEventListener2, gyre, SensorManager.SENSOR_DELAY_NORMAL)
    }
}