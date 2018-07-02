package com.bccns.umsserviceweb.ums.vo.PDS;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Code {
	@XmlAttribute
	private String group;
	@XmlAttribute
	private String id;
	@XmlAttribute
	private String code;
	@XmlAttribute
	private String message;
	
	@XmlElement
	private List<String> source;
	
	public String getGroup() {
		return group;
	}
	public String getId() {
		return id;
	}
	public String getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	public List<String> getSource() {
		return source;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Code [group=");
		builder.append(group);
		builder.append(", id=");
		builder.append(id);
		builder.append(", code=");
		builder.append(code);
		builder.append(", message=");
		builder.append(message);
		builder.append(", source=");
		builder.append(source);
		builder.append("]");
		return builder.toString();
	}
}
