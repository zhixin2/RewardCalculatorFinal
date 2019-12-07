package com.example.rewardcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.rewardcalculator.Constant.AMEX_URL;


public class Amex extends AppCompatActivity implements View.OnClickListener {

    private Button back;
    private TextView money;
    private Button convert;
    private EditText point;

    private double rate;
    private  Button convertrmb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.amex);

        back = findViewById(R.id.Return1);
        back.setOnClickListener(this);

        convert = findViewById(R.id.amex_clikcer);
        convert.setOnClickListener(this);



        point = findViewById(R.id.totalpoints);

        money = findViewById(R.id.money_amex);
        convertrmb = findViewById(R.id.amex_clicked_rmb);
        convertrmb.setOnClickListener(this);

//        money.setVisibility(View.GONE);

//        EditText point = findViewById(R.id.totalpoints);
//        String txt = point.getText().toString();
//        if (!txt.isEmpty()) {
//            int number = Integer.parseInt(point.getText().toString());
//            money = findViewById(R.id.textout_amex);
//            money.setText(String.valueOf(number));
//        }

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.amex_clikcer:
                String txt = point.getText().toString();
                if (!txt.isEmpty()) {
                    double number = Double.valueOf(point.getText().toString());
                    number = number * 0.005;
                    money.setText("$" + String.valueOf(number) + " In Cash");
                    money.setVisibility(View.VISIBLE);
                } else {
                    money.setVisibility(View.GONE);
                }
                break;
            case R.id.amex_clicked_rmb:
                txt = point.getText().toString();
                if (!txt.isEmpty()) {
                    //https://developer.android.com/training/volley/simple
                    RequestQueue queue = Volley.newRequestQueue(this);
                    String url = "https://openexchangerates.org/api/latest.json?app_id=d0dbe219da2f4621801db1fe8d4fe052";
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            String[] splitted = response.split("CNY\":");
                            rate = Double.parseDouble(splitted[1].substring(0,7));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            money.setText("Connection Failed");
                        }
                    });
                    queue.add(stringRequest);
                    double number = Double.valueOf(point.getText().toString());
                    number = number * 0.005 * rate;
                    money.setText("¥" + String.valueOf(number) + " In Cash");
                    money.setVisibility(View.VISIBLE);
                } else {
                    money.setVisibility(View.GONE);
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
