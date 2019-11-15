package edu.ku.cete.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.LinkageLevelSortOrder;
import edu.ku.cete.domain.MicroMap;
import edu.ku.cete.domain.MicroMapExample;
import edu.ku.cete.model.LinkageLevelSortOrderMapper;
import edu.ku.cete.model.MicroMapMapper;
import edu.ku.cete.service.MicroMapService;
import edu.ku.cete.tde.webservice.domain.MicroMapResponseDTO;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class MicroMapServiceImpl implements MicroMapService {

	/**
	 * microMapMapper
	 */
	@Autowired
	private MicroMapMapper microMapMapper;
	
	@Autowired
	private LinkageLevelSortOrderMapper linkageLevelSortOrderMapper;
	
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.MicroMapService#addOrUpdate(java.util.List)
	 */
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<MicroMap> addOrUpdate(
			List<MicroMapResponseDTO> microMapResponseDtos, Long contentId) {
		MicroMapExample microMapExample = null;
		MicroMap microMap = null;
		Integer updated = 0;
		List<MicroMap> microMaps = new ArrayList<MicroMap>();
		for(MicroMapResponseDTO microMapResponseDto: microMapResponseDtos) {
			microMapExample = new MicroMapExample();
			microMapExample.createCriteria().andMicromapIdEqualTo(microMapResponseDto.getMicromapid()).
				andNodeIdEqualTo(microMapResponseDto.getNodeid());
			microMap = copyObject(microMapResponseDto, contentId);
			updated = microMapMapper.updateByExampleSelective(microMap, microMapExample);
			if(updated < 1) {
				insert(microMap);				
			}
			microMaps.add(microMap);
		}
		
		return microMaps;
	}
	
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.MicroMapService#insert(edu.ku.cete.domain.MicroMap)
	 */
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer insert(MicroMap microMap) {
		microMap.setAuditColumnProperties();		
		return microMapMapper.insert(microMap);
	}
	
	
	/**
	 * @param microMapResponseDto
	 * @return
	 */
	private MicroMap copyObject(MicroMapResponseDTO microMapResponseDto, Long contentId) {
		MicroMap microMap = new MicroMap();
		microMap.setMicromapId(microMapResponseDto.getMicromapid());
		microMap.setMicromapName(microMapResponseDto.getMicromapname());
		microMap.setAssociatedEE(microMapResponseDto.getAssociatedee());
		microMap.setNodeId(microMapResponseDto.getNodeid());
		microMap.setLinkageLabel(microMapResponseDto.getLinkagelabel());
		microMap.setNodeKey(microMapResponseDto.getNodekey());
		microMap.setNodeName(microMapResponseDto.getNodename());
		microMap.setLinkageLevelShortDesc(microMapResponseDto.getLinkagelevelshortdesc());
		microMap.setLinkageLevelLongDesc(microMapResponseDto.getLinkagelevellongdesc());
		microMap.setNodeDescription(microMapResponseDto.getNodedescription());
		microMap.setVersionId(microMapResponseDto.getVersionid());
		microMap.setVersionName(microMapResponseDto.getVersionname());
		microMap.setVersionNumber(microMapResponseDto.getVersionnumber());
		microMap.setContentFrameworkDetailId(contentId);
		
		return microMap;
	}


	@Override
	public List<MicroMap> selectMicroMapByItiHistoryStudentId(Long studentID) {
		return microMapMapper.selectMicroMapByItiHistory(studentID);
	}


	@Override
	public List<LinkageLevelSortOrder> getLinkageLevelSortOrders(Long contentAreaId, List<String> linkageLevelNames) {
		return linkageLevelSortOrderMapper.getLevelsByContentAreaAndName(contentAreaId, linkageLevelNames);
	}
	
}
