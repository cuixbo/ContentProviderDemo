package demo.cxb.com.app_client;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button mBtnQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mBtnQuery = (Button) findViewById(R.id.btn_query);
        mBtnQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContentFromContentProvider();
            }
        });
    }

    private void getContentFromContentProvider() {
        Uri uri = Uri.parse("content://com.cxb.ipc/person");    //ContentProvider 中注册的 URI
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", System.currentTimeMillis());
        contentValues.put("name", "rourou" + System.currentTimeMillis());
        contentValues.put("description", "beautiful girl");
        ContentResolver contentResolver = getContentResolver();    //获取内容处理器
        contentResolver.insert(uri, contentValues);    //插入一条数据
        //再查询一次
        Cursor cursor = contentResolver.query(uri, new String[]{"name", "description"}, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            StringBuilder cursorResult = new StringBuilder("DB 查询结果：");
            while (cursor.moveToNext()) {
                String result = cursor.getString(0) + ", " + cursor.getString(1);
                cursorResult.append("\n").append(result);
            }
            Log.d("xbc", "DB 查询结果：" + cursorResult);
        }
        if (cursor != null) {
            cursor.close();
        }
    }

}
