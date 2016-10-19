package com.ge.inspection.ir.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ge.inspection.ir.dao.IssueDao;
import com.ge.inspection.ir.domain.muta.InspectionMedia;
import com.ge.inspection.ir.model.IssueMarkerModel;
import com.ge.inspection.ir.util.JSONUtil;

@RestController
public class IssueController {
	@Autowired
	private IssueDao issueDaoImpl;
	
	@RequestMapping(value = "/inspection/addIssue", method = RequestMethod.POST)
	public String addIssue(@RequestBody InspectionMedia inspectionMedia){
		issueDaoImpl.addIssue(inspectionMedia);
		return "";
	}
	
	@RequestMapping(value = "/inspection/getIssueDate/inspectorId={inspectorId}&assetId={assetId}", method = RequestMethod.GET)
	public String getIssueDate(@PathVariable String inspectorId,@PathVariable String assetId){
		List<Date> issueDate=issueDaoImpl.getIssueDate(inspectorId,assetId);
		DateFormat df = new SimpleDateFormat("MMM dd yyyy");
		String issueDateStr= df.format(issueDate.get(0));
		List<InspectionMedia> inspectionMediaList= issueDaoImpl.getIssueDtls(inspectorId,issueDateStr,assetId);
		
		String issueDateJson=JSONUtil.toJson(issueDate);
		return issueDateJson;
	}
	
	@RequestMapping(value = "/inspection/getIssues/inspectorId={inspectorId}&issueDate={issueDate}&assetId={assetId}", method = RequestMethod.GET)
	public String getIssue(@PathVariable String inspectorId,@PathVariable String issueDate,@PathVariable String assetId){
		List<InspectionMedia> inspectionMediaList= issueDaoImpl.getIssueDtls(inspectorId,issueDate,assetId);
		String issueJson=JSONUtil.toJson(inspectionMediaList);
		return issueJson;
	}
	
	@RequestMapping(value = "/inspection/getIssueMarker/inspectorId={inspectorId}&issueDate={issueDate}&assetId={assetId}", method = RequestMethod.GET)
	public String getIssueMarker(@PathVariable String inspectorId,@PathVariable String issueDate,@PathVariable String assetId){
		List<IssueMarkerModel> inspectionMediaList= issueDaoImpl.getIssueMarker(inspectorId,issueDate);
		String issueMarker=JSONUtil.toJson(inspectionMediaList);
		return issueMarker;
	}
	
	
}
