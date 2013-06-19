package com.me.news;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.me.news.util.DensityUtil;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

public class MainActivity extends Activity {

	public final int COLUMNWIDTHPX = 195;
	public final int FLINGVELOCITYPX =800; //滚动距离
	
	public int mColumnWidthDip;
	public int mFlingVelocityDip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//把px转换成dip
		mColumnWidthDip = DensityUtil.px2dip(this, COLUMNWIDTHPX);
		mFlingVelocityDip = DensityUtil.px2dip(this, FLINGVELOCITYPX);
		
		//获取新闻分类
		String[] categoryArray = getResources().getStringArray(R.array.categories);
		//把新闻分类保存到List中
		List<HashMap<String, String>> categories = new ArrayList<HashMap<String,String>>();
		for(int i=0;i<categoryArray.length;i++)
		{
			HashMap<String, String> hashMap = new HashMap<String, String>();
			hashMap.put("category_title", categoryArray[i]);
			categories.add(hashMap);
		}
		//创建Adapter，指明映射字段
		SimpleAdapter categoryAdapter = new SimpleAdapter(this, categories, R.layout.category_title, new String[]{"category_title"}, new int[]{R.id.category_title});
		
		GridView category = new GridView(this);
		category.setColumnWidth(mColumnWidthDip);//每个单元格宽度
		category.setNumColumns(GridView.AUTO_FIT);//单元格数目
		category.setGravity(Gravity.CENTER);//设置对其方式
		//设置单元格选择是背景色为透明，这样选择时就不现实黄色北京
		category.setSelector(new ColorDrawable(Color.TRANSPARENT));
		//根据单元格宽度和数目计算总宽度
		int width = mColumnWidthDip * categories.size();
		LayoutParams params = new LayoutParams(width, LayoutParams.FILL_PARENT);
		//更新category宽度和高度，这样category在一行显示
		category.setLayoutParams(params);
		//设置适配器
		category.setAdapter(categoryAdapter);
		//把category加入到容器中
		LinearLayout categoryList = (LinearLayout) findViewById(R.id.category_layout);
		categoryList.addView(category);
		
		// 箭头
		final HorizontalScrollView categoryScrollview = (HorizontalScrollView) findViewById(R.id.category_scrollview);
		Button categoryArrowRight = (Button) findViewById(R.id.category_arrow_right);
		categoryArrowRight.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				categoryScrollview.fling(DensityUtil.px2dip(MainActivity.this, mFlingVelocityDip));
			}
		});
		
	}

	

}
