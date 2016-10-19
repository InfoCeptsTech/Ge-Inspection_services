package com.ge.inspection.ir.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonInclude(Include.NON_NULL)
public class MediaModel {
    private String title;
    @JsonProperty("img")
    private List<ImageModel> imageModel;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<ImageModel> getImageModel() {
		return imageModel;
	}
	public void setImageModel(List<ImageModel> imageModel) {
		this.imageModel = imageModel;
	}
	public MediaModel(String title, List<ImageModel> imageModel) {
		this.title = title;
		this.imageModel = imageModel;
	}
	public MediaModel(String title) {
		super();
		this.title = title;
	}

  
   
}
