package com.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.medicalproject.R;
import com.helpers.WebService;

import org.ksoap2.serialization.SoapObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private List<Map<String, Object>> DoctorList = new ArrayList<Map<String, Object>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        final ListView listview = (ListView) findViewById(R.id.doctor_list);
        //通过工具类调用WebService接口
        WebService.callWebService(WebService.WEB_SERVER_URL, "selectDoctor", null, new WebService.WebServiceCallBack() {
            //WebService接口返回的数据回调到这个方法中
            public void callBack(SoapObject result) {
                if (result != null) {
                    DoctorList = DoctorSoapObject(result);
                    //创建一个自定义的SimpleAdapter
                    SimpleAdapter adapter = new SimpleAdapter(MainActivity.this,
                            DoctorList, R.layout.doctor_items,
                            new String[]{"doctorName", "dateAvg", "day7Persent", "curePersent", "diseaseLvAvg", "score"},
                            new int[]{R.id.doctor_name, R.id.date_avg, R.id.day7_persent, R.id.cure_persent, R.id.disease_lv_avg, R.id.score});
                    //将Adapter与ListView关联
                    listview.setAdapter(adapter);
                } else {
                    Toast.makeText(MainActivity.this, "获取WebService数据错误", Toast.LENGTH_SHORT).show();//弹出连接错误信息
                }
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, PatientAddActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    //解析SoapObject对象
    private List<Map<String, Object>> DoctorSoapObject(SoapObject result) {
        List<Map<String, Object>> DoctorItems = new ArrayList<Map<String, Object>>();
        SoapObject provinceSoapObject = (SoapObject) result.getProperty("selectDoctorResult");
        for (int i = 0; i < provinceSoapObject.getPropertyCount(); i = i + 7) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("doctorName", provinceSoapObject.getProperty(i).toString());
            map.put("dateAvg", provinceSoapObject.getProperty(i + 1).toString());
            map.put("day7Persent", provinceSoapObject.getProperty(i + 2).toString());
            map.put("curePersent", provinceSoapObject.getProperty(i + 3).toString());
            map.put("diseaseLvAvg", provinceSoapObject.getProperty(i + 4).toString());
            map.put("diseaseLvMax", provinceSoapObject.getProperty(i + 5).toString());
            map.put("score", provinceSoapObject.getProperty(i + 6).toString());
            DoctorItems.add(map);
        }
        return DoctorItems;
    }
}
