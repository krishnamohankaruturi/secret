package edu.ku.cete.domain.test;

public class TaskVariantContentFrameworkDetail extends TaskVariantContentFrameworkDetailKey {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.taskvariantcontentframeworkdetail.isprimary
     *
     * @mbggenerated Thu Oct 25 19:34:56 CDT 2012
     */
    private Boolean isPrimary;
    
    private ContentFrameworkDetail contentFrameworkDetail;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.taskvariantcontentframeworkdetail.isprimary
     *
     * @return the value of public.taskvariantcontentframeworkdetail.isprimary
     *
     * @mbggenerated Thu Oct 25 19:34:56 CDT 2012
     */
    public Boolean getIsPrimary() {
        return isPrimary;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.taskvariantcontentframeworkdetail.isprimary
     *
     * @param isPrimary the value for public.taskvariantcontentframeworkdetail.isprimary
     *
     * @mbggenerated Thu Oct 25 19:34:56 CDT 2012
     */
    public void setIsPrimary(Boolean isprimary) {
        this.isPrimary = isprimary;
    }

	/**
	 * @return the contentFrameworkDetail
	 */
	public ContentFrameworkDetail getContentFrameworkDetail() {
		return contentFrameworkDetail;
	}

	/**
	 * @param contentFrameworkDetail the contentFrameworkDetail to set
	 */
	public void setContentFrameworkDetail(ContentFrameworkDetail contentFrameworkDetail) {
		this.contentFrameworkDetail = contentFrameworkDetail;
	}
}