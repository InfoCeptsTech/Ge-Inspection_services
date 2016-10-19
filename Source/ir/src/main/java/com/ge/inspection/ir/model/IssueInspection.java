package com.ge.inspection.ir.model;

import java.util.Set;

public class IssueInspection {
  private String url;
  private String date;
  private String duration;
  private Set<IssueDtlModel> issueDtlModel;
  
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getDuration() {
	return duration;
}
public void setDuration(String duration) {
	this.duration = duration;
}
public Set<IssueDtlModel> getIssueDtlModel() {
	return issueDtlModel;
}
public void setIssueDtlModel(Set<IssueDtlModel> issueDtlModel) {
	this.issueDtlModel = issueDtlModel;
}
  
  
}
