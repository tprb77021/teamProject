package com.example.georlock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_function_open_list.*
import org.json.JSONArray
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class Function_openList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_function_open_list)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        Thread(){
          var list:ArrayList<String> = UpdateMainLog()
            runOnUiThread{
                Log.i("testLog", "loginclick : ${list}")
                openlistview.adapter = ArrayAdapter<String>(
                    this,
//            android.R.layout.simple_list_item_1,
                    R.layout.layout_list,
                    list)
            }
        }.start()

        search_open_button.setOnClickListener {
            Thread(){
                 var list:ArrayList<String> = searchMainLog("${search_open.text.toString()}","${search_opendate1.text.toString()}","${search_opendate2.text.toString()}")
                runOnUiThread{
                    Log.i("testLog", "loginclick : ${list}")
                    openlistview.adapter = ArrayAdapter<String>(
                        this,
//            android.R.layout.simple_list_item_1,
                        R.layout.layout_list,
                        list)


                }
            }.start()
        }
    }


    fun UpdateMainLog():ArrayList<String>{
        val url = URL("${Static.server_url}/openlist")

        var list:ArrayList<String> = arrayListOf()

            var txt = url.readText()
            var arr:JSONArray = JSONArray(txt)
            for(i in 0 until arr.length()){
                var obj:JSONObject = arr.get(i) as JSONObject
                Log.i("testLog", "cc :${ obj["intime"].toString()}")
                list.add("사번 : ${ obj["empNo"].toString()} \n이름 : ${ obj["username"].toString()} \n출입 시간 \n ${ obj["intime"].toString()}")
            }
            return list

    }


    fun searchMainLog(search:String,date1:String,date2:String):ArrayList<String>{

        var se=  URLEncoder.encode(search, "UTF-8");
        val url = URL("${Static.server_url}/openSearch?search=${se}&startDate=${date1}&endDate=${date2}")

        Log.i("testLog", "search : ${se}")
        var list:ArrayList<String> = arrayListOf()

            var txt = url.readText()
            var arr: JSONArray = JSONArray(txt)
            for(i in 0 until arr.length()){
                var obj: JSONObject = arr.get(i) as JSONObject
                list.add("사번 : ${ obj["empNo"].toString()} \n이름 : ${ obj["username"].toString()} \n출입 시간 \n ${ obj["intime"].toString()}")
            }
            return list

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                //toolbar의 back키 눌렀을 때 동작
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}