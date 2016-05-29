package com.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.medicalproject.R;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by mingk on 2016/2/15.
 */
public class PatientAddActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*在Android4.0以后，会发现，只要是写在主线程（就是Activity）中的HTTP请求
        运行时都会报错，这是因为Android在4.0以后为了防止应用的ANR（aplication Not Response）异常*/
        if (Build.VERSION.SDK_INT >= 11) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patientadd);

        final EditText patientName = (EditText) findViewById(R.id.patient_name);
        final EditText inDate = (EditText) findViewById(R.id.in_date);
        final EditText outDate = (EditText) findViewById(R.id.out_date);
        final EditText doctorID = (EditText) findViewById(R.id.doctor_id);
        final EditText cure = (EditText) findViewById(R.id.cure);
        final Spinner diseaseLv = (Spinner) findViewById(R.id.disease_lv);

        Button confirm = (Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String PatientName = patientName.getText().toString().trim();
                //Log.e("PatientName", PatientName);
                final String InDate = inDate.getText().toString().trim();
                final String OutDate = outDate.getText().toString().trim();
                final String DoctorID = doctorID.getText().toString().trim();
                final String Cure = cure.getText().toString().trim();
                final String DiseaseLv = diseaseLv.getSelectedItem().toString().trim();
                if (PatientName.length() != 0 && InDate.length() != 0 && OutDate.length() != 0 && DoctorID.length() != 0 && Cure.length() != 0){
                    setPatient(PatientName, InDate, OutDate, DoctorID, Cure, DiseaseLv);
                    Intent intent = new Intent();
                    intent.setClass(PatientAddActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else{
                    Toast.makeText(getBaseContext(), "未填完必选项", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setPatient(String sPatientName, String sInDate, String sOutDate, String sDoctorID, String sCure, String sDiseaseLv) {
        //Log.e("PatientName", sPatientName);
        // 命名空间
        String nameSpace = "http://tempuri.org/";

        // 调用的方法名称
        String methodName = "insertPatient";//WebService调用的方法名

        // EndPoint
        String endPoint = "http://192.168.187.2:8080//Service1.asmx";//WebService的地址！重要！更换服务器地址记得更改此语句！

        // SOAP Action
        String soapAction = "http://tempuri.org/insertPatient";

        // 指定WebService的命名空间和调用的方法名
        SoapObject rpc = new SoapObject(nameSpace, methodName);

        // 设置需调用WebService接口需要传入的两个参数mobileCode、userId，不可以随便写，必须和提供的参数名相同
        rpc.addProperty("PatientName", sPatientName);
        rpc.addProperty("InDate", sInDate);
        rpc.addProperty("OutDate", sOutDate);
        rpc.addProperty("DoctorID", sDoctorID);
        rpc.addProperty("Cure", sCure);
        rpc.addProperty("DiseaseLv", sDiseaseLv);

        // 生成调用WebService方法的SOAP请求信息,并指定SOAP的版本
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

        envelope.bodyOut = rpc;

        // 设置是否调用的是dotNet开发的WebService
        envelope.dotNet = true;

        // 等价于envelope.bodyOut = rpc;
        envelope.setOutputSoapObject(rpc);
        HttpTransportSE transport = new HttpTransportSE(endPoint);

        try {
            // 调用WebService
            transport.call(soapAction, envelope);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
