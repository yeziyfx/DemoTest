package com.demo.demotest.fragment;

import com.demo.demotest.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class AlwaysContactsFragment extends Fragment {
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	View contentView=inflater.inflate(R.layout.fragment_always_contacts, null);
	return contentView;
}
}
