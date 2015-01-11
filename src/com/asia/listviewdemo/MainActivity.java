package com.asia.listviewdemo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;


public class MainActivity extends Activity {

	private ListView listView ;
	private List<ListItem> dataList;
	private MyAdapter adapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
    }
    
    
    /**
     * 初始化视图
     */
    private void initView(){
    	listView = (ListView) super.findViewById(R.id.list_view);
    	createTestData();
    	adapter = new MyAdapter(this, dataList);
    	listView.setAdapter(adapter);
    	adapter.setListView(listView);
    	//点击item事件,也可点击按钮.
    	listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//响应点击事件
				adapter.itemClick(position);
			}
		});
    }
    
    
    /**
     * 创建测试数据
     */
    private void createTestData(){
    	dataList = new ArrayList<ListItem>();
    	for (int i = 0; i < 100; i++) {
			ListItem item = new ListItem();
			item.setProcess(0);
			item.setText("这是第" + i + "条.");
			item.setHidden(false);
			dataList.add(item);
		}
    }
}
