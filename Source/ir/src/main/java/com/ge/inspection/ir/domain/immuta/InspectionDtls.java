package com.ge.inspection.ir.domain.immuta;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
public class InspectionDtls {
  @Id
  @Column(updatable = false, nullable = false)
  @Size(min = 0, max = 50)	
  private String blobId;
  private String visibility;
  private String facilityId;
  private String assetId;
  private String inspectionId;
  private String inspectorId;
  private String inspectionType;
  
  @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date inspectionStart;
  
  
  @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date inspectionStop;
  
  @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
  @Temporal(TemporalType.TIMESTAMP)
  private Date timestamp;
  
  private String inspectionPhaseId;
  private int requiredResolution;
  private int requiredFieldOfView;
  private String sensor_name;
  private String sensor_sensorType;
  private String sensor_sensorMode;
  private String inspectionPointId;
  
  
public String getBlobId() {
	return blobId;
}
public void setBlobId(String blobId) {
	this.blobId = blobId;
}
public String getVisibility() {
	return visibility;
}
public void setVisibility(String visibility) {
	this.visibility = visibility;
}
public String getFacilityId() {
	return facilityId;
}
public void setFacilityId(String facilityId) {
	this.facilityId = facilityId;
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
public String getInspectorId() {
	return inspectorId;
}
public void setInspectorId(String inspectorId) {
	this.inspectorId = inspectorId;
}
public String getInspectionType() {
	return inspectionType;
}
public void setInspectionType(String inspectionType) {
	this.inspectionType = inspectionType;
}

public Date getInspectionStart() {
	return inspectionStart;
}
public void setInspectionStart(Date inspectionStart) {
	this.inspectionStart = inspectionStart;
}
public Date getInspectionStop() {
	return inspectionStop;
}
public void setInspectionStop(Date inspectionStop) {
	this.inspectionStop = inspectionStop;
}
public Date getTimestamp() {
	return timestamp;
}
public void setTimestamp(Date timestamp) {
	this.timestamp = timestamp;
}
public String getInspectionPhaseId() {
	return inspectionPhaseId;
}
public void setInspectionPhaseId(String inspectionPhaseId) {
	this.inspectionPhaseId = inspectionPhaseId;
}
public int getRequiredResolution() {
	return requiredResolution;
}
public void setRequiredResolution(int requiredResolution) {
	this.requiredResolution = requiredResolution;
}
public int getRequiredFieldOfView() {
	return requiredFieldOfView;
}
public void setRequiredFieldOfView(int requiredFieldOfView) {
	this.requiredFieldOfView = requiredFieldOfView;
}
public String getSensor_name() {
	return sensor_name;
}
public void setSensor_name(String sensor_name) {
	this.sensor_name = sensor_name;
}
public String getSensor_sensorType() {
	return sensor_sensorType;
}
public void setSensor_sensorType(String sensor_sensorType) {
	this.sensor_sensorType = sensor_sensorType;
}
public String getSensor_sensorMode() {
	return sensor_sensorMode;
}
public void setSensor_sensorMode(String sensor_sensorMode) {
	this.sensor_sensorMode = sensor_sensorMode;
}
public String getInspectionPointId() {
	return inspectionPointId;
}
public void setInspectionPointId(String inspectionPointId) {
	this.inspectionPointId = inspectionPointId;
}
  
  

}
