package com.ge.inspection.ir.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ge.inspection.ir.dao.AssestDao;

@RestController
public class AssetController {

	@Autowired
	private AssestDao assestDaoImple;
	
	@RequestMapping(value = "/inspection/getAssets/{userId}", method = RequestMethod.GET)
	public String getAssets(@PathVariable String userId){
		String assests=assestDaoImple.getAssets(userId);
		return assests;
	}
	@RequestMapping(value = "/inspection/getIssue/{userId}", method = RequestMethod.GET)
	public String getIssue(@PathVariable String userId){
		String assests=assestDaoImple.getIssue(userId);
		return assests;
	}
	@RequestMapping(value = "/inspection/getMarker/{userId}", method = RequestMethod.GET)
	public String getIssueMarker(@PathVariable String userId){
		String assests=assestDaoImple.getMarker(userId);
		return assests;
	}
}
