package skven.com.moviesvisittracker.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import skven.com.moviesvisittracker.R;
import skven.com.moviesvisittracker.constants.LoginConstants;
import skven.com.moviesvisittracker.helper.SharedPreferenceHelper;


public class LoginActivity  extends AppCompatActivity {

    EditText userId;
    Button login;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences(LoginConstants.USER_ID, MODE_PRIVATE);
        if(sharedPreferences.contains(LoginConstants.USER_ID)) {
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        }else {
            setContentView(R.layout.login);
            userId = findViewById(R.id.userId);
            login = findViewById(R.id.loginButton);

            login.setOnClickListener(view ->{
                if(TextUtils.isEmpty(userId.getText())) {
                    Toast.makeText(getApplicationContext(), LoginConstants.USER_ID + " is empty", Toast.LENGTH_LONG).show();
                    return;
                }
                SharedPreferenceHelper.addKey(this, LoginConstants.USER_ID, LoginConstants.USER_ID, userId.getText().toString());
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            });
        }





    }
}
