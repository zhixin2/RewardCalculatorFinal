package com.example.rewardcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.rewardcalculator.Constant.CITI_URL;


public class citi extends AppCompatActivity implements View.OnClickListener {

    private Button back;
    private TextView moneycash;
    private TextView moneytravel;
    private Button convert;
    private EditText point;

    private List<String> dataList;
    private TextView textView;
    private Spinner spinner;
    private ArrayAdapter<String> adapter;

    private int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.citi);

        back = findViewById(R.id.Return1);
        back.setOnClickListener(this);

        convert = findViewById(R.id.amex_clikcer);
        convert.setOnClickListener(this);



        point = findViewById(R.id.totalpoints);

        moneycash = findViewById(R.id.money_chase);
        moneytravel = findViewById(R.id.trvael_chase);

//        money.setVisibility(View.GONE);

//        EditText point = findViewById(R.id.totalpoints);
//        String txt = point.getText().toString();
//        if (!txt.isEmpty()) {
//            int number = Integer.parseInt(point.getText().toString());
//            money = findViewById(R.id.textout_amex);
//            money.setText(String.valueOf(number));
//        }
        spinner = (Spinner) findViewById(R.id.spinner);
        textView = (TextView) findViewById(R.id.tv);

        //为dataList赋值，将下面这些数据添加到数据源中
        dataList = new ArrayList<String>();
        dataList.add("Please Make a Selection");
        dataList.add("CARD1");
        dataList.add("CARD2");



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
            case R.id.amex_clikcer:
                String txt = point.getText().toString();
                if (!txt.isEmpty()) {
                    if (currentPos == dataList.indexOf("CARD1")) {
                        double number = Double.valueOf(point.getText().toString());
                        number = number * 0.01;
                        double numbertravel = Double.valueOf(point.getText().toString());
                        numbertravel = numbertravel *0.0125;
                        moneycash.setText("$" + String.valueOf(number) + " In Cash");
                        moneycash.setVisibility(View.VISIBLE);
                        moneytravel.setText("$" + String.valueOf(numbertravel) + " For Travel");
                    } else if (currentPos == dataList.indexOf("CARD2")) {
                        double number = Double.valueOf(point.getText().toString());
                        number = number * 0.01;
                        double numbertravel = Double.valueOf(point.getText().toString());
                        numbertravel = numbertravel *0.015;
                        moneycash.setText("$" + String.valueOf(number) + " In Cash");
                        moneycash.setVisibility(View.VISIBLE);
                        moneytravel.setText("$" + String.valueOf(numbertravel) + " For Travel");
                    }

                } else {
                    moneycash.setVisibility(View.GONE);
                    moneytravel.setVisibility(View.GONE);
                }
                break;
            case R.id.Return1:
                finish();
                break;


        }
        if (intent != null){
            startActivity(intent);
        }
    }

}
