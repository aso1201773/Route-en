package jp.ac.asojuku.route_en;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Toko_Henshu extends Activity implements View.OnClickListener {

	String text1;
	String text2;
	String text3;
	String text4;

	private Button btn = null;
	private TextView tv = null;

	private Spinner _spinner = null;
	KeyValuePair item;

	//画像用
	private static final int REQUEST_GALLERY1 = 1;
	private static final int REQUEST_GALLERY2 = 2;
	private static final int REQUEST_GALLERY3 = 3;
	private ImageView imageView1;
	private ImageView imageView2;
	private ImageView imageView3;
	//private Button btn1;
	public Uri uri;
	public InputStream in;
	public Bitmap img;
	private final int TARGET_WIDTH = 100;
	private final int TARGET_HEIGHT = 100;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自動生成されたメソッド・スタブ
		super.onCreate(savedInstanceState);
		setContentView(R.layout.toko_henshu);

		// button4登録
		Button button4 = (Button)findViewById(R.id.button4);
		button4.setOnClickListener(this);

		btn = (Button)findViewById(R.id.btn1);
		tv = (TextView)findViewById(R.id.tv1);

		btn.setOnClickListener(this);

		Button button1 = (Button)findViewById(R.id.button1);
		button1.setOnClickListener(this);
		imageView1 = (ImageView)findViewById(R.id.imageView1);
		Button button2 = (Button)findViewById(R.id.button2);
		button2.setOnClickListener(this);
		imageView2 = (ImageView)findViewById(R.id.imageView2);
		Button button3 = (Button)findViewById(R.id.button3);
		button3.setOnClickListener(this);
		imageView3 = (ImageView)findViewById(R.id.imageView3);
		ImageButton imageButton1 = (ImageButton)findViewById(R.id.imageButton1);
		imageButton1.setOnClickListener(this);


		// editText1
		EditText editText1 = (EditText)findViewById(R.id.editText1);
		SpannableStringBuilder sb1 = (SpannableStringBuilder)editText1.getText();
		text1 = sb1.toString();
		// editText2
		EditText editText2 = (EditText)findViewById(R.id.editText2);
		SpannableStringBuilder sb2 = (SpannableStringBuilder)editText2.getText();
		text2 = sb2.toString();
		// editText3
		EditText editText3 = (EditText)findViewById(R.id.editText3);
		SpannableStringBuilder sb3 = (SpannableStringBuilder)editText3.getText();
		text3 = sb3.toString();
		// editText4
		EditText editText4 = (EditText)findViewById(R.id.editText4);
		SpannableStringBuilder sb4 = (SpannableStringBuilder)editText4.getText();
		text4 = sb4.toString();

		/*
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// アイテムを追加します
		adapter1.add("福岡県");
		adapter1.add("佐賀県");
		adapter1.add("長崎県");
		Spinner spinner1 = (Spinner)findViewById(R.id.spinner1);
		// アダプターを設定します
		spinner1.setAdapter(adapter1);
		 */

		//スピナー
		_spinner =  (Spinner)this.findViewById(R.id.spinner1);
		_spinner.setOnItemSelectedListener(Spinner1_OnItemSelectedListener);
		//スピナーのドロップダウンアイテムを設定
		List<KeyValuePair> list = getSpinnerData();
		KeyValuePairArrayAdapter adapter = new KeyValuePairArrayAdapter(this, android.R.layout.simple_spinner_item, list);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		_spinner.setAdapter(adapter);
		//キーが2の値を選択する
		// Integer selectKey = 2;
		//_spinner.setSelection(adapter.getPosition(selectKey));

		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// アイテムを追加します
		adapter2.add("デート");
		adapter2.add("一人で");
		adapter2.add("家族で");
		adapter2.add("旅行");
		adapter2.add("おまかせ！");
		Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
		// アダプターを設定します
		spinner2.setAdapter(adapter2);

	}

	@Override
	public void onClick(View v) {
		// TODO 自動生成されたメソッド・スタブ
		switch(v.getId()){
		case R.id.button4:
			doPost("http://toenp.php.xdomain.jp/test.php");
			Intent intent4 = new Intent(Toko_Henshu.this, Toko_Sentaku.class);
			startActivity(intent4);
			break;
		case R.id.btn1:
			exec_post();
			break;
		case R.id.button1:
			gallery(1);
			break;
		case R.id.button2:
			gallery(2);
			break;
		case R.id.button3:
			gallery(3);
			break;
		case R.id.imageButton1:
			Bitmap bitmap = img;
			UploadAsyncTask task = new UploadAsyncTask(this);
			task.execute(bitmap);
			break;
		}

	}

	public String doPost(String url) {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost method = new HttpPost(url);

		// リクエストパラメータの設定
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("text1", "aaaa"));
		try {
			method.setEntity(new UrlEncodedFormEntity(params, "utf-8"));
			HttpResponse response = client.execute(method);
			int status = response.getStatusLine().getStatusCode();
			return "status:" + status;
		} catch (Exception e) {
			return "Error:" + e.getMessage();
		}
	}

	// POST通信を実行（AsyncTaskによる非同期処理を使うバージョン）
	private void exec_post() {

		// 非同期タスクを定義
		HttpPostTask task = new HttpPostTask(
				this,
				"http://toenp.php.xdomain.jp/test.php",

				// タスク完了時に呼ばれるUIのハンドラ
				new HttpPostHandler(){

					@Override
					public void onPostCompleted(String response) {
						// 受信結果をUIに表示
						tv.setText( response );
					}

					@Override
					public void onPostFailed(String response) {
						tv.setText( response );
						Toast.makeText(
								getApplicationContext(),
								"エラーが発生しました。",
								Toast.LENGTH_LONG
								).show();
					}
				}
				);
		task.addPostParam( "p_id", item.getKey().toString() );
		task.addPostParam( "text2", "パスワード" );

		// タスクを開始
		task.execute();

	}

	/**
	 * @brief スピナーデータを取得します。
	 * @return
	 */
	private List<KeyValuePair> getSpinnerData(){
		List<KeyValuePair> list = new ArrayList<KeyValuePair>();
		Resources res = getResources();
		TypedArray spinner1_data = res.obtainTypedArray(R.array.spinner1_data);
		for (int i = 0; i < spinner1_data.length(); ++i) {
			int id = spinner1_data.getResourceId(i, -1);
			if (id > -1) {
				String[] item = res.getStringArray(id);
				list.add(new KeyValuePair(item[0], item[1]));
			}
		}
		spinner1_data.recycle();
		return list;
	}

	/**
	 * @brief スピナーのOnItemSelectedListener
	 */
	private OnItemSelectedListener Spinner1_OnItemSelectedListener = new OnItemSelectedListener() {
		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
			item = (KeyValuePair)_spinner.getSelectedItem();
			Toast.makeText(Toko_Henshu.this, item.getKey().toString(), Toast.LENGTH_LONG).show();
		}
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	};

	private void gallery(int num){
		// ギャラリー呼び出し
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);

		if (num == 1) {
			startActivityForResult(intent, REQUEST_GALLERY1);
		} else if (num == 2){
			startActivityForResult(intent, REQUEST_GALLERY2);
		} else {
			startActivityForResult(intent, REQUEST_GALLERY3);
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if(requestCode == REQUEST_GALLERY1 && resultCode == RESULT_OK)
		{
			try
			{
				in = getContentResolver().openInputStream(data.getData());
				img = BitmapFactory.decodeStream(in);
				img = PictureUtil.resize(img, TARGET_WIDTH, TARGET_HEIGHT);
				in.close();

				// 選択した画像を表示
				imageView1.setImageBitmap(img);
			}
			catch (Exception e)
			{
			}
		} else if(requestCode == REQUEST_GALLERY2 && resultCode == RESULT_OK){
			try
			{
				in = getContentResolver().openInputStream(data.getData());
				img = BitmapFactory.decodeStream(in);
				img = PictureUtil.resize(img, TARGET_WIDTH, TARGET_HEIGHT);
				in.close();

				// 選択した画像を表示
				imageView2.setImageBitmap(img);
			}
			catch (Exception e)
			{
			}
		} else if(requestCode == REQUEST_GALLERY3 && resultCode == RESULT_OK){
			try
			{
				in = getContentResolver().openInputStream(data.getData());
				img = BitmapFactory.decodeStream(in);
				img = PictureUtil.resize(img, TARGET_WIDTH, TARGET_HEIGHT);
				in.close();

				// 選択した画像を表示
				imageView3.setImageBitmap(img);
			}
			catch (Exception e)
			{
			}
		}
	}

}
