package com.ge.inspection.ir.dao;

import java.util.List;

import com.ge.inspection.ir.domain.muta.InspectionMedia;
import com.ge.inspection.ir.model.IssueInspection;
import com.ge.inspection.ir.model.IssueMarkerModel;

public interface IssueDao {
  public List<InspectionMedia>  getIssueDtls(String inspectorId,String inspectionId,String assetId);
  public void addIssue(List<InspectionMedia> inspectionMediaList);
  public void addUpdateIssue(List<InspectionMedia> inspectionMediaList);
  public void updateIssue(InspectionMedia inspectionMedia);
  public List<IssueInspection> getIssueDate(String inspectorId,String assetId);
  public List<IssueMarkerModel>  getIssueMarker(String inspectorId);
}
