/**
 * 
 */
package edu.ku.cete.service;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.OrganizationTree;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.UploadFile;
import edu.ku.cete.domain.student.PersonalNeedsProfileRecord;
import edu.ku.cete.domain.upload.UploadedOrganization;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.util.AartParseException;

/**
 * @author neil.howerton
 *
 */
public interface UploadService {

	/**
	 * @param uploadFile {@link UploadFile} 
	 * @param orgColMap {@link  Map<String, String>}
	 * @param fieldSpecificationMap Map<String, FieldSpecification>}
	 * @param currentUserOrg {@link Organization}
	 * @param currentUserChildOrgs {@link Collection<Organization>}
	 * @param invalidOrgs{@link List<UploadedOrganization>}
	 * @return Map<String, Integer>
	 * @throws IOException
	 * @throws AartParseException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws InstantiationException
	 * @throws IntrospectionException
	 */
	public Map<String, Object> bulkUploadOrganization(UploadFile uploadFile, Map<String, FieldSpecification> fieldSpecificationMap,
			Organization currentUserOrg, ContractingOrganizationTree contractingOrganizationTree, List<UploadedOrganization> invalidOrgs, 
			String fileCharset, Boolean defaultFileCharset)  throws IOException, AartParseException, IllegalAccessException, InvocationTargetException, InstantiationException, IntrospectionException;

	/**
	 * Uploads the records and return the ones with appropriate
	 * save/reject statuses marked
	 * @param personalNeedsProfileRecords
	 * @param userOrganizationTree
	 * @return
	 */
	List<PersonalNeedsProfileRecord> cascadeAddOrUpdate(
			List<PersonalNeedsProfileRecord> personalNeedsProfileRecords,
			OrganizationTree userOrganizationTree);

}
