package com.ge.inspection.ir.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ge.inspection.ir.dao.InspectionDao;
import com.ge.inspection.ir.dao.IssueDao;
import com.ge.inspection.ir.domain.muta.InspectionMedia;
import com.ge.inspection.ir.model.IssueDtlModel;
import com.ge.inspection.ir.model.IssueInspection;
import com.ge.inspection.ir.model.IssueMarkerModel;
import com.ge.inspection.ir.model.IssueModel;
import com.ge.inspection.ir.util.ImageUtil;
import com.ge.inspection.ir.util.JSONUtil;

@RestController
public class IssueController {
	@Autowired
	private IssueDao issueDaoImpl;
	
	@Autowired
	private InspectionDao inspectionDao;
	
	@Value("${media.temp.location}")
	private String compMediaLocation;
	
	@Value("${media.location}")
 	private String mediaLocation;
	
	
	@RequestMapping(value = "/inspection/addIssue", method = RequestMethod.POST)
	public String addIssue(@RequestBody String inspectionMedia){
		
		List<Object> reqObject=(List<Object>) JSONUtil.toObject(inspectionMedia, List.class);
		//issueDaoImpl.addIssue(inspectionMedia);
		List<InspectionMedia> inspectionMediaList=new ArrayList<InspectionMedia>();
		for(Object object:reqObject){
			
			Map<String,Object> reqMap=(Map<String, Object>) object;
			String annotation=JSONUtil.toJson(reqMap.get("annotation"));
			String blobId=(String)reqMap.get("id");
			String comment=(String)reqMap.get("comments");
			String description=(String)reqMap.get("description");
			String inspectorId=(String)reqMap.get("inspectorId");
			String defectType=String.valueOf(reqMap.get("defectType"));
			String statusType=String.valueOf(reqMap.get("statusType"));
			String inspectionId=(String)reqMap.get("inspectionId");
			String assetId=(String)reqMap.get("assetId");
			InspectionMedia media=new InspectionMedia(comment, blobId, inspectorId, new Date(), statusType, defectType,  annotation,description,assetId,inspectionId);
			inspectionMediaList.add(media);
		}
		issueDaoImpl.addIssue(inspectionMediaList);
		return "success";
	}
	@RequestMapping(value = "/inspection/addUpdateIssue", method = RequestMethod.POST)
	public String addUpdateIssue(@RequestBody String inspectionMedia){
		List<Object> reqObject=(List<Object>) JSONUtil.toObject(inspectionMedia, List.class);
		List<InspectionMedia> inspectionMediaList=new ArrayList<InspectionMedia>();
		for(Object object:reqObject){
			Map<String,Object> reqMap= (Map<String, Object>) object;
			String blobId=(String)reqMap.get("id");
			String comment=(String)reqMap.get("comments");
			String inspectorId=(String)reqMap.get("inspectorId");
			String defectType=String.valueOf(reqMap.get("defectType"));
			String statusType=String.valueOf(reqMap.get("statusType"));
			String inspectionId=(String)reqMap.get("inspectionId");
			String assetId=(String)reqMap.get("assetId");
			InspectionMedia media=new InspectionMedia(comment,blobId,inspectorId,new Date(),assetId,statusType,defectType,inspectionId);
			inspectionMediaList.add(media);
			
		}
	  return "success";	
	}
	
	@RequestMapping(value = "/inspection/updateIssue", method = RequestMethod.POST)
	public String updateIssue(@RequestBody String inspectionMedia){
		List<Object> reqObject=(List<Object>) JSONUtil.toObject(inspectionMedia, List.class);
		for(Object object:reqObject){
		
			Map<String,Object> reqMap= (Map<String, Object>) object;
			String blobId=(String)reqMap.get("id");
			String statusType=(String)reqMap.get("statusType");
			InspectionMedia media=new InspectionMedia(blobId,new Date(),statusType);
			issueDaoImpl.updateIssue(media);
		}
		return "success";
	}
	
