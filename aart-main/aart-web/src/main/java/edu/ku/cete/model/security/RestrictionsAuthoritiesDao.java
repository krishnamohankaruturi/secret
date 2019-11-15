package edu.ku.cete.model.security;

import edu.ku.cete.domain.security.RestrictionsAuthorities;
import edu.ku.cete.domain.security.RestrictionsAuthoritiesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RestrictionsAuthoritiesDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictions_authorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    int countByExample(RestrictionsAuthoritiesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictions_authorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    int deleteByExample(RestrictionsAuthoritiesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictions_authorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictions_authorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    int insert(RestrictionsAuthorities record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictions_authorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    int insertSelective(RestrictionsAuthorities record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictions_authorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    List<RestrictionsAuthorities> selectByExample(RestrictionsAuthoritiesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictions_authorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    RestrictionsAuthorities selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictions_authorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    int updateByExampleSelective(@Param("record") RestrictionsAuthorities record, @Param("example") RestrictionsAuthoritiesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictions_authorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    int updateByExample(@Param("record") RestrictionsAuthorities record, @Param("example") RestrictionsAuthoritiesExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictions_authorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    int updateByPrimaryKeySelective(RestrictionsAuthorities record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.restrictions_authorities
     *
     * @mbggenerated Tue Sep 04 22:27:28 CDT 2012
     */
    int updateByPrimaryKey(RestrictionsAuthorities record);
}