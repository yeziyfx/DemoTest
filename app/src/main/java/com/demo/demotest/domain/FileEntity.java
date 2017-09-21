package com.demo.demotest.domain;

import java.io.Serializable;


public class FileEntity implements Serializable {
	public String id;
	public String fileName;
	@Override
	public String toString() {
		return "FileEntity [id=" + id + ", fileName=" + fileName + "]";
	}
	
}
