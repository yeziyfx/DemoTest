package com.demo.demotest.util;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by KEYIKEYI-Android-2 on 2016/4/24 17:25
 */
public class TextColorUtil {
    /**
     * 关键字高亮显示
     *
     * @param content   需要显示的文字
     * @param key 需要高亮的关键字
     * @param color  高亮颜色值
     * @param isAll  是否需要全部高亮
     * @return spannable 处理完后的结果，记得不要toString()，否则没有效果
     * <br/><br/>
     * 调用示例:
     * <br/> SpannableStringBuilder textString = TextUtilTools.highlight(item.getItemName(), KnowledgeActivity.searchKey);
     * <br/> vHolder.tv_itemName_search.setText(textString);
     */
    public static SpannableStringBuilder highlight(String content, String key, int color, boolean isAll) {
        content= TextUtils.isEmpty(content)?"":content;
        SpannableStringBuilder spannable = new SpannableStringBuilder(content);
        if(TextUtils.isEmpty(content)|| TextUtils.isEmpty(key))
        {
            return spannable;
        }
        CharacterStyle span = null;
        Pattern p ;
        try
        {
           p= Pattern.compile(key);
        }
        catch (Exception e)
        {
            p= Pattern.compile("[^0-9]");
        }
        Matcher m = p.matcher(content);

        while (m.find()) {
            span = new ForegroundColorSpan(color);// 需要重复！
            spannable.setSpan(span, m.start(), m.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            if (isAll) {
            } else {
                break;
            }
        }
        return spannable;
    }

    /**
     * 生成部分高亮显示的文字
     * @param beforeText 颜色不做处理的文字
     * @param highLightText 需要高亮的文字
     * @param afterText 不需要高亮的文字
     * @param color 颜色值(需要时getResource().get过的)
     * @return
     */
    public static SpannableStringBuilder createHighlightText(String beforeText, String highLightText, String afterText, int color) {
        int len=beforeText.length();
        SpannableStringBuilder spannable = new SpannableStringBuilder();
        spannable.append(beforeText);
        spannable.append(highLightText);
        CharacterStyle span   = new ForegroundColorSpan(color);
        spannable.append(afterText);
        spannable.setSpan(span, len, len+highLightText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }
}
