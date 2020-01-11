package com.example.aidldemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aidldemo.R;
import com.example.aidldemo.utils.IPCUtils;

import static com.example.aidldemo.AIDLCmdConst.MAIN_CALL_SERVICE_CMD_ASK_FOR_DINNER;
import static com.example.aidldemo.AIDLCmdConst.MAIN_CALL_SERVICE_CMD_ASK_FOR_WECHAT;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtJumpService;
    private Button mBtAskForWechat;
    private Button mBtAskForDinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        mBtJumpService = findViewById(R.id.bt_jump_to_service);
        mBtAskForWechat = findViewById(R.id.bt_ask_for_wechat);
        mBtAskForDinner = findViewById(R.id.bt_ask_for_dinner);

        setViewClickListener();
    }

    private void setViewClickListener() {
        mBtJumpService.setOnClickListener(this);
        mBtAskForWechat.setOnClickListener(this);
        mBtAskForDinner.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.bt_jump_to_service){
            Intent intent = new Intent(this, ServiceActivity.class);
            startActivity(intent);
        }else if(id == R.id.bt_ask_for_wechat){
            IPCUtils.getInstance().doCallServiceCmd(MAIN_CALL_SERVICE_CMD_ASK_FOR_WECHAT, "");
        }else if(id == R.id.bt_ask_for_dinner){
            IPCUtils.getInstance().doCallServiceCmd(MAIN_CALL_SERVICE_CMD_ASK_FOR_DINNER, "");
        }
    }
}
