package com.demo.demotest.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.demo.demotest.R;
import com.demo.demotest.domain.FileEntity;
import com.demo.demotest.domain.ImgEntity;
import com.demo.demotest.util.LogSuperUtil;
import com.demo.demotest.util.SysConstant;

/**
 * 
 * 项目名称: DemoBaseTest<br/>
 * 类名称: ExpandableListViewAdapter<br/>
 * 描述: 可扩展列表的ListView的适配器
 *
 * @author:yefx
 * @Date:2016-2-18下午3:06:07
 */
public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
	private Context m_context;
	private List<ImgEntity> m_groupList;
	public ExpandableListViewAdapter(Context context,List<ImgEntity> groupList)
	{
		m_context=context;
		m_groupList=groupList;
	}
	@Override
	public int getGroupCount() {
		return m_groupList.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return m_groupList.get(groupPosition).fileList.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return m_groupList.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return m_groupList.get(groupPosition).fileList.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		ViewHolderGroup vhGroup;
		if(convertView==null)
		{
			vhGroup=new ViewHolderGroup();
			convertView=View.inflate(m_context, R.layout.item_group_exlistview, null);
			vhGroup.tvId=(TextView) convertView.findViewById(R.id.tv_id_item_group_expandable_listview);
			vhGroup.tvImgName=(TextView) convertView.findViewById(R.id.tv_img_name_item_group_expandable_listview);
			convertView.setTag(vhGroup);
		}
		else
		{
			vhGroup=(ViewHolderGroup) convertView.getTag();
		}
		vhGroup.tvId.setText(m_groupList.get(groupPosition).productid);
		vhGroup.tvImgName.setText(m_groupList.get(groupPosition).imageName);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		ViewHolderChild vhChild;
		if(convertView==null)
		{
			vhChild=new ViewHolderChild();
			convertView=View.inflate(m_context, R.layout.item_child_exlistview, null);
			vhChild.tvId=(TextView) convertView.findViewById(R.id.tv_id_item_child_expandable_listview);
			vhChild.tvFileName=(TextView) convertView.findViewById(R.id.tv_file_name_item_child_expandable_listview);
			convertView.setTag(vhChild);
		}
		else
		{
			vhChild=(ViewHolderChild) convertView.getTag();
		}
		FileEntity fileEntity=m_groupList.get(groupPosition).fileList.get(childPosition);
		LogSuperUtil.i(SysConstant.Log.element, "fileEntity="+fileEntity.toString());
		vhChild.tvId.setText(fileEntity.id);
		vhChild.tvFileName.setText(fileEntity.fileName);
		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	class ViewHolderGroup
	{
		TextView tvId;
		TextView tvImgName;
	}
	class ViewHolderChild
	{
		TextView tvId;
		TextView tvFileName;
	}
}
