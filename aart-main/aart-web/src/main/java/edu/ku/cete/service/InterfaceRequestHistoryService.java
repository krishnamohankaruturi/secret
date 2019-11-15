package edu.ku.cete.service;

import edu.ku.cete.domain.InterfaceRequestHistory;

public interface InterfaceRequestHistoryService {

    int insert(InterfaceRequestHistory record);

    int insertSelective(InterfaceRequestHistory record);

    InterfaceRequestHistory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(InterfaceRequestHistory record);

    int updateByPrimaryKey(InterfaceRequestHistory record);
}
