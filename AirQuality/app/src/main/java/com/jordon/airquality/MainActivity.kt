package com.jordon.airquality

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.jordon.airquality.databinding.ActivityMainBinding
import com.jordon.airquality.retrofit.AirQualityReponse
import com.jordon.airquality.retrofit.AirQualityService
import com.jordon.airquality.retrofit.RetrofitConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.io.IOException
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var locationProvider: LocationProvider

    private val PERMISSIONS_REQUEST_CODE = 100


    val REQUIRED_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    lateinit var getGPSPermissionLancher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkAllPermissions()
        updateUI()
        setRefreshButton()
    }


    private fun updateUI() {
        locationProvider = LocationProvider(this@MainActivity)

        val latitude : Double? = locationProvider.getLocationLatitude()
        val longitude : Double? = locationProvider.getLocationLongitude()

        if(latitude != null && longitude != null){
            //1. 현재 위치가져와서 UI 업데이트
            val address = getCurrentAddress(latitude, longitude)

            //.let은 address가 null 아닌 경우에 실행
            address?.let {
                val titleName = it.thoroughfare ?: "${it.subLocality}"
                binding.tvLocationTitle.text = titleName
                binding.tvLocationSubtitle.text = "${it.countryName} ${it.adminArea} ${it.locality}"
            }
            //2. 미세먼지 농도 가져와서 UI 업데이트
            getAirQualityData(latitude, longitude)
        }else {
            Toast.makeText(this, "위도, 경도 정보를 가져올 수 없습니다.", Toast.LENGTH_LONG).show()
        }
    }

    private fun getAirQualityData(latitude: Double, longitude: Double){
        var retrofitApi = RetrofitConnection.getInstance().create(
            AirQualityService::class.java
        )

        // execute(동기)
        // enqueue(비동기)
        retrofitApi.getAirQualityData(
            latitude.toString(),
            longitude.toString(),
            "[API KEY 지정]"
        ).enqueue( object: Callback<AirQualityReponse>{
                override fun onResponse(
                    call: Call<AirQualityReponse>,
                    response: Response<AirQualityReponse>
                ) {
                    if(response.isSuccessful){
                        Toast.makeText(this@MainActivity, "최신 데이터 업데이트 완료!", Toast.LENGTH_LONG).show()
                        response.body()?.let{updateAirUI(it)}
                    } else {
                        Toast.makeText(this@MainActivity, "데이터를 가져오는 데 실패했습니다", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<AirQualityReponse>, t: Throwable) {
                    // 에러 출력
                    t.printStackTrace()
                    Toast.makeText(this@MainActivity, "데이터를 가져오는 데 실패했습니다", Toast.LENGTH_LONG).show()
                }
            }
        ) // enqueue

    }

    private fun updateAirUI(airQualityData : AirQualityReponse){

        val pollution = airQualityData.data.current.pollution

        // 수치를 지정
        binding.tvCount.text = pollution.aqius.toString()

        // 측정된 날짜
        val dateTime = ZonedDateTime.parse(pollution.ts).withZoneSameInstant(ZoneId.of("Asia/Seoul")).toLocalDateTime()
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        binding.tvCheckTime.text = dateTime.format(dateFormatter).toString()


        when(pollution.aqius){
            in 0..50 -> {
                binding.tvTitle.text = "좋음"
                binding.imgBg.setImageResource(R.drawable.bg_good)
            }

            in 51..150 -> {
                binding.tvTitle.text = "보통"
                binding.imgBg.setImageResource(R.drawable.bg_soso)
            }

            in 151..200 -> {
                binding.tvTitle.text = "나쁨"
                binding.imgBg.setImageResource(R.drawable.bg_bad)
            }

            else -> {
                binding.tvTitle.text = "매우 나쁨"
                binding.imgBg.setImageResource(R.drawable.bg_worst)
            }

        }
    }

    private fun setRefreshButton(){
        binding.btnRefresh.setOnClickListener {
            updateUI()
        }
    }

    private fun getCurrentAddress (latitude: Double, longitude : Double) : Address? {
        val geoCoder = Geocoder(this, Locale.KOREA)

        val addresses : List<Address>

        addresses = try {
            geoCoder.getFromLocation(latitude, longitude, 7)!!
        } catch(ioException : IOException) {
            Toast.makeText(this, "지오코더 서비스를 이용불가 입니다.", Toast.LENGTH_LONG).show()
            return null
        } catch(illegalArgumentException : java.lang.IllegalAccessError){
            Toast.makeText(this, "잘못된 위도, 경도 입니다.", Toast.LENGTH_LONG).show()
            return null
        }

        if(addresses == null || addresses.size == 0){
            Toast.makeText(this, "주소가 발견되지 않았습니다.", Toast.LENGTH_LONG).show()
            return null
        }
        return addresses[0]
    }

    private fun checkAllPermissions() {
        if(!isLocationServicesAvailable()){
            // GPS 권한이 꺼저 있을때
            showDialogForLocationServiceSetting()
        } else {
            isRunTimePermissionsGranted()
        }
    }

    private fun isLocationServicesAvailable() : Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        // GPS나 네트워크를 활성화되어 있는지 체크
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    private fun isRunTimePermissionsGranted() {
        // COARSE_LOCATION : 대략적인 위치 정보를 얻을 수 있음
        // FINE_LOCATION : 자세한 위치 정보를 얻을 수 있음

        // GPS를 Provider로 사용하냐, 네트워크를 Provider로 사용하냐에 따라 조금 차이가 있을 수 있지만
        // GPS든 네트워크든 사용할려면 ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION Permission을 추가해줘야 한다.
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarselLocationPermission = ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION)


        if(hasFineLocationPermission != PackageManager.PERMISSION_GRANTED || hasCoarselLocationPermission != PackageManager.PERMISSION_GRANTED) {
            //ACCESS_FINE_LOCATION 나 ACCESS_COARSE_LOCATION 둘 중 하나라도 권한이 없다면 권한요청을 해야한다.

            // REQUIRED_PERMISSIONS [ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION]
            // PERMISSIONS_REQUEST_CODE = 100(위 단에서 정의)
            // 이유 : 우리가 Result로 받아올때 어떤 요청이였는지를 식별하기 위함이다.
            // 즉) 식별 할 수 있는 ID 값이라고 보면 된다.
            // 예를들어 100이라는 코드 값으로 보내고 결과에 대해 응답을 받았을때 100이란 값이 나왔다면
            // 요청자가 보낸 값에 대해 응답을 받았는지 식별 할 수 있다는 것이다.

            //requestPermissions가 정상적으로 처리 되었다면 override 되는 onRequestPermissionsResult 함수가 호출 될 것이다.
            ActivityCompat.requestPermissions(this@MainActivity, REQUIRED_PERMISSIONS, PERMISSIONS_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == PERMISSIONS_REQUEST_CODE && grantResults.size == REQUIRED_PERMISSIONS.size){
            var checkResult = true

            for(result in grantResults){
                if(result != PackageManager.PERMISSION_GRANTED){
                    checkResult = false
                    break;
                }
            }

            if(checkResult){
                // 위치값을 가져올 수 있음
                updateUI()
            }else {
                Toast.makeText(this@MainActivity, "권한이 거부되었습니다. 앱을 다시 실행하여 권한을 허용해주세요.", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun showDialogForLocationServiceSetting() {
        getGPSPermissionLancher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            //서비스 권한 요청 dialog로그에서 선택했을때 그 결과에 대해 callback 함수로 실행 된다.
                result ->
                if(result.resultCode == Activity.RESULT_OK){
                    if(isLocationServicesAvailable()){
                        isRunTimePermissionsGranted()
                    } else {
                        Toast.makeText(this@MainActivity, "위치 서비스를 사용할 수 없습니다.", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
        }


        val builder : AlertDialog.Builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("위치 서비스 활성화")
        builder.setMessage("위치 서비스가 꺼져있습니다. 설정해야 앱을 사용할 수 있습니다.")
        builder.setCancelable(true)
        builder.setPositiveButton("설정", DialogInterface.OnClickListener { dialogInterface, i ->
            val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            getGPSPermissionLancher.launch(callGPSSettingIntent)
        })
        builder.setNegativeButton("취소", DialogInterface.OnClickListener { dialogInterface, i ->
            dialogInterface.cancel()
            Toast.makeText(this@MainActivity, "위치 서비스를 사용할 수 없습니다.", Toast.LENGTH_LONG).show()
            finish()
        })

        builder.create().show()
    }

}