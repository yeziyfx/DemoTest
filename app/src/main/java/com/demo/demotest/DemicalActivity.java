package com.demo.demotest;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.demo.demotest.R;
import com.demo.demotest.base.BaseActivity;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: DemicalActivity<br/>
 * 描述: 测试小数有关的
 * 
 * @author:yefx
 * @Date:2015-5-14下午8:38:02
 */
public class DemicalActivity extends BaseActivity implements OnClickListener {

	private EditText m_etInput;
	private Button m_btnGetDouble;
	private Button m_btnGetDoubleDigit;
	private Button m_btnClear;
	private EditText m_etDigit;
	private int m_digit;
	@Override
	protected void init() {
		setContentView(R.layout.activity_decimal);
	}

	@Override
	protected void initView() {
		m_etInput = (EditText) findViewById(R.id.et_input);
		m_btnGetDouble = (Button) findViewById(R.id.btn_get_double);
		m_btnGetDoubleDigit = (Button) findViewById(R.id.btn_get_double_digit);
		m_btnClear = (Button) findViewById(R.id.btn_clear);
		m_etDigit = (EditText) findViewById(R.id.et_digit);
	}

	@Override
	protected void initData() {
		// m_etInput.setText(getDecimalFormat(34.21, 6));
		float f = 1.12345678f;
		// m_etInput.setText(f+"");
		m_digit = getTextIntValue(m_etDigit);
	}

	@Override
	protected void initListener() {
		m_btnGetDouble.setOnClickListener(this);
		m_btnGetDoubleDigit.setOnClickListener(this);
		m_btnClear.setOnClickListener(this);
	}

	/**
	 * 
	 * 功能:将double类型数据乘以0.01(暂不用)
	 * 
	 * @param d
	 * @author: yefx
	 * @date:2015-5-14下午8:44:06
	 */
	private double getDouble(double d) {
		return d * 0.01;
	}

	@Override
	public void onClick(View v) {
		
		switch (v.getId()) {
		case R.id.btn_get_double:
			m_etInput.setText(getTextDoubleValue(m_etInput) + "");
			break;
		case R.id.btn_clear:
			m_etInput.setText("");
			break;
		case R.id.btn_get_double_digit:
			m_etInput.setText("" + formatDoubleDigit(getTextDoubleValue(m_etInput), 3));
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * 功能:double类型数据小数点后保留digit位成字符串
	 * 
	 * @param db
	 * @param digit
	 * @return 字符串形式的数字
	 * @author: yefx
	 * @date:2015-5-14下午8:41:16
	 */
	public String formatDecimalToString(double db, int digit) {
		if (digit <= 0) {
			return null;
		}
		String str = "";
		for (int i = 0; i < digit; i++) {
			str += "0";
		}
		str = "0." + str;
		//
		DecimalFormat format = new DecimalFormat(str);
		return format.format(db);
	}

	/**
	 * 将double类型数据保留指定位数小数
	 * 
	 * @param d
	 * @param digit
	 * @return
	 */
	private double formatDoubleDigit(double d, int digit) {
		double result = 0;
		// DecimalFormat dFormat=new DecimalFormat("######.00");
		// result=ParseBaseTypeUtil.parseToDouble(dFormat.format(getTextDoubleValue(m_etInput)));
		BigDecimal bd = new BigDecimal(d);
		result = bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		showToast("d=" + d + ",result=" + result);
		return result;
	}
}
