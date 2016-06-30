package de.starwit.ljprojectbuilder.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum ResponseCode {
	OK("200"), EMPTY("204"), NOT_FOUND("404"), NOT_VALID("451"), ERROR("500"), NOT_DELETE("205"), NOT_IMPLEMENTED("501");
	
	private String msgCode;
	
	ResponseCode(String msgCode) {
		this.msgCode = msgCode;
	}
	public String getMsgCode() {
		return msgCode;
	}

	@Override
	public java.lang.String toString() {
		return msgCode;
	}
	
}
