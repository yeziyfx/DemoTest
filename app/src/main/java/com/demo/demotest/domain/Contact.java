package com.demo.demotest.domain;
/**
 * 联系人实体
 * @author Administrator
 *
 */
public class Contact {
	public int id;
	public String name;
	/**图片的网络路径*/
	public String imageUrl;
	public Contact(int id, String name, String image) {
		super();
		this.id = id;
		this.name = name;
		this.imageUrl = image;
	}
	public Contact() {
	}
	@Override
	public String toString() {
		return "Contact [id=" + id + ", name=" + name + ", imageUrl=" + imageUrl + "]";
	}
	
}
