package org.zjh.web.task.vo;

import java.util.List;

public class QueryParams {

	public Float score;
	public String code;
	public List<String> codes;
	
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<String> getCodes() {
		return codes;
	}
	public void setCodes(List<String> codes) {
		this.codes = codes;
	}
	
}
