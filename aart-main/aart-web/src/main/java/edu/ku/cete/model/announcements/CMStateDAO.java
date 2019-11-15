/**
 * 
 */
package edu.ku.cete.model.announcements;

import java.math.BigInteger;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.ku.cete.domain.announcements.CMState;

/**
 * @author venkat
 *
 */
public interface CMStateDAO {
		public int insert(CMState cmOAP);
		
		public int delete(CMState cmOAP);
		
		
		public int deleteByMessageId(@Param("messageId") BigInteger messageId);
		
		public List<CMState> getMessageStates(@Param("messageId") BigInteger messageId);
		
}
