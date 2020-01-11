package com.example.aidldemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aidldemo.R;
import com.example.aidldemo.utils.IPCUtils;

import static com.example.aidldemo.AIDLCmdConst.SERVICE_CALL_MAIN_CMD_ASK_FOR_NAME;


public class ServiceActivity extends AppCompatActivity {

    private Button mBtAskForName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        initView();
    }

    private void initView() {
        mBtAskForName = findViewById(R.id.bt_ask_for_name);

        mBtAskForName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IPCUtils.getInstance().doCallMainCmd(SERVICE_CALL_MAIN_CMD_ASK_FOR_NAME, "");
            }
        });
    }
}
