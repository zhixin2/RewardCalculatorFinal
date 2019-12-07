package com.example.rewardcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.rewardcalculator.Constant.AMEX_URL;
import static com.example.rewardcalculator.Constant.CHASE_URL;
import static com.example.rewardcalculator.Constant.CITI_URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button amex;
    private Button chase;
    private Button citi;
    private Button apply;

    private List<String> dataList;
    private TextView textView;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private TextView applyBanner;

    private int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        amex = findViewById(R.id.amex);
        amex.setOnClickListener(this);
        chase = findViewById(R.id.chase);
        chase.setOnClickListener(this);

        citi = findViewById(R.id.citi);
        citi.setOnClickListener(this);

        apply = findViewById(R.id.apply_main);
        apply.setOnClickListener(this);
        /*final Button chase = findViewById(R.id.chase);
        Amex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, chase.class);
                startActivity(intent);
            }
        });*/
        applyBanner = findViewById(R.id.applyBanner);
        applyBanner.setVisibility(View.VISIBLE);

        spinner = (Spinner) findViewById(R.id.spinner);
        textView = (TextView) findViewById(R.id.tv);

        //为dataList赋值，将下面这些数据添加到数据源中
        dataList = new ArrayList<String>();
        dataList.add("Please Make a Selection");
        dataList.add("American Express");
        dataList.add("CHASE");
        dataList.add("CITIBANK");


        /*为spinner定义适配器，也就是将数据源存入adapter，这里需要三个参数
        1. 第一个是Context（当前上下文），这里就是this
        2. 第二个是spinner的布局样式，这里用android系统提供的一个样式
        3. 第三个就是spinner的数据源，这里就是dataList*/
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dataList);

        //为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //为spinner绑定我们定义好的数据适配器
        spinner.setAdapter(adapter);

        //为spinner绑定监听器，这里我们使用匿名内部类的方式实现监听器
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == dataList.indexOf("Please Make a Selection")) {
                    textView.setText(adapter.getItem(position));
                } else {
                    textView.setText("Your Current Selection is：" + adapter.getItem(position));
                }
                currentPos = position;


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                textView.setText("Please Make a Selection");

            }
        });

    }



    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.amex:
                intent = new Intent(MainActivity.this, Amex.class);
                break;
            case R.id.chase:
                intent = new Intent(this,chase.class);
                break;
            case R.id.citi:
                intent = new Intent(this,citi.class);
                intent.putExtra("URL",CITI_URL);
                break;
            default:
                break;
            case R.id.apply_main:
                intent = new Intent(this, WebActivity.class);

                if (currentPos == dataList.indexOf("American Express")) {
                    intent.putExtra("URL",AMEX_URL);
                } else if (currentPos == dataList.indexOf("CHASE")) {
                    intent.putExtra("URL",CHASE_URL);
                } else if (currentPos == dataList.indexOf("CITIBANK")) {
                    intent.putExtra("URL", CITI_URL);
                }

        }
        if (intent != null){
            startActivity(intent);
        }
    }
}
