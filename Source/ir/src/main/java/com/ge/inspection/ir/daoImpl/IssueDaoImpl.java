package com.ge.inspection.ir.daoImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.inspection.ir.dao.IssueDao;
import com.ge.inspection.ir.domain.muta.InspectionMedia;
import com.ge.inspection.ir.model.InspectionModel;
import com.ge.inspection.ir.model.IssueInspection;
import com.ge.inspection.ir.model.IssueMarkerModel;
import com.ge.inspection.ir.repository.muta.IssueDtlRepository;
import com.ge.inspection.ir.util.JSONUtil;

@Component("issueDaoImpl")
public class IssueDaoImpl implements IssueDao{

	@Autowired
	private IssueDtlRepository issueDtlRepository;
	@Autowired
	private InspectionDaoImpl inspectionDaoImpl;
	
	public void addIssue(List<InspectionMedia> inspectionMediaList){
		/*
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
		try {
			inspectionMedia.setInspectionDate(formatter.parse(inspectionMedia.getMediaDate()));
		} catch (ParseException e) {
			e.printStackTrace();
		}*/
		for(InspectionMedia inspectionMedia:inspectionMediaList){
			issueDtlRepository.saveAndFlush(inspectionMedia);
		}
	
	}
    
    @Override
	public List<IssueInspection> getIssueDate(String inspectorId,String assetId) {
		List<InspectionMedia> issueDateList=issueDtlRepository.getIssueDate(inspectorId,assetId);
		List<IssueInspection> issueInspectionList =getIssueInspection(issueDateList,inspectorId,assetId);
		return issueInspectionList;
	}

    
    private List<IssueInspection> getIssueInspection(List<InspectionMedia> issueDateList,String inspectorId,String assetId){
    	Set<String> inspectionIds =new HashSet<String>();
    	List<InspectionModel> inspectionModelList=inspectionDaoImpl.getMediaDate(inspectorId,assetId);
    	
		/*
		if(issueDate.size()>0){
			String issueDateStr= df.format(issueDate.get(0));
			List<InspectionMedia> inspectionMediaList= issueDaoImpl.getIssueDtls(inspectorId,issueDateStr,assetId);
			IssueModel[] issueModelArray=getIssueJson(assetList,issueDate,inspectionMediaList,assetIndex);
			issueDateJson=JSONUtil.toJson(issueModelArray);
		}*/
		for(InspectionMedia inspectionMedia:issueDateList){
			inspectionIds.add(inspectionMedia.getInspectionId());
		}
		
		List<IssueInspection> issueInspectionList=new ArrayList<IssueInspection>();
		for(String inspectionId:inspectionIds){
			if(inspectionId.equals(inspectionModelList.get(0).getInspectionId())){
				IssueInspection issueInspection=new IssueInspection("my-issues",inspectionModelList.get(0).getDate() , inspectionModelList.get(0).getDuration(), null, inspectionId);
				issueInspectionList.add(issueInspection);
			}
		}
		
		
		return issueInspectionList;
    }
    
	@Override
	public List<InspectionMedia>  getIssueDtls(String inspectorId,String inspectionId,String assetId) {
		List<InspectionMedia> inspectionDtlList=null;
		inspectionDtlList=issueDtlRepository.findIssue(inspectorId,assetId,inspectionId);
		return inspectionDtlList;
	}

	@Override
	public List<IssueMarkerModel> getIssueMarker(String inspectorId) {
		List<IssueMarkerModel> issueMarkerModelList=null;
		List<InspectionMedia> inspectionDtlList=issueDtlRepository.findIssueMarker(inspectorId);
		issueMarkerModelList=getIssueMarkerModel(inspectionDtlList);
		return issueMarkerModelList;
	}

	private List<IssueMarkerModel> getIssueMarkerModel(List<InspectionMedia> inspectionDtlList) {
		List<IssueMarkerModel> issueMarkerList=new ArrayList<IssueMarkerModel>();
		for(InspectionMedia inspectionMedia:inspectionDtlList){
			Object annotatedObject=JSONUtil.toObject(inspectionMedia.getAnnotatedMetadata(), Object.class);
			List<Object> commentList=new ArrayList<Object>();
			List<Object> descList=new ArrayList<Object>();
			if(inspectionMedia.getComment()!=null && inspectionMedia.getComment().trim().length()>0){
				commentList=(List<Object>) JSONUtil.toObject(inspectionMedia.getComment(),List.class);
			}
			if(inspectionMedia.getDescription()!=null && inspectionMedia.getDescription().trim().length()>0){
				 descList=(List<Object>) JSONUtil.toObject(inspectionMedia.getDescription(),List.class);
			}
			
			File file=new File(inspectionMedia.getBlobId());
			String id=file.getName().split("\\.")[0];
			IssueMarkerModel issueModel=new IssueMarkerModel(id, "/Polymer/images/marker.png", "issue-marker", null,annotatedObject, commentList, inspectionMedia.getDefectType(), inspectionMedia.getStatusType(),descList);
		
			issueMarkerList.add(issueModel);
		}
        return issueMarkerList;	
	}

	@Override
	public void updateIssue(InspectionMedia inspectionMedia) {
		issueDtlRepository.updateIssue(inspectionMedia.getBlobId(),inspectionMedia.getStatusType());
	}

	@Override
	public void addUpdateIssue(List<InspectionMedia> inspectionMediaList) {
		for(InspectionMedia inspectionMedia:inspectionMediaList){
			if(issueDtlRepository.findOne(inspectionMedia.getBlobId())!=null){
				if(inspectionMedia.getDefectType()!=null && inspectionMedia.getDefectType().trim().length()>0){
					issueDtlRepository.updateIssueType(inspectionMedia.getBlobId(), inspectionMedia.getStatusType(), inspectionMedia.getInspectorId(), inspectionMedia.getDefectType(), inspectionMedia.getInspectionId(), inspectionMedia.getAssetId(), new Date());		
				}else{
					issueDtlRepository.updateComments(inspectionMedia.getBlobId(), inspectionMedia.getInspectorId(), inspectionMedia.getComment(), inspectionMedia.getInspectionId(), inspectionMedia.getAssetId(), new Date());		
				}
				
			}else{
				issueDtlRepository.saveAndFlush(inspectionMedia);
			}
		}
		
	}
	
	public List<Object[]> getIssueCount(String inspectorId){
		return issueDtlRepository.getIssueCount(inspectorId);
	}


}
