package com.ge.inspection.ir.daoImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.inspection.ir.dao.IssueDao;
import com.ge.inspection.ir.domain.muta.InspectionMedia;
import com.ge.inspection.ir.model.IssueMarkerModel;
import com.ge.inspection.ir.repository.muta.IssueDtlRepository;

@Component("issueDaoImpl")
public class IssueDaoImpl implements IssueDao{

	@Autowired
	private IssueDtlRepository issueDtlRepository;
	
	public void addIssue(InspectionMedia inspectionMedia){
    	//inspectionMedia.setIssueDate(new Date());
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
		try {
			inspectionMedia.setInspectionDate(formatter.parse(inspectionMedia.getMediaDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		issueDtlRepository.saveAndFlush(inspectionMedia);
	}
    
    @Override
	public List<Date> getIssueDate(String inspectorId,String assetId) {
		List<Date> issueDateList=issueDtlRepository.getIssueDate(inspectorId,assetId);
		return issueDateList;
	}

	@Override
	public List<InspectionMedia>  getIssueDtls(String inspectorId,String issueDateStr,String assetId) {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
		List<InspectionMedia> inspectionDtlList=null;
		try {
			Date issueDate = formatter.parse(issueDateStr);
			inspectionDtlList=issueDtlRepository.findIssue(inspectorId,issueDate,assetId);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return inspectionDtlList;
	}

	@Override
	public List<IssueMarkerModel> getIssueMarker(String inspectorId,String inspectionDateStr) {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
		List<IssueMarkerModel> issueMarkerModelList=null;
		try {
			Date inspectionDate = formatter.parse(inspectionDateStr);
			List<InspectionMedia> inspectionDtlList=issueDtlRepository.findIssueMarker(inspectorId, inspectionDate);
			issueMarkerModelList=getIssueMarkerModel(inspectionDtlList);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return issueMarkerModelList;
	}

	private List<IssueMarkerModel> getIssueMarkerModel(List<InspectionMedia> inspectionDtlList) {
		List<IssueMarkerModel> issueMarkerList=new ArrayList<IssueMarkerModel>();
		for(InspectionMedia inspectionMedia:inspectionDtlList){
			issueMarkerList.add(new IssueMarkerModel(inspectionMedia.getBlobId()));
		}
        return issueMarkerList;	
	}


}
