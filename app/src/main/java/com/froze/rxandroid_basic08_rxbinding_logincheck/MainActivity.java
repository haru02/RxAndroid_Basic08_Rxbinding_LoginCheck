package com.froze.rxandroid_basic08_rxbinding_logincheck;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import rx.Observable;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSign = (Button)findViewById(R.id.btnSignin);

        Observable<TextViewTextChangeEvent> idObs = RxTextView.textChangeEvents((EditText)findViewById(R.id.editTextID));

        Observable<TextViewTextChangeEvent> pwObs = RxTextView.textChangeEvents((EditText)findViewById(R.id.editTextPw));

        Observable.combineLatest(idObs, pwObs,
                (idChanges, pwChanges) -> {
                    // ID : 5글자 이상
                    boolean idCheck = idChanges.text().length() >= 5;
                    // PW : 8글자 이상(특수문자 1글자 이상)
                    boolean pwCheck = pwChangeCheck(pwChanges.text().toString());
                    return idCheck && pwCheck;
                })
                .subscribe(
                        checkFlag -> btnSign.setEnabled(checkFlag)
                );

    }
    public boolean pwChangeCheck(String pwChanges) {

        //특수문자 1글자 이상 포함되도록 하는 함수
        int sum = 0;
        for (int i = 0; i < pwChanges.length(); i++) {
            char ch = pwChanges.charAt(i);
            if ((ch >= 33 && ch <= 47) || (ch >= 58 && ch <= 64)) sum++;
        }

        return (pwChanges.toString().length() >= 8) && (sum > 0);
    }
}
