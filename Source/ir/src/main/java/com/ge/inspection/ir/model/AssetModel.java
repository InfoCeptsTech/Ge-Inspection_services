package com.ge.inspection.ir.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class AssetModel {
    private String title;
    private String url="my-assets";
    private List<InspectionModel> inspection;
    
	public AssetModel( String title,List<InspectionModel> inspection) {
		this.title = title;
		this.inspection = inspection;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<InspectionModel> getInspection() {
		return inspection;
	}
	public void setInspection(List<InspectionModel> inspection) {
		this.inspection = inspection;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
  
}
