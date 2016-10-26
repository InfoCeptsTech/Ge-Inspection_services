package com.ge.inspection.ir.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class IssueMarkerModel {
    private String id;
    private String issuePath;
    @JsonProperty("class")
    private String cssClass;
    private MetadataModel metaData;
    private Object annotation;
    private String comments;
    private String defectType;
    private String statusType;
    private String inspectorId;
    private String assetId;
    private String inspectionId;
    private String description;
    
  
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getIssuePath() {
		return issuePath;
	}
	public void setIssuePath(String issuePath) {
		this.issuePath = issuePath;
	}
	public MetadataModel getMetaData() {
		return metaData;
	}
	public void setMetaData(MetadataModel metaData) {
		this.metaData = metaData;
	}
	public String getCssClass() {
		return cssClass;
	}
	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}
	public Object getAnnotation() {
		return annotation;
	}
	public void setAnnotation(Object annotation) {
		this.annotation = annotation;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getDefectType() {
		return defectType;
	}
	public void setDefectType(String defectType) {
		this.defectType = defectType;
	}
	public String getStatusType() {
		return statusType;
	}
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	public String getInspectorId() {
		return inspectorId;
	}
	public void setInspectorId(String inspectorId) {
		this.inspectorId = inspectorId;
	}
	public String getAssetId() {
		return assetId;
	}
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}
	public String getInspectionId() {
		return inspectionId;
	}
	public void setInspectionId(String inspectionId) {
		this.inspectionId = inspectionId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public IssueMarkerModel(String id, String issuePath, String cssClass,
			MetadataModel metaData, Object annotation, String comments,
			String defectType, String statusType, String description) {
		super();
		this.id = id;
		this.issuePath = issuePath;
		this.cssClass = cssClass;
		this.metaData = metaData;
		this.annotation = annotation;
		this.comments = comments;
		this.defectType = defectType;
		this.statusType = statusType;
		this.description = description;
	}
	
}
