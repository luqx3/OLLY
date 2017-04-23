package com.example.cyhtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMGroupManager;
import com.example.dialog.FlippingLoadingDialog;
import com.juns.health.net.loopj.android.http.RequestParams;

import org.apache.http.message.BasicNameValuePair;
import org.bitlet.weupnp.Main;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Lociation on 2016/12/20.
 */

public class LoginActivity  extends Activity implements View.OnClickListener{
    private TextView txt_title, tv_password;
    private ImageView img_back;
    private Button btn_login, btn_register, btn_send_login;
    private EditText et_usertel, et_password;
    protected FlippingLoadingDialog mLoadingDialog;
    int i = 30;
    int fen = 0;
    // 填写从短信SDK应用后台注册得到的APPKEY
    private static String APPKEY = "1a1b7196fdf78";
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "1702707aab4596e5db4ed561b63178bf";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initControl();
        setListener();
        Log.d("--------------","==============");
    }


    protected void initControl() {
        txt_title = (TextView) findViewById(R.id.txt_title);
        tv_password = (TextView) findViewById(R.id.tv_password);
        txt_title.setText("登录");
        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setVisibility(View.VISIBLE);
        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_qtlogin);
        et_usertel = (EditText) findViewById(R.id.et_usertel);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_send_login = (Button) findViewById(R.id.btn_send_login);
        btn_send_login.setClickable(false);
    }


    protected void setListener() {
        img_back.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_register.setOnClickListener(this);
        btn_send_login.setOnClickListener(this);
        et_usertel.addTextChangedListener(new TextChange());
        et_password.addTextChangedListener(new TextChange());
        findViewById(R.id.tv_wenti).setOnClickListener(this);
        smsregis();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.tv_wenti:
                //界面跳转
                //SMSSDK.getVerificationCode("86", "15350290613");
                btn_send_login.setVisibility(View.VISIBLE);
                btn_send_login.setEnabled(true);
                btn_send_login.setClickable(true);
                btn_register.setVisibility(View.INVISIBLE);
                btn_register.setClickable(false);
                et_password.setHint("填写验证码");
                tv_password.setText("验证码");
                btn_login.setText("验证码登录");
                fen = 1;
                break;
            case R.id.btn_qtlogin:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                break;
            case R.id.btn_login:
                getLogin();
                if (fen == 1){
                    getRegister();
                }
                break;
            case R.id.btn_send_login:
                btn_send_login.setClickable(true);
                getCode();
                break;
            default:
                break;
        }
    }


    private boolean getLogin() {
        String userName = et_usertel.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        if (judgePhoneNums(userName)) {
            String tel = userName;
            String pwd = password;
            final ManageSQL manageSQL = new ManageSQL(getApplicationContext());
            String pwd2 = manageSQL.repwd(tel);
            if (pwd.equals(pwd2) && !TextUtils.isEmpty(pwd2)) {
                //getLoadingDialog("正在登录...  ").show();
                Toast.makeText(LoginActivity.this, "正在登录", Toast.LENGTH_SHORT).show();
                createProgressBar();
                Intent intent = new Intent(LoginActivity.this,
                        MainActivity.class);
                startActivity(intent);
            }
            else if (TextUtils.isEmpty(pwd2)) {
                Toast.makeText(LoginActivity.this, "用户名不存在，请注册", Toast.LENGTH_SHORT).show();
                return false;
            }
            //getChatserive(userName, DES.md5Pwd(password));
        } else {
            if (fen >= 1)
                Toast.makeText(LoginActivity.this, "请填写验证码！", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(LoginActivity.this, "请填写账号或密码！", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // EditText监听器

    class TextChange implements TextWatcher {

        @Override
        public void afterTextChanged(Editable arg0) {

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

        }

        @Override
        public void onTextChanged(CharSequence cs, int start, int before,
                                  int count) {
            boolean Sign2 = et_usertel.getText().length() > 0;
            boolean Sign3 = et_password.getText().length() > 4;
            if (fen == 1)
                Sign3 = et_password.getText().length() == 4;
            if (Sign2 & Sign3) {
                btn_login.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.btn_bg_green));
                btn_login.setEnabled(true);
            } else {
                btn_login.setBackgroundDrawable(getResources().getDrawable(
                        R.drawable.btn_enable_green));
                btn_login.setTextColor(0xFFD0EFC6);
                btn_login.setEnabled(false);
            }
        }
    }


    public void getCode() {
        // 1. 通过规则判断手机号
        if (judgePhoneNums(et_usertel.getText().toString())) {
            // 2. 通过sdk发送短信验证
            if(getLogin()) {
                SMSSDK.getVerificationCode("86", et_usertel.getText().toString());

                // 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
                btn_send_login.setClickable(false);
                btn_send_login.setText("重新发送(" + i + ")");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
            }
        }
    }

    public void getRegister(){
        //将收到的验证码和手机号提交再次核对
        String ul = et_usertel.getText().toString();
        String pd = et_password.getText().toString();
        //Toast.makeText(this, ul+"++"+pd, Toast.LENGTH_SHORT).show();
        SMSSDK.submitVerificationCode("86", ul, pd);
    }
    public void smsregis(){
        SMSSDK.initSDK(this,APPKEY,APPSECRET);
        EventHandler eventHandler = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        //注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }

    Handler handler=new Handler(){
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                btn_send_login.setText("重新发送(" + i + ")");
            } else if (msg.what == -8) {
                btn_send_login.setText("获取验证码");
                btn_send_login.setClickable(true);
                i = 30;
            } else {
                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;
                Log.e("event", "event=" + event);
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // 短信注册成功后，返回MainActivity,然后提示
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
                        Toast.makeText(getApplicationContext(), "提交验证码成功",
                                Toast.LENGTH_SHORT).show();
                        //btn_login.setOnClickListener(LoginActivity.this);
                        //startActivity(intent);
                        createProgressBar();
                        Intent it = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(it);
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        Toast.makeText(getApplicationContext(), "正在获取验证码",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "验证码错误",
                                Toast.LENGTH_SHORT).show();
                        ((Throwable) data).printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "验证码错误",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    };


    public FlippingLoadingDialog getLoadingDialog(String msg) {
        if (mLoadingDialog == null)
            mLoadingDialog = new FlippingLoadingDialog(this, msg);
        return mLoadingDialog;
    }
    /**
     * 判断手机号码是否合理
     *
     * @param phoneNums
     */
    private boolean judgePhoneNums(String phoneNums) {
        if (isMatchLength(phoneNums, 11)
                && isMobileNO(phoneNums)) {
            return true;
        }
        Toast.makeText(this, "手机号码输入有误！",Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 判断一个字符串的位数
     * @param str
     * @param length
     * @return
     */
    public static boolean isMatchLength(String str, int length) {
        if (str.isEmpty()) {
            return false;
        } else {
            return str.length() == length ? true : false;
        }
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
        /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
        String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }

    /**
     * progressbar
     */
    private void createProgressBar() {
        FrameLayout layout = (FrameLayout) findViewById(android.R.id.content);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        ProgressBar mProBar = new ProgressBar(this);
        mProBar.setLayoutParams(layoutParams);
        mProBar.setVisibility(View.VISIBLE);
        layout.addView(mProBar);
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterAllEventHandler();
        super.onDestroy();
    }
}
