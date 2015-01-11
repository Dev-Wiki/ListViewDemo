package com.asia.listviewdemo;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends BaseAdapter{
	
	private List<ListItem> dataList;
	private Context context;
	private ListView listView;
	
	public MyAdapter(Context context, List<ListItem> dataList) {
		this.dataList = dataList;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		int count = 0 ;
		//防止空指针
		if (dataList != null) {
			count = dataList.size();
		}
		return count;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dataList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		Holder holder ;
		ButtonClick click;
		DLog.d("调用getView" );
		if (convertView == null) {
			holder = new Holder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.list_view_item, parent,false);
			click = new ButtonClick(position);
			holder.button = (Button) convertView.findViewById(R.id.button);
			holder.button.setOnClickListener(click);
			holder.textView = (TextView) convertView.findViewById(R.id.text_view);
			holder.progressBar = (ProgressBar) convertView.findViewById(R.id.progress);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		
		ListItem item = dataList.get(position);
		Log.d("----------", item.getProcess() + "");
		holder.progressBar.setProgress(item.getProcess());
		holder.textView.setText(item.getText());
		
		if (item.isHidden()) {
			holder.textView.setVisibility(View.GONE);
		} else {
			holder.textView.setVisibility(View.VISIBLE);
		}
		
		return convertView;
	}
	
	
	/**
	 * 缓存视图
	 * @author Asia
	 *
	 */
	public class Holder{
		public ProgressBar progressBar;
		public TextView textView;
		public Button button;
	}
	
	
	/**
	 * 设置需要Adapter更新数据的ListView
	 * @param listView
	 */
	public void setListView(ListView listView){
		this.listView = listView;
	}
	
	
	/**
	 * 更新指定的item
	 * @param position
	 */
	public void refreshItem(int position){
		DLog.d("更新界面" );
		int first = listView.getFirstVisiblePosition();
		View view = listView.getChildAt(position - first);
		getView(position, view, listView);
	}
	
	
	/**
	 * 按钮的点击事件
	 * @author Asia
	 * 
	 */
	public class ButtonClick implements OnClickListener{
		/**
		 * 记录按钮的位置
		 */
		private int position;
		
		public ButtonClick(int position) {
			this.position = position;
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//这里和itemClick方法内容一致,为了区分外部点击和按钮点击,故没调用同一个方法
			int process = dataList.get(position).getProcess();
			if (process > 0 && process < 100) {
				Toast.makeText(context, "任务正在进行", Toast.LENGTH_SHORT).show();
			} else if (process == 0  ||  process == 100) {
				changeData(position);
			}
		}
	}
	
	
	/**
	 * 响应ListView的点击事件
	 * @param position
	 */
	public void itemClick(int position){
		DLog.d("点击了:" + position );
		int process = dataList.get(position).getProcess();
		if (process > 0 && process < 100) {
			Toast.makeText(context, "任务正在进行", Toast.LENGTH_SHORT).show();
		} else if (process == 0  ||  process == 100) {
			changeData(position);
		}
	}
	
	private final int WHAT_REFRESH = 0 ;
	@SuppressLint("HandlerLeak") 
	/**
	 * 处理视图更新
	 */
	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case WHAT_REFRESH:
				DLog.d("接收到消息" );
				refreshItem(msg.arg1);
				break;
			default:
				break;
			}
		};
	};
	
	
	/**
	 * 更改数据
	 * @param position
	 */
	public void changeData(final int position){
		DLog.d("改变数据" );
		new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				ListItem tempItem = dataList.get(position);
				tempItem.setHidden(true);
				for (int i = 0; i < 101; i++) {
					tempItem.setProcess(i);
					dataList.set(position, tempItem);
					Message message = new Message();
					message.arg1 = position;
					message.what = WHAT_REFRESH;
					DLog.d("发送消息" );
					handler.sendMessage(message);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

}
