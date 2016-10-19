package com.ge.inspection.ir.repository.immuta;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ge.inspection.ir.domain.immuta.InspectionDtls;


public interface InspectionDtlRepository extends JpaRepository<InspectionDtls, String>{
	 @Query("SELECT u FROM InspectionDtls u WHERE LOWER(u.inspectorId) = LOWER(:inspectorId)")
	 List<InspectionDtls> findByInspectorId(@Param("inspectorId") String inspectorId);
	 
	 @Query("SELECT u FROM InspectionDtls u WHERE LOWER(u.inspectorId) = LOWER(:inspectorId) ")
	 List<InspectionDtls> findIssueByInspectorId(@Param("inspectorId") String inspectorId);
	 
	 @Query("SELECT distinct  (u.assetId) FROM InspectionDtls u WHERE LOWER(u.inspectorId) = LOWER(:inspectorId)")
	 List<String> getAsset(@Param("inspectorId") String inspectorId); 
	 
	 @Query("SELECT distinct  (u.inspectionStart) FROM InspectionDtls u WHERE LOWER(u.assetId)=LOWER(:assetId) and LOWER(u.inspectorId) = LOWER(:inspectorId)")
	 List<Timestamp> getMediaDate(@Param("inspectorId") String inspectorId,@Param("assetId") String assetId);
	 
	 @Query("SELECT u FROM InspectionDtls u WHERE LOWER(u.inspectorId) = LOWER(:inspectorId) and LOWER(u.assetId)=LOWER(:assetId) and u.inspectionStart=:inspectionStart order by u.inspectionPhaseId")
	 List<InspectionDtls> getMedia(@Param("inspectorId") String inspectorId,@Param("assetId") String assetId,@Param("inspectionStart") Date inspectionStart);

}
