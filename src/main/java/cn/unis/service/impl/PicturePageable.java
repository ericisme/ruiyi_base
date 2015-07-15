package cn.unis.service.impl;

import java.util.List;

import cn.unis.entity.Picture;

/**
 * PictureService自己写得findAll分页
 * @author unis
 *
 */
public class PicturePageable {
	private List<Picture> pictureList;
	private Long size;
	public List<Picture> getPictureList() {
		return pictureList;
	}
	public void setPictureList(List<Picture> pictureList) {
		this.pictureList = pictureList;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}

}
