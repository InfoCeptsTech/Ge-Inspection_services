package com.ge.inspection.ir.repository.muta;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ge.inspection.ir.domain.muta.InspectionMedia;

public interface IssueDtlRepository extends JpaRepository<InspectionMedia, String> {
	 
	 @Query("SELECT distinct  (u.issueDate) FROM InspectionMedia u WHERE LOWER(u.inspectorId) = LOWER(:inspectorId) and LOWER(u.assetId) = LOWER(:assetId)")
	 List<Date> getIssueDate(@Param("inspectorId") String inspectorId,@Param("assetId") String assetId);
	 
	 @Query("SELECT u FROM InspectionMedia u WHERE LOWER(u.inspectorId) = LOWER(:inspectorId) and u.issueDate=:issueDate and LOWER(u.assetId) = LOWER(:assetId)")
	 List<InspectionMedia> findIssue(@Param("inspectorId") String inspectorId,@Param("issueDate") Date issueDate,@Param("assetId") String assetId);
	 
	 @Query("SELECT u FROM InspectionMedia u WHERE LOWER(u.inspectorId) = LOWER(:inspectorId) and u.issueDate=:issueDate")
	 List<InspectionMedia> findByInspectorId(@Param("inspectorId") String inspectorId,@Param("issueDate") Date issueDate);
	 
	 @Query("SELECT u FROM InspectionMedia u WHERE LOWER(u.inspectorId) = LOWER(:inspectorId) and u.inspectionDate=:inspectionDate")
	 List<InspectionMedia> findIssueMarker(@Param("inspectorId") String inspectorId,@Param("inspectionDate") Date inspectionDate);
	 
}
