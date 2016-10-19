package com.ge.inspection.ir.model;

import java.util.List;

public class IssueModel {
    private String id;
    private String title;
    private String url;
    private List<IssueInspection> issueInspection;
  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public List<IssueInspection> getIssueInspection() {
		return issueInspection;
	}
	public void setIssueInspection(List<IssueInspection> issueInspection) {
		this.issueInspection = issueInspection;
	}
  
}
