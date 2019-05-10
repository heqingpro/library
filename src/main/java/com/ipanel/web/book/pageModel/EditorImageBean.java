package com.ipanel.web.book.pageModel;

/**
 * 图书中插入的图片
 * @author  fangg
 * @version  1.0.0
 * @2017年6月10日 下午2:02:08
 */
public class EditorImageBean {
	private String title;//图片真实名称
	private String alt;//图片原始名称
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alt == null) ? 0 : alt.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		EditorImageBean other = (EditorImageBean) obj;
		if (alt == null) {
			if (other.alt != null) {
				return false;
			}
		} else if (!alt.equals(other.alt)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}
	
}

