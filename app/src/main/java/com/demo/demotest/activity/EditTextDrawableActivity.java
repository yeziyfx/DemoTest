package com.demo.demotest.activity;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;

/**
 * Created by sun on 2017/12/6 10:16
 * EditText的图片
 */
public class EditTextDrawableActivity extends BaseActivity {
    private EditText mEtSearch;
    private ImageView mIvEmpty;
    @Override
    protected void init() {
        setContentView(R.layout.activity_edittext_drawable);
    }
    @Override
    protected void initView() {
        setCommonTitleTitle("EditText的图片");
        mEtSearch= (EditText) findViewById(R.id.et_edittext_drawable);
        final Drawable drawableSearch=getResources().getDrawable(R.drawable.search_cnki_articles);
        drawableSearch.setBounds(0,0,drawableSearch.getMinimumWidth(),drawableSearch.getMinimumHeight());
        mEtSearch.setCompoundDrawables(drawableSearch,null,null,null);
        mEtSearch.setCompoundDrawablePadding(10);
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count!=0)
                {
                    mEtSearch.setCompoundDrawables(null,null,null,null);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s))
                {
                    mEtSearch.setCompoundDrawables(drawableSearch,null,null,null);
                }
            }
        });
        mIvEmpty = (ImageView) findViewById(R.id.iv_empty_edittext_drawable);
    }
    @Override
    protected void initData() {
    }
    @Override
    protected void initListener() {
        setCommonTitleBackListener();
    }
}
