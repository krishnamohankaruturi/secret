package edu.ku.cete.model.security;

import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.security.RestrictionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RestrictionDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restriction
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    int countByExample(RestrictionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restriction
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    int deleteByExample(RestrictionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restriction
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restriction
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    int insert(Restriction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restriction
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    int insertSelective(Restriction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restriction
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    List<Restriction> selectByExample(RestrictionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restriction
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    Restriction selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restriction
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    int updateByExampleSelective(@Param("record") Restriction record, @Param("example") RestrictionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restriction
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    int updateByExample(@Param("record") Restriction record, @Param("example") RestrictionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restriction
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    int updateByPrimaryKeySelective(Restriction record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restriction
     *
     * @mbggenerated Tue Sep 04 20:00:12 CDT 2012
     */
    int updateByPrimaryKey(Restriction record);
}