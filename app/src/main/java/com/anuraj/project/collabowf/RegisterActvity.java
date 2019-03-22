package com.anuraj.project.collabowf;
/**
 * Created by Anuraj R(i321994) a4anurajr@gmail.com
 */
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActvity extends Activity {

    private EditText inputName, inputPassword, inputDomain, inputId;
    private Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registerlayout);


        inputId = (EditText) findViewById(R.id.input_employeeid);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputDomain = (EditText) findViewById(R.id.input_domain);
        inputName = (EditText) findViewById(R.id.input_name);
        btnSave = (Button) findViewById(R.id.btn_register);
    }
}
