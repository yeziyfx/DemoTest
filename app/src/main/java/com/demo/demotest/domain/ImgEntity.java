package com.demo.demotest.domain;

import java.io.Serializable;
import java.util.List;


public class ImgEntity implements Serializable {
	public String productid;
	public String sensorid;
	public String imageName;
	public String keyWord;
	public String scoutTime;
	public List<FileEntity> fileList;
}
