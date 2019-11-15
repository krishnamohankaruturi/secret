/**
 * 
 */
package edu.ku.cete.service;

import java.util.List;

import edu.ku.cete.domain.Tag;

/**
 * @author gowtham.nalluri
 *
 */
public interface TagService {

    public Tag createTag(Tag tag);
    
    public void updateTag(Tag tag);

    public Tag getTag(long tagId);
    
    public List<Tag> getAllTags();
    
    public List<Tag> getTagByName(String name, boolean includeEqual);    
}
