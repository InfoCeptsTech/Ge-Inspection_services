package com.ge.inspection.ir.daoImpl;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ge.inspection.ir.dao.InspectionDao;
import com.ge.inspection.ir.domain.immuta.InspectionDtls;
import com.ge.inspection.ir.model.AssetModel;
import com.ge.inspection.ir.model.ImageModel;
import com.ge.inspection.ir.model.MediaModel;
import com.ge.inspection.ir.repository.immuta.InspectionDtlRepository;

@Component("inspectionDao")
public class InspectionDaoImpl implements InspectionDao {

    @Autowired
    private InspectionDtlRepository inspectionDtlRepository;
    
    
	@Override
	public AssetModel[] getInspectionDtls(String inspectorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getAsset(String inspectorId) {
		List<String> assetList=inspectionDtlRepository.getAsset(inspectorId);
		return assetList;
	}

	@Override
	public List<Timestamp> getMediaDate(String inspectorId, String assetId) {
		List<Timestamp> dateList=inspectionDtlRepository.getMediaDate(inspectorId, assetId);
		return dateList;
	}

	@Override
	public Set<MediaModel> getMedia(String inspectorId, String assetId,
			String inspectionStart) {
		SimpleDateFormat formatter = new SimpleDateFormat("MMM dd yyyy");
		Set<MediaModel> mediaModel=null;
		List<InspectionDtls> inspectionDtlsList=null;
		try {
			Date inspectionDate = formatter.parse(inspectionStart);
			inspectionDtlsList=inspectionDtlRepository.getMedia(inspectorId, assetId, inspectionDate);
			mediaModel=getMedia(inspectionDtlsList);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return mediaModel;
	}

	
	private Set<MediaModel> getMedia(List<InspectionDtls> inspectionDtlsList){
		Set<MediaModel> phaseSet=new HashSet<MediaModel>();
		for(InspectionDtls inspectionDtls:inspectionDtlsList){
			phaseSet.add(new MediaModel(inspectionDtls.getInspectionPhaseId()));
		}
		
		for(MediaModel phase:phaseSet){
			List<ImageModel> imageModelList=new ArrayList<ImageModel>();
			for(InspectionDtls inspectionDtls:inspectionDtlsList){
				if(inspectionDtls.getInspectionPhaseId().equalsIgnoreCase(phase.getTitle())){
					imageModelList.add(new ImageModel(inspectionDtls.getBlobId(), "miniPath", "megaPath",inspectionDtls.getInspectionStop(),inspectionDtls.getInspectionStart()));
				}
			}
			phase.setImageModel(imageModelList);
		}
		
		return phaseSet;
	}

	/*
	@Autowired
	private InspectionDtlRepository inspectionDtlRepository;
	 
	@Transactional
	public AssetModel[]  getInspectionDtls(String inspectorId) {
		List<InspectionDtl> inspectionList=inspectionDtlRepository.findByInspectorId(inspectorId);
		AssetModel[] assetModel=getAssets(inspectionList);
		return assetModel;
	}
	
	private AssetModel[] getAssets(List<InspectionDtl> inspectionList){
		Set<AssetModel> assetSet=new HashSet<AssetModel>();
		for(InspectionDtl inspectionDtl:inspectionList){
			assetSet.add(new AssetModel(inspectionDtl.getAsset().getAssetId(),inspectionDtl.getAsset().getAssetName(),null));
		}
		
		for(AssetModel assetModel:assetSet){
			List<MediaModel> mediaList=new ArrayList<MediaModel>();
			for(InspectionDtl inspectionDtl:inspectionList){
				
				if(assetModel.getId().equals(inspectionDtl.getAsset().getAssetId())){
					mediaList.add(new MediaModel(String.valueOf(inspectionDtl.getInspectionStart()),"duration",null));
				}
			}
			assetModel.setInspection(mediaList);
		}
	
	
	
	for(AssetModel assetModel:assetSet){
		for(MediaModel mediaModel:assetModel.getInspection()){
			List<PhaseModel> phaseModelList=new ArrayList<PhaseModel>(); 
			for(InspectionDtl inspectionDtl:inspectionList){
				if(mediaModel.getDate().equals(inspectionDtl.getInspectionStart())){
					phaseModelList.add(new PhaseModel(inspectionDtl.getPhase().getPhaseName(),null));
				}
			}
			mediaModel.setPhase(phaseModelList);
		}
	}
	
	for(AssetModel assetModel:assetSet){
		for(MediaModel mediaModel:assetModel.getInspection()){
			for(PhaseModel phaseModel:mediaModel.getPhase()){
				List<ImageModel> imgModelList=new ArrayList<ImageModel>();
				for(InspectionDtl inspectionDtl:inspectionList){
				  if(inspectionDtl.getPhase().getPhaseName().equals(phaseModel.getTitle())){
					ImageModel imageModel=new ImageModel(inspectionDtl.getMedia().getMediaId(),"minipath","megapath");
					//code for image compression and temp path
					imgModelList.add(imageModel); 
				  }
				}
				phaseModel.setImageModel(imgModelList);
			}
			
			
		}
	}
  return (AssetModel[]) assetSet.toArray();
}
*/
}

