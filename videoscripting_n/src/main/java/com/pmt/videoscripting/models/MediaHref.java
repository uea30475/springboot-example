package com.pmt.videoscripting.models;

import java.util.ArrayList;
import java.util.List;

public class MediaHref {
	
	private String code;
	private List<String> listVttFile = new ArrayList<String>();
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<String> getListVttFile() {
		return listVttFile;
	}
	public void setListVttFile(List<String> listVttFile) {
		this.listVttFile = listVttFile;
	}
	
	public void addListVttFile(String vttFilename) {
		this.listVttFile.add(vttFilename);
	}


}
