package com.ge.inspection.ir.dao;

import java.util.Date;
import java.util.List;

import com.ge.inspection.ir.domain.muta.InspectionMedia;
import com.ge.inspection.ir.model.IssueMarkerModel;

public interface IssueDao {
  public List<InspectionMedia>  getIssueDtls(String inspectorId,String issueDate,String assetId);
  public void addIssue(InspectionMedia inspectionMedia);
  public List<Date> getIssueDate(String inspectorId,String assetId);
  public List<IssueMarkerModel>  getIssueMarker(String inspectorId,String issueDate);
}
