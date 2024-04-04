package com.genpact.onlineShoppingApp.entity;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("serial")
public class FavoriteId implements Serializable{
	private Integer pid;
	private Integer cid;
	
	public FavoriteId() {
		
	}
	
	public FavoriteId(Integer pid, Integer cid) {
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
		if (this.getClass() != obj.getClass())
			return false;
		FavoriteId other = (FavoriteId) obj;
		return Objects.equals(cid, other.cid) && Objects.equals(pid, other.pid);
	}
	
}
