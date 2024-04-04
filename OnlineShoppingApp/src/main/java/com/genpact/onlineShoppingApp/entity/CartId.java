package com.genpact.onlineShoppingApp.entity;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class CartId implements Serializable{
	private Integer pid;
	private Integer cid;
	
	public CartId() {
		
	}
	
	public CartId(Integer pid, Integer cid) {
		super();
		this.pid = pid;
		this.cid = cid;
	}
	
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}

	@Override
	public int hashCode() {
		return Objects.hash(cid, pid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartId other = (CartId) obj;
		return Objects.equals(cid, other.cid) && Objects.equals(pid, other.pid);
	}
	
}
