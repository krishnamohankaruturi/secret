package edu.ku.cete.model.mapper;

import edu.ku.cete.domain.user.UserPasswordReset;
import edu.ku.cete.domain.user.UserPasswordResetCriteria;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserPasswordResetMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.userpasswordreset
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    int countByUserPasswordResetCriteria(UserPasswordResetCriteria userPasswordResetCriteria);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.userpasswordreset
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    int deleteByUserPasswordResetCriteria(UserPasswordResetCriteria userPasswordResetCriteria);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.userpasswordreset
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    int insert(UserPasswordReset record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.userpasswordreset
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    int insertSelective(UserPasswordReset record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.userpasswordreset
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    List<UserPasswordReset> selectByUserPasswordResetCriteria(UserPasswordResetCriteria userPasswordResetCriteria);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.userpasswordreset
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    int updateByUserPasswordResetCriteriaSelective(@Param("record") UserPasswordReset record, @Param("userPasswordResetCriteria") UserPasswordResetCriteria userPasswordResetCriteria);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table public.userpasswordreset
     *
     * @mbggenerated Tue Nov 27 11:52:27 CST 2012
     */
    int updateByUserPasswordResetCriteria(@Param("record") UserPasswordReset record, @Param("userPasswordResetCriteria") UserPasswordResetCriteria userPasswordResetCriteria);

    /**
     * Manoj Kumar O : Added for US_16244(provide UI TO merge Users)
     */
	void deleteUserById(@Param("id") Long id);
}