	@RequestMapping(value = "/inspection/getIssueDate/inspectorId={inspectorId}&assetId={assetId}", method = RequestMethod.GET)
	public String getIssueDate(@PathVariable String inspectorId,@PathVariable String assetId){
		
		List<String> assetList=inspectionDao.getAsset(inspectorId);
		int assetIndex=assetList.indexOf(assetId);
		List<IssueInspection> issueInspectionList=issueDaoImpl.getIssueDate(inspectorId,assetId);
		String issueDateJson="";
		
		if(issueInspectionList.size()>0){
			List<InspectionMedia> inspectionMediaList= issueDaoImpl.getIssueDtls(inspectorId,issueInspectionList.get(0).getInspectionId(),assetId);
			IssueModel[] issueModelArray=getIssueJson(assetList,issueInspectionList,inspectionMediaList,assetIndex);
			issueDateJson=JSONUtil.toJson(issueModelArray);
		}
		
		return issueDateJson;
	}
	@RequestMapping(value = "/inspection/getIssues/inspectorId={inspectorId}&inspectionId={inspectionId}&assetId={assetId}", method = RequestMethod.GET)
	public String getIssue(@PathVariable String inspectorId,@PathVariable String inspectionId,@PathVariable String assetId){
		List<String> assetList=inspectionDao.getAsset(inspectorId);
		int assetIndex=assetList.indexOf(assetId);
		List<IssueInspection> issueInspectionList=issueDaoImpl.getIssueDate(inspectorId,assetId);
		String issueDateJson="";
		int inspectionIndex=0;
		
		for(IssueInspection issueInspection:issueInspectionList){
			if(issueInspection.getInspectionId().equalsIgnoreCase(inspectionId)){
				break;
			}
			inspectionIndex++;
		}
		
		if(issueInspectionList.size()>0){
			List<InspectionMedia> inspectionMediaList= issueDaoImpl.getIssueDtls(inspectorId,issueInspectionList.get(inspectionIndex).getInspectionId(),assetId);
			IssueModel[] issueModelArray=getIssueJson(assetList,issueInspectionList,inspectionMediaList,assetIndex);
			issueDateJson=JSONUtil.toJson(issueModelArray);
		}
		
		return issueDateJson;
	}
	@RequestMapping(value = "/inspection/getIssueMarker/inspectorId={inspectorId}", method = RequestMethod.GET)
	public String getIssueMarker(@PathVariable String inspectorId){
		List<IssueMarkerModel> inspectionMediaList= issueDaoImpl.getIssueMarker(inspectorId);
		String issueMarker=JSONUtil.toJson(inspectionMediaList);
		return issueMarker;
	}
	
	private IssueModel[] getIssueJson(List<String> assetList,List<IssueInspection> issueInspectionList,List<InspectionMedia> inspectionMediaList,int assetIndex){
		
		IssueModel[] issueInsectionArray=new IssueModel[assetList.size()];
		int index=0;
		for(String asset:assetList){
			
			for(IssueInspection issueInspection:issueInspectionList){
				Set<IssueDtlModel> set=new HashSet<IssueDtlModel>();
				for(InspectionMedia inspectionMedia:inspectionMediaList){
					String compFilePath="";
					//File file=new File(mediaLocation+inspectionMedia.getBlobId());
					//if(!ImageUtil.isCompressedFilePresent(compMediaLocation+file.getName())){
						compFilePath=ImageUtil.storeAndCompressedFile(mediaLocation+inspectionMedia.getBlobId(), compMediaLocation);
					//}
					IssueDtlModel issueDtlModel=new IssueDtlModel(inspectionMedia.getDefectType(), "/Polymer/temp/"+compFilePath, inspectionMedia.getBlobId(), "/Polymer/images/marker2.png", inspectionMedia.getStatusType(), "tooltip");
					set.add(issueDtlModel);
				}
				issueInspection.setIssueDtlModel(set);
			}
			
			IssueModel issueModel=null;
			if(index==assetIndex){
				issueModel=new IssueModel(String.valueOf(index),asset,"drone-one",issueInspectionList);
			}else{
				issueModel=new IssueModel(String.valueOf(index),asset,"drone-one",null);
			}
			issueInsectionArray[index]=issueModel;
			index++;
		}
		
		return issueInsectionArray;
	}
	
	
	
	
}
