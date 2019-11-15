package edu.ku.cete.service;

import java.util.List;

import edu.ku.cete.domain.LinkageLevelSortOrder;
import edu.ku.cete.domain.MicroMap;
import edu.ku.cete.tde.webservice.domain.MicroMapResponseDTO;

public interface MicroMapService {

	/**
	 * @param microMapResponseDtos
	 * @return
	 */
	List<MicroMap> addOrUpdate(
			List<MicroMapResponseDTO> microMapResponseDtos, Long contentId);
	
	/**
	 * @param microMap
	 * @return
	 */
	Integer insert(MicroMap microMap);
	
	List<MicroMap> selectMicroMapByItiHistoryStudentId(Long studentID);
	
	List<LinkageLevelSortOrder> getLinkageLevelSortOrders(Long contentAreaId, List<String> linkageLevelNames);
}
