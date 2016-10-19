package com.ge.inspection.ir.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ge.inspection.ir.dao.InspectionDao;
import com.ge.inspection.ir.model.AssetModel;
import com.ge.inspection.ir.model.DurationModel;
import com.ge.inspection.ir.model.ImageModel;
import com.ge.inspection.ir.model.InspectionModel;
import com.ge.inspection.ir.model.MediaModel;
import com.ge.inspection.ir.util.JSONUtil;

@RestController
public class InspectionController {
 
	@Autowired
	private InspectionDao inspectionDao;
	
	@RequestMapping(value = "/inspection/getInspection/inspectorId={inspectorId}", method = RequestMethod.GET)
	public String getAssets(@PathVariable String inspectorId){
		AssetModel[] assetModel=inspectionDao.getInspectionDtls(inspectorId);
		String inspectionJson=JSONUtil.toJson(assetModel);
		return inspectionJson;
	}
	
	@RequestMapping(value = "/inspection/getAsset/inspectorId={inspectorId}", method = RequestMethod.GET)
	public String getAsset(@PathVariable String inspectorId){
		List<String> assetList=inspectionDao.getAsset(inspectorId);
		String assetId=assetList.get(0);
		List<Timestamp> dateList=inspectionDao.getMediaDate(inspectorId,assetId);
		DateFormat df = new SimpleDateFormat("MMM dd yyyy");
		String inspectionStart= df.format(dateList.get(0));
		Set<MediaModel> mediaList=inspectionDao.getMedia(inspectorId,assetId,inspectionStart);
		AssetModel[] assetModel=createJson(assetList,dateList,mediaList,0,0);
		String assetJson=JSONUtil.toJson(assetModel);
		return assetJson;
	}
	
	@RequestMapping(value = "/inspection/getMediaDate/inspectorId={inspectorId}&assetId={assetId}", method = RequestMethod.GET)
	public String getMediaDate(@PathVariable String inspectorId,@PathVariable String assetId){
		
		List<String> assetList=inspectionDao.getAsset(inspectorId);
		int assetIndex=assetList.indexOf(assetId);
		
		List<Timestamp> dateList=inspectionDao.getMediaDate(inspectorId,assetId);
		DateFormat df = new SimpleDateFormat("MMM dd yyyy");
		String inspectionStart= df.format(dateList.get(0));
		Set<MediaModel> mediaList=inspectionDao.getMedia(inspectorId,assetId,inspectionStart);
		AssetModel[] assetModel=createJson(assetList,dateList,mediaList,assetIndex,0);
		String assetJson=JSONUtil.toJson(assetModel);
		
		return assetJson;
	}
	
	@RequestMapping(value = "/inspection/getMedia/inspectorId={inspectorId}&assetId={assetId}&inspectionStart={inspectionStart}", method = RequestMethod.GET)
	public String getMedia(@PathVariable String inspectorId,@PathVariable String assetId,@PathVariable String inspectionStart){
		//Set<MediaModel> assetList=inspectionDao.getMedia(inspectorId,assetId,inspectionStart);
		
		List<String> assetList=inspectionDao.getAsset(inspectorId);
		int assetIndex=assetList.indexOf(assetId);
		
		List<Timestamp> dateList=inspectionDao.getMediaDate(inspectorId,assetId);
		List<String> inspectionDateList=new ArrayList<String>();
		
		DateFormat df = new SimpleDateFormat("MMM dd yyyy");
		for(Date date:dateList){
			inspectionDateList.add(df.format(date));
		}
		int mediaIndex=inspectionDateList.indexOf(inspectionStart);
		
		Set<MediaModel> mediaList=inspectionDao.getMedia(inspectorId,assetId,inspectionStart);
		AssetModel[] assetModel=createJson(assetList,dateList,mediaList,assetIndex,mediaIndex);
		String assetJson=JSONUtil.toJson(assetModel);
		
		return assetJson;
	}
	
	
	private AssetModel[] createJson(List<String> assetList,List<Timestamp> dateList,Set<MediaModel> mediaList,int assetIndex,int mediaIndex){
		
		AssetModel[] assetModelArray = new AssetModel[assetList.size()];
		int assetIndexLocal=0;
		for(String asset:assetList){
			 List<InspectionModel> inspectionModelList=new ArrayList<InspectionModel>();
			 
			 int mediaIndexLocal=0;
			 for(Timestamp time:dateList){
				if(mediaIndexLocal==mediaIndex&&assetIndex==assetIndexLocal){
					String duration="";
					List<DurationModel> startDurationList=new ArrayList<DurationModel>();
					List<DurationModel> endDurationList=new ArrayList<DurationModel>();
					for(MediaModel mediaModel:mediaList){
						for(ImageModel imageModel:mediaModel.getImageModel()){
							startDurationList.add(new DurationModel(imageModel.getInspectionStartDate()));
							endDurationList.add(new DurationModel(imageModel.getInspectionEndDate()));
						}
					}
					if(startDurationList.get(0)!=null && endDurationList.get(endDurationList.size()-1)!=null){
						duration=String.valueOf(startDurationList.get(0).getDateTime()).split(" ")[1]+"-"+String.valueOf(endDurationList.get(endDurationList.size()-1).getDateTime()).split(" ")[1];	
					}
					inspectionModelList.add(new InspectionModel(time, duration, mediaList));
				}else{
					inspectionModelList.add(new InspectionModel(time, null, null));
				}
				mediaIndexLocal++;
			 }
			 AssetModel assetModel=null;
			if(assetIndexLocal==assetIndex){
				assetModel=new AssetModel(asset,inspectionModelList);
			}else{
				assetModel=new AssetModel(asset,null);
			}
			assetModelArray[assetIndexLocal]=assetModel;
			assetIndexLocal++;
			
		}
		return assetModelArray;
	}
}
