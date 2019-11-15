/**
 * 
 */
package edu.ku.cete.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.domain.Tag;
import edu.ku.cete.model.TagMapper;
import edu.ku.cete.service.TagService;

/**
 * @author gowtham.nalluri
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagDao;

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Tag createTag(Tag tag) {
    	tagDao.insert(tag);
        return tag;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void updateTag(Tag tag) {
    	tagDao.updateByPrimaryKeySelective(tag);
    }
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.DEFAULT)
    public final List<Tag> getAllTags() {
        return (List<Tag>) tagDao.selectAll();
    }
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.DEFAULT)
   public final List<Tag> getTagByName(String tagName, boolean includeEqual) {
    	
		if (includeEqual) {
			return tagDao.selectByName(tagName);
		} else {
			return tagDao.selectByNameContains(tagName);
		}        
    }

    @Override
    @Transactional(readOnly=true, propagation = Propagation.NOT_SUPPORTED, isolation = Isolation.DEFAULT)
    public final Tag getTag(long tagId) {
        return tagDao.selectByPrimaryKey(tagId);
    }
    
}
