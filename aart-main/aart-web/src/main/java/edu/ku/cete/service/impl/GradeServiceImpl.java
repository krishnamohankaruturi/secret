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

import edu.ku.cete.domain.content.Grade;
import edu.ku.cete.model.GradeDao;
import edu.ku.cete.service.GradeService;

/**
 * @author neil.howerton
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class GradeServiceImpl implements GradeService {

    @Autowired
    private GradeDao gradeDao;

    /**
     * @param grade int
     * @return {@link Grade}
     */    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Grade findByGrade(int grade) {
        return gradeDao.findByGrade(grade);
    }

    /**
     * @return List<Grade>
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Grade> getAll() {
        return gradeDao.getAll();
    }

}
