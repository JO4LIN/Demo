package com.shengrui.huilian.main_sideslip.task_details.Specific_task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.shengrui.huilian.R;
import com.shengrui.huilian.base_mvp.Global.Global;
import com.shengrui.huilian.base_mvp.net.FinishCallBack;
import com.shengrui.huilian.base_mvp.net.Utils;
import com.shengrui.huilian.main_sideslip.indent_details.Specific_indent.Specific_indent_details_presenter;
import com.shengrui.huilian.medium_infor.MedCheckActivity;
import com.shengrui.huilian.user_infor.User_check_info;

/**
 * Created by jh on 2016/7/23.
 */
public class Specific_task_details  extends AppCompatActivity implements View.OnClickListener {


    private ImageView userHead;
    private TextView userName;
    private TextView title;
    private TextView intro;
    private TextView price;
    private TextView graphicsTypes;
    private TextView proDate;
    private TextView link;
    private RelativeLayout mediaInfo;
    private TextView activityTitle;
    private RelativeLayout back;
    ProgressDialog progressDialog = null;
    private Bundle bundle = null;
    private int indentId;
    private int progress;
    private int userId;
    private TextView accept;
    private TextView refuse;
    private TextView userType;

    private Specific_task_details_presenter activePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_indent);

        if (activePresenter == null){
            activePresenter = Specific_task_details_presenter.getInstance();
        }
        initialize();      //初始化控件
        activityType();
        refreshMedia();
    }

    private void activityType() {
        userType.setText("广告主");
        activityTitle.setText("任务详情");
        if(progress==3){
            refuse.setVisibility(View.VISIBLE);
            accept.setVisibility(View.VISIBLE);
        }
    }

    private void initialize() {
        bundle=this.getIntent().getExtras();
        indentId = Integer.valueOf(bundle.getString("indentId"));
        progress = Integer.valueOf(bundle.getString("progress"));
        back = (RelativeLayout) findViewById(R.id.back);
        userHead = (ImageView) findViewById(R.id.mainHead);
        userName = (TextView) findViewById(R.id.mainName);
        title = (TextView) findViewById(R.id.title);
        intro = (TextView) findViewById(R.id.intro);
        price = (TextView) findViewById(R.id.price);
        graphicsTypes = (TextView) findViewById(R.id.graphicsTypes);
        proDate = (TextView) findViewById(R.id.proDate);
        link = (TextView) findViewById(R.id.link);
        refuse = (TextView) findViewById(R.id.refuse);
        accept = (TextView) findViewById(R.id.accept);
        mediaInfo = (RelativeLayout) findViewById(R.id.mediaInfo);
        activityTitle = (TextView) findViewById(R.id.activityTitle);
        userType = (TextView) findViewById(R.id.userType);

        back.setOnClickListener(this);
        refuse.setOnClickListener(this);
        accept.setOnClickListener(this);
        mediaInfo.setOnClickListener(this);
    }

    private void refreshMedia() {
        activePresenter.refreshTask(indentId, new FinishCallBack() {
            public void onSuccess() {
                userName.setText(activePresenter.taskModel.getUserName());
                userHead.setImageBitmap(Utils.toRoundBitmap(Utils.returnBitMap(activePresenter.taskModel.getUserHead())));
                title.setText(activePresenter.taskModel.getTitle());
                intro.setText(activePresenter.taskModel.getIntro());
                proDate.setText(activePresenter.taskModel.getProDate());
                link.setText(activePresenter.taskModel.getLink());
                price.setText(String.valueOf(activePresenter.taskModel.getPrice()));
                userId = activePresenter.taskModel.getUserId();
                graphicsTypes.setText(activePresenter.taskModel.getGraphicsTypes());
                Log.d("---------------", String.valueOf(userId));
            }

            public void onFailure() {
                Toast.makeText(Global.context, "请求失败", Toast.LENGTH_SHORT).show();
            }
        });
        //meidaHead.setImageBitmap();
        //       String[] s = mSendIndentPresenter.refreshReturn();
        //      mediaName.setText(s[0]);
        //      indentNum.setText(s[1]);
        //mediaName.setText(mSendIndentPresenter.mSendIndentModel.getMediaName());
        // indentNum.setText(model.getIndentNum());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.accept:
                RequestParams paramsfFinish = new RequestParams();
                paramsfFinish.put("progress",0);
                updateProgress(paramsfFinish);
                break;
            case R.id.refuse:
                RequestParams paramsbBack = new RequestParams();
                paramsbBack.put("progress",2);
                updateProgress(paramsbBack);
                break;
            case R.id.mediaInfo:
                startActivity(new Intent(Specific_task_details.this, User_check_info.class).putExtra("userId", String.valueOf(userId)));
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    public void updateProgress(RequestParams params){
        params.put("indentId",indentId);
//        progressDialog = ProgressDialog.show(Global.context,"Loading","Please wait...",true,true);
        activePresenter.progress(params, new FinishCallBack() {
            public void onSuccess() {
                //    progressDialog.dismiss();
                Toast.makeText(Global.context, "操作成功", Toast.LENGTH_SHORT).show();
                setResult(Activity.RESULT_OK);
                finish();
            }

            public void onFailure() {
                Toast.makeText(Global.context, "更改失败", Toast.LENGTH_SHORT).show();
                //   progressDialog.dismiss();
            }
        });
    }
}
