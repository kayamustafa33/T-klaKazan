package com.mustafa.tklakazan.bottom_fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mustafa.tklakazan.R
import com.mustafa.tklakazan.databinding.FragmentStepBinding
import java.util.*
import java.util.concurrent.TimeUnit

class StepFragment : Fragment(),SensorEventListener {

    private lateinit var binding : FragmentStepBinding
    private  var sensorManager: SensorManager? = null
    private  var stepSensor: Sensor? = null
    private var stepCount = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentStepBinding.inflate(inflater,container,false)

        //Initialize add
        //Get user weight and height if there is a sensor for user
        initSensor()
        checkPermission()
        resetStepCounterAtMidnight(binding.root.context)
        goToStepDetailsPage()

        binding.steps.setOnClickListener {
            //Adımları puana çeviren bir sayfaya gitsin
            Toast.makeText(binding.root.context,"Yapım aşamasında!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun initSensor(){
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Toast.makeText(binding.root.context, getString(R.string.pedometer_sensor_not_found), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        stepSensor.let {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // do nothing
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            stepCount = event.values[0].toInt()
            binding.stepTextView.text = "$stepCount"

            val intSteps : Int = Integer.parseInt(binding.stepTextView.text.toString())
            val formattedCal = String.format("%.2f",calculateCalories(intSteps,80.0))
            binding.caloriesTextView.text = formattedCal
            val formattedKm = String.format("%.1f",calculateKilometers(intSteps))
            binding.kmTextView.text = formattedKm
        }
    }

    private fun calculateCalories(steps: Int, weight: Double): Double {
        val caloriesPerMile = 0.57 * weight
        val miles = steps / 2000.0
        return caloriesPerMile * miles
    }

    private fun calculateKilometers(steps: Int): Double {
        val meters = steps * 0.6
        return meters / 1000.0
    }

    private fun resetStepCount(context: Context) {
        stepCount = 0
    }

    private fun resetStepCounterAtMidnight(context: Context) {
        val calendar = Calendar.getInstance()

        // Gece yarısını temsil eden zamanı hesapla
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val midnight = calendar.timeInMillis

        // Zamanlayıcıyı ayarla ve çalıştır
        val timer = Timer()

        timer.schedule(object : TimerTask() {
            override fun run() {
                // Adım sayacını sıfırla
                resetStepCount(context)
            }
        }, midnight, TimeUnit.DAYS.toMillis(1))
    }

    companion object {
        private const val PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION = 1000
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // İzin verildi, adım sayacını başlat
                } else {
                    // İzin reddedildi, hata mesajı göster
                    Toast.makeText(requireContext(), getString(R.string.get_permission_text), Toast.LENGTH_LONG).show()
                }
                return
            }
            else -> {
                // Diğer izinler için default işlem yap
                super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            }
        }
    }

    private fun checkPermission(){
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                    PERMISSIONS_REQUEST_ACTIVITY_RECOGNITION)
            }
        }
    }

    private fun goToStepDetailsPage(){
        binding.goToStepsSideCardView.setOnClickListener {
            Toast.makeText(binding.root.context,"Yapım aşamasında!",Toast.LENGTH_SHORT).show()
        }
    }

}