package com.bccns.umsserviceweb.common.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class Entity implements Serializable, Cloneable {
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	public Object clone() {
		Object obj = null;

		try {
			obj = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	/**
	 * 해당 객체 내의 값들을 문자열로 반환한다.
	 * 
	 * @see org.apache.commons.lang.builder.ToStringBuilder#reflectionToString(Object)
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
