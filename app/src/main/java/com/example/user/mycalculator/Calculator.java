package com.example.user.mycalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class Calculator extends AppCompatActivity {
    private TextView str;
    private String dp = "";
    private String currentOperator = "";
    private String c="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        str = (TextView) findViewById(R.id.textView);
        str.setText(dp);
    }
    public void Screen(){
        str.setText(dp);
    }

    public void onClickNumbers (View v){
        Button b = (Button) v;
        dp += b.getText();
        Screen();
    }

    public void onClickOperator (View v){
        Button b = (Button) v;
        if(dp.equals("")) return;
        if(!currentOperator.equals("")) dp = dp.replace(currentOperator, b.getText().toString());
        else dp += b.getText();
        currentOperator = b.getText().toString();
        Screen();
    }

    public void clear(){
        dp = "";
        currentOperator = "";
    }

    public void onClickClear(View v){
        clear();
        Screen();
    }

    public void onClickCopy(View v){
        if(!currentOperator.equals("")) return;
        c=dp;
        dp="";
        Screen();
    }

    public void onClickPaste(View v){
        if(c.equals("")) return;
        if(currentOperator.equals("") && !dp.equals(""))return;
        String[] operation = dp.split(Pattern.quote(currentOperator));
        if(operation.length > 1) return;
        dp+=c;
        Screen();
    }

    public double operate (String a, String b, String op){
        switch (op){
            case"+": return Double.valueOf(a) + Double.valueOf(b);
            case"-": return Double.valueOf(a) - Double.valueOf(b);
            case"x": return Double.valueOf(a) * Double.valueOf(b);
            case"/": return Double.valueOf(a) / Double.valueOf(b);
                default:
                    return -1;
        }
    }

    public double action (String a, String op) {
        Integer s=1;
        switch (op){
            case"%": return Double.valueOf(a) * 0.01;
            case"sqr": return Math.pow(Double.valueOf(a),2);
            case"sqrt": return Math.sqrt(Double.valueOf(a));
            case"cos": return Math.cos(Double.valueOf(a));
            case"sin": return Math.sin(Double.valueOf(a));
            case"tan": return Math.tan(Double.valueOf(a));
            case "!": {
                if(Integer.valueOf(a) < 34 && Integer.valueOf(a) >= 0) {
                    for (int i = 2; i <= Integer.valueOf(a); i++)
                        s *= i;
                    return s;
                }
                else return 1;
            }
            default: return -1;
        }
    }

    public void onClickOp (View v){
        Button b = (Button) v;
        if(dp.equals("")) return;
        if(!currentOperator.equals("")) return;
        currentOperator = b.getText().toString();
        Double result = action(dp, currentOperator);
        if(currentOperator.equals("!") || currentOperator.equals("%"))
            str.setText(dp + currentOperator + "\n" + String.valueOf(result));
        else
            str.setText(currentOperator + "(" + dp + ")" + "\n" + String.valueOf(result));
        dp = String.valueOf(result);
        currentOperator = "";
    }

    public void onClickEqual(View v){
        if(currentOperator.equals("")) return;
        String[] operation = dp.split(Pattern.quote(currentOperator));
        if(operation.length < 2) return;
        Double result = operate(operation[0], operation[1], currentOperator);
        str.setText(dp + "\n" + String.valueOf(result));
        dp = String.valueOf(result);
        currentOperator = "";
    }
}

