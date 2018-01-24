package com.demo.demotest.domain;

import java.io.Serializable;


public class NewsEntity implements Serializable {
	public String title;
	public String content;
	@Override
	public String toString() {
		return "NewsEntity{" +
				"title='" + title + '\'' +
				", content='" + content + '\'' +
				'}';
	}
}
