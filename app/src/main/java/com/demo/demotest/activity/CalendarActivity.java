package com.demo.demotest.activity;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;
import com.demo.demotest.util.LogSuperUtil;
import com.demo.demotest.util.TextColorUtil;
import com.demo.demotest.view.calendar.MonthDateView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by sun on 2017/8/15 17:25
 * http://blog.csdn.net/mr_dsw/article/details/48755993
 */
public class CalendarActivity extends BaseActivity {
    private ImageView iv_left;
    private ImageView iv_right;
    private TextView tv_date;
    private TextView tv_week;
    private TextView tv_today;
    private MonthDateView monthDateView;
    private TextView mTvTest;
    @Override
    protected void init() {
    }
    @Override
    protected void initView() {
        setContentView(R.layout.activity_calendar);
        setCommonTitleTitle("自定义日历");
        iv_left = (ImageView) findViewById(R.id.iv_left);
        iv_right = (ImageView) findViewById(R.id.iv_right);
        monthDateView = (MonthDateView) findViewById(R.id.monthDateView);
        tv_date = (TextView) findViewById(R.id.date_text);
        tv_week  =(TextView) findViewById(R.id.week_text);
        tv_today = (TextView) findViewById(R.id.tv_today);
        monthDateView.setTextView(tv_date,tv_week);
        mTvTest=(TextView)findViewById(R.id.tv_test_calendar);
    }
    @Override
    protected void initData() {
        List<Integer> list = new ArrayList<Integer>();
        list.add(10);
        list.add(12);
        list.add(15);
        list.add(16);
        monthDateView.setDaysHasThingList(list);
        String content="科技aa哈哈b五五bcc";
        String keyWords="aabbcc";
        mTvTest.setText(getKeyContent(content,keyWords,getResources().getColor(R.color.red)));
    }
    @Override
    protected void initListener() {
        setCommonTitleBackListener();
        monthDateView.setDateClick(new MonthDateView.DateClick() {

            @Override
            public void onClickOnDate() {
                Toast.makeText(getApplication(), "点击了：" + monthDateView.getSelDay(), Toast.LENGTH_SHORT).show();
            }
        });
        iv_left.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthDateView.onLeftClick();
            }
        });

        iv_right.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthDateView.onRightClick();
            }
        });

        tv_today.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                monthDateView.setTodayToView();
            }
        });
    }
    private SpannableStringBuilder getKeyContent(String content,String keyWords,int color)
    {
        char[] keyChar= keyWords.toCharArray();
        Set<String> charSet=new HashSet<>();
        for (int i=0;i<keyChar.length;i++)
        {
            charSet.add(String.valueOf(keyChar[i]));
        }
        Iterator<String> iterator=charSet.iterator();
        SpannableStringBuilder spannable=new SpannableStringBuilder(content);
        while (iterator.hasNext())
        {
            String key=iterator.next();
            CharacterStyle characterStyle ;
            Pattern p ;
            try
            {
                p= Pattern.compile(key);
            }
            catch (Exception e)
            {
                p= Pattern.compile("[^0-9]");
            }
            Matcher matcher = p.matcher(content);

            while (matcher.find()) {
                characterStyle = new ForegroundColorSpan(color);// 需要重复！
                int startIndex=matcher.start();
                int endIndex=matcher.end();
                spannable.setSpan(characterStyle,startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spannable;
    }
}
