package com.example.cyhtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

/////import android.graphics.drawable.Drawable.Callback;
/*import com.tab.R; 
import com.tab.bitmap.ImageLoader; 
import com.tab.bitmap.ImageLoader.Callback; 
import com.tab.entity.ArticleInfo; 
import com.tab.entity.MagaInfo; 
import com.tab.utils.Constant; 
import com.tab.utils.DefinedScrollView; 
import com.tab.utils.JsonParse; */

/**
 * * 利用自定义的ScrollView加载视图来实现翻页效果，下面用页码和总页数标识当前的视图是第几屏
*  
* @author WANGXIAOHONG 
*  
*/
public class ActContentByDefinedView extends Activity{
	private LinearLayout mLinearLayout;
	private LinearLayout.LayoutParams param;
	private DefinedScrollView scrollView; 
	private LayoutInflater inflater, inflater2, inflater3;
	private TextView mTextView;
	private ImageView mImageViewMagaPic;
	private int pageCount = 0;
	private View small, sex;

	//点击修改信息
	private TextView tv_region,tv_region_temp, tv_sign, tv_sign_temp;
	private ImageView img_right, head, ic_sex, img_back;
	private String Name, UserId;
	private Button btn_sendmsg;
	private RadioButton boy;
	private RadioButton girl;
	private TextView tag;
	private EditText con;
	private Button giveup, save;

  
	private void setupView() { 
		scrollView = (DefinedScrollView) findViewById(R.id.definedview); 
		mTextView = (TextView) findViewById(R.id.text_page);

		pageCount = 4;
	  
		mTextView.setText(1 + "/" + pageCount); 
	  
		for (int i = 0; i < pageCount; i++) { 
			param = new LinearLayout.LayoutParams(
			android.view.ViewGroup.LayoutParams.FILL_PARENT, 
			android.view.ViewGroup.LayoutParams.FILL_PARENT); 
			inflater = this.getLayoutInflater(); 
		  
			if (i == 0) { 
				final View addview = inflater.inflate( R.layout.about_us_1, null);
				img_back = (ImageView) addview.findViewById(R.id.img_back);
				img_back.setVisibility(View.VISIBLE);
				img_back.setOnClickListener(listener);

				mLinearLayout = new LinearLayout(this);
				mLinearLayout.addView(addview, param); 
				scrollView.addView(mLinearLayout); 
			}
			else if (i == 1){
				View addview = inflater.inflate(R.layout.about_us_2, null);
				img_back = (ImageView) addview.findViewById(R.id.img_back);
				img_back.setVisibility(View.VISIBLE);
				img_back.setOnClickListener(listener);

				mLinearLayout = new LinearLayout(this);
				mLinearLayout.addView(addview, param); 
				scrollView.addView(mLinearLayout); 
			}
			else if (i == 2){
				View addview = inflater.inflate(R.layout.about_us_3, null);
				img_back = (ImageView) addview.findViewById(R.id.img_back);
				img_back.setVisibility(View.VISIBLE);
				img_back.setOnClickListener(listener);

				mLinearLayout = new LinearLayout(this);
				mLinearLayout.addView(addview, param);
				scrollView.addView(mLinearLayout);
			}
			else {
				View addview = inflater.inflate(R.layout.about_us_4, null);
				img_back = (ImageView) addview.findViewById(R.id.img_back);
				img_back.setVisibility(View.VISIBLE);
				img_back.setOnClickListener(listener);

				mLinearLayout = new LinearLayout(this);
				mLinearLayout.addView(addview, param);
				scrollView.addView(mLinearLayout);
			}
		  
	} 

	scrollView.setPageListener(new DefinedScrollView.PageListener() {
		@Override
		public void page(int page) { 
		setCurPage(page); 
		} 
	}); 
} 
  
	private void setCurPage(int page) { 
		mTextView.setText((page + 1) + "/" + pageCount); 
	} 
  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_us_main);

		inflater2 = LayoutInflater.from(ActContentByDefinedView.this);
		small = inflater2.inflate(R.layout.perinfo_small_view, null);
		inflater3 = LayoutInflater.from(ActContentByDefinedView.this);
		sex = inflater3.inflate(R.layout.sex_info, null);
		setupView();
	}



	protected void setListener() {
		tv_sign.setOnClickListener(listener);
		tv_region.setOnClickListener(listener);
		ic_sex.setOnClickListener(listener);
		btn_sendmsg.setOnClickListener(listener);
	}
	private View.OnClickListener listener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.img_right:
					Intent it = new Intent(ActContentByDefinedView.this, MainActivity.class);
					startActivity(it);
					break;
				case R.id.btn_sendmsg:

					break;
				case R.id.tv_region:
					updateArea();
					break;
				case R.id.tv_sign:
					updateSign();
					break;
				case R.id.iv_sex:
					updateSex();
					break;
				case R.id.img_back:
					finish();
					break;
				default:
					break;
			}
		}
	};


	public void updateSex(){
		final AlertDialog.Builder builder = new AlertDialog.Builder(ActContentByDefinedView.this);
		builder.setTitle("性别");
		builder.setView(sex);
		final AlertDialog dag = builder.create();
		dag.show();//需要先显示才能查找控件
		boy = (RadioButton) findViewById(R.id.boy);
		girl = (RadioButton) findViewById(R.id.girl);
		giveup = (Button) findViewById(R.id.giveup);
		save = (Button) findViewById(R.id.save);
		giveup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dag.cancel();
			}
		});
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (boy.isChecked())
					ic_sex.setImageDrawable(getResources().getDrawable(R.drawable.ic_sex_male));
				if (girl.isChecked())
					ic_sex.setImageDrawable(getResources().getDrawable(R.drawable.ic_sex_female));
				dag.cancel();
			}
		});
	}

	public void updateArea(){
		final AlertDialog.Builder builder = new AlertDialog.Builder(ActContentByDefinedView.this);
		builder.setTitle("信息更新");
		builder.setView(small);
		final AlertDialog dag = builder.create();
		dag.show();//需要先显示才能查找控件
		tag = (TextView) findViewById(R.id.tag);
		con = (EditText) findViewById(R.id.tag_con);
		giveup = (Button) findViewById(R.id.giveup);
		save = (Button) findViewById(R.id.save);
		String tags =  tv_region_temp.getText().toString();
		tag.setText(tags);
		String cons = tv_region.getText().toString();
		con.setText(cons);
		giveup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dag.cancel();
			}
		});
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String cons2 = con.getText().toString();
				tv_sign.setText(cons2);
				dag.cancel();
			}
		});
	}

	public void updateSign(){
		final AlertDialog.Builder builder = new AlertDialog.Builder(ActContentByDefinedView.this);
		builder.setTitle("信息更新");
		builder.setView(small);
		final AlertDialog dag = builder.create();
		dag.show();//需要先显示才能查找控件
		tag = (TextView) findViewById(R.id.tag);
		con = (EditText) findViewById(R.id.tag_con);
		giveup = (Button) findViewById(R.id.giveup);
		save = (Button) findViewById(R.id.save);
		String tags =  tv_sign_temp.getText().toString();
		tag.setText(tags);
		String cons = tv_sign.getText().toString();
		con.setText(cons);
		giveup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dag.cancel();
			}
		});
		save.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String cons2 = con.getText().toString();
				tv_sign.setText(cons2);
				dag.cancel();
			}
		});
	}
}