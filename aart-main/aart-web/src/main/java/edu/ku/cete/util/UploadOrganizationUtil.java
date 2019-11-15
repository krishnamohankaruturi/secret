/**
 * 
 */
package edu.ku.cete.util;

import java.beans.IntrospectionException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import au.com.bytecode.opencsv.CSVReader;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.CategoryExample;
import edu.ku.cete.domain.common.CategoryType;
import edu.ku.cete.domain.common.CategoryTypeExample;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.common.UploadFile;
import edu.ku.cete.domain.upload.UploadedOrganization;
import edu.ku.cete.domain.validation.FieldSpecification;
import edu.ku.cete.domain.validation.FieldSpecificationExample;
import edu.ku.cete.model.InValidDetail;
import edu.ku.cete.model.common.CategoryTypeDao;
import edu.ku.cete.model.validation.FieldSpecificationDao;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.ServiceException;

/**
 * @author neil.howerton
 * TODO remove this class if this is not used.
 * @deprecated dead code
 */
@Component
public class UploadOrganizationUtil implements MessageSourceAware {
	//TODO change it to use serviceImpl alone.
    private Logger logger = LoggerFactory.getLogger(UploadOrganizationUtil.class);

    @Autowired
    private OrgUploadSpecification orgUploadSpecification;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryTypeDao categoryTypeDao;

    @Autowired
    private FieldSpecificationDao fieldSpecificationDao;

    @Autowired
    private OrganizationService orgService;

    @Autowired
    private OrganizationTypeService orgTypeService;

    private Map<Long, Category> recordTypeMap;

    private MessageSource messageSource;

    /**
     * Initial setup for the class.
     */
    @PostConstruct
    public final void setFieldSpecs() {
        setRecordTypesMap();
    }

    /**
     * Processes the uploaded organization record file. Checking for consistency and] adding organizations.
     * @param uploadFile {@link UploadFile}
     * @param result {@link Errors}
     * @param mav {@link ModelAndView}
     * @param currentContext {@link Organization}
     */
    public final void uploadOrganizationsFromFile(UploadFile uploadFile, Errors result, ModelAndView mav,
            Organization currentContext) {
        List<UploadedOrganization> uploadedOrgs = new ArrayList<UploadedOrganization>();

        mav.addObject("recordsCreatedCount", 0);
        mav.addObject("recordRejectedCount", 0);
        mav.addObject("totalRecordsProcessed", 0);

        try {
            if (uploadFile.getFile() != null) {
                CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(uploadFile.getFile())));
                AartColumnMappingStrategy<UploadedOrganization> columnReadingStrategy =
                        new AartColumnMappingStrategy<UploadedOrganization>();
                columnReadingStrategy.setType(UploadedOrganization.class);
                columnReadingStrategy.setColumnMapping(orgUploadSpecification.getOrgColumnMap());

                AartCsvToBean<UploadedOrganization> aartCsvToBeanParser = new AartCsvToBean<UploadedOrganization>();
                aartCsvToBeanParser.setFieldSpecificationMap(setOrgFieldSpecificationMap());

                uploadedOrgs = aartCsvToBeanParser.aartParse(columnReadingStrategy, reader);

                logger.debug("Received {} organizations to upload to the system.", uploadedOrgs.size());

                List<UploadedOrganization> invalidOrgs = new ArrayList<UploadedOrganization>();
                int createdOrgs = checkUploadedOrgs(uploadedOrgs, invalidOrgs, currentContext);

                mav.addObject("recordsCreatedCount", createdOrgs);
                mav.addObject("recordsRejectedCount", invalidOrgs.size());
                mav.addObject("recordsUpdatedCount", 0);
                mav.addObject("inValidRecords", invalidOrgs);
                mav.addObject("totalRecordCount", uploadedOrgs.size());
            }
        } catch (IOException e) {
            logger.debug("Caught IOException in uploadOranizationsFromFile. Stacktrace: {}", e.getStackTrace());
            mav.addObject("errorMessage", messageSource.getMessage("error.upload.org.unabletoparse", null, null));
        } catch (AartParseException e) {
            logger.debug("Caught AartParseException in uploadOrganizationsFromFile. StackTrace: {}", e.getStackTrace());
            InValidDetail invalidDetail = InValidDetail.getInstance(e.getRecordName(), e.getAttemptedValue());
            mav.addObject("inValidDetail", invalidDetail);
        } catch (IllegalAccessException e) {
            logger.debug("Caught IllegalAccessException in uploadUsersFromFile. Stacktrace: {}", e.getStackTrace());
            mav.addObject("errorMessage", messageSource.getMessage("error.upload.org.unabletoparse", null, null));
        } catch (InvocationTargetException e) {
            logger.debug("Caught InvocationTargetException in uploadUsersFromFile. Stacktrace: {}", e.getStackTrace());
            mav.addObject("errorMessage", messageSource.getMessage("error.upload.org.unabletoparse", null, null));
        } catch (InstantiationException e) {
            logger.debug("Caught InstantiationException in uploadUsersFromFile. Stacktrace: {}", e.getStackTrace());
            mav.addObject("errorMessage", messageSource.getMessage("error.upload.org.unabletoparse", null, null));
        } catch (IntrospectionException e) {
            logger.debug("Caught IntrospectionException in uploadUsersFromFile. Stacktrace: {}", e.getStackTrace());
            mav.addObject("errorMessage", messageSource.getMessage("error.upload.org.unabletoparse", null, null));
        } catch (ServiceException e) {
            logger.debug("Caught IntrospectionException in uploadUsersFromFile. Stacktrace: {}", e.getStackTrace());
            mav.addObject("errorMessage", messageSource.getMessage("error.upload.org.unabletoparse", null, null));
        }
    }

    /**
     * Checks that the uploaded organizations parent exists, that its type exists and that no other organization within
     * the branch has the same displayIdentifier as it does.
     * @param uploadedOrgs List<UploadedOrganization>
     * @param invalidOrgs List<UploadedOrganization>
     * @param currentContext {@link Organization}
     * @return int - the number of created organizations
     * @throws ServiceException ServiceException
     */
    private int checkUploadedOrgs(List<UploadedOrganization> uploadedOrgs,
            List<UploadedOrganization> invalidOrgs, Organization currentContext) throws ServiceException {
        logger.trace("Entering the checkUploadedOrgs() method.");
        int createdOrgs = 0;

        if (uploadedOrgs.size() > 0) {
            Map<String, List<UploadedOrganization>> orderedOrgs = convertListToMap(uploadedOrgs, invalidOrgs);
            List<OrganizationType> orderedTypes = getOrderedTypes(orderedOrgs, invalidOrgs);

            //TODO - iterate over that list, finding the organization types that have matching typeCodes in the keySet of the map.
            for (OrganizationType orgType : orderedTypes) {
                for (UploadedOrganization uploadedOrg : orderedOrgs.get(orgType.getTypeCode().toUpperCase())) {
                    if (isValidOrganization(uploadedOrg, currentContext)) {
                        boolean hasParent = hasParent(uploadedOrg, currentContext);
                        if (hasParent) {
                            uploadedOrg.addInvalidField("Organization", uploadedOrg.getDisplayIdentifier());
                            invalidOrgs.add(uploadedOrg);
                        } else {
                        	//TODO change it to one service call so that orphan organizations
                        	//are not getting created.
                            orgService.add(uploadedOrg.getOrganization());
                            createdOrgs++;
                        }
                    } else {
                        invalidOrgs.add(uploadedOrg);
                    }
                }
            }
//            
//            for (String key : orderedOrgs.keySet()) {
//                for (UploadedOrganization uploadedOrg : orderedOrgs.get(key)) {
//                    if (isValidOrganization(uploadedOrg, currentContext)) {
//                        boolean hasParent = hasParent(uploadedOrg, currentContext);
//                            // add the parent relationship to the organization.
//                            if (hasParent) {
//                                // error case, add invalid detail to the uploaded organization
//                                uploadedOrg.addInvalidField("Organization", uploadedOrg.getDisplayIdentifier());
//                                invalidOrgs.add(uploadedOrg);
//                            } else {
//                                // add the parent relationship to the organization.
//                                orgService.add(uploadedOrg.getOrganization());
//                                createdOrgs++;
//                            }
//                    } else {
//                        //add to invalidOrgs list.
//                        invalidOrgs.add(uploadedOrg);
//                    }
//                }
//            }
        }

        logger.trace("Leaving the checkUploadedOrgs() method.");
        return createdOrgs;
    }

    /**
     * Checks that the values necessary for an organization to exists in the database exist.
     * @param uploadedOrganization {@link UploadedOrganization}
     * @param currentContext {@link Organization}
     * @return boolean whether the organization's values are valid.
     */
    private boolean isValidOrganization(UploadedOrganization uploadedOrganization, Organization currentContext) {
        boolean returnVal = true;

        if (uploadedOrganization.getInValidDetails().size() > 0 || !organizationTypeExists(uploadedOrganization)
                || !organizationParentExists(uploadedOrganization, currentContext)) {
            returnVal = false;
        }

        return returnVal;
    }

    /**
     * Checks that the organization type referenced by the uploaded organization record exists within the system.
     * @param uploadedOrganization {@link UploadedOrganization}
     * @return boolean whether the specified Organization Type exists within the system.
     */
    private boolean organizationTypeExists(UploadedOrganization uploadedOrganization) {
        // check that the organization type exists within the system.
        boolean returnVal = true;

        OrganizationType orgType = orgTypeService
                .getByTypeCodeCaseInsensitive(uploadedOrganization.getOrganizationTypeCode().toUpperCase());
        if (orgType != null) {
            //set the organization type on the organization
            uploadedOrganization.setOrganizationType(orgType);
        } else {
            returnVal = false;
            uploadedOrganization.addInvalidField("OrganizationTypeCode", uploadedOrganization.getOrganizationTypeCode());
        }

        return returnVal;
    }

    /**
     * Checks that the parent organization referenced by the uploaded organization record exists within the system.
     * @param uploadedOrganization {@link UploadedOrganization}
     * @param contextOrg {@link Organization}
     * @return boolean whether the parent Organization already exists.
     */
    private boolean organizationParentExists(UploadedOrganization uploadedOrganization, Organization contextOrg) {
        // check that the parent exists within the system.
        boolean returnVal = true;

        Organization parent = orgService.getByDisplayIdWithContext(uploadedOrganization.getParentDisplayIdentifier(),
                contextOrg.getId());
        if (parent != null) {
            //set the parent on the organization.
            uploadedOrganization.setParentOrganizationId(parent.getId());
        } else {
            returnVal = false;
            uploadedOrganization.addInvalidField("Parent Organization", uploadedOrganization.getParentDisplayIdentifier());
        }

        return returnVal;
    }

    /**
     * Checks if the uploaded organization has a parent relationship with the organization referenced by the
     * parentDisplayIdentifier.
     * @param uploadedOrganization {@link UploadedOrganization}
     * @param contextOrg {@link Organization}
     * @return boolean whether the specified parent relationship for the uploaded organization already exists.
     */
    private boolean hasParent(UploadedOrganization uploadedOrganization, Organization contextOrg) {
        boolean returnVal = false;
        Organization duplicate = orgService.getByDisplayIdWithFullContext(uploadedOrganization.getDisplayIdentifier(),
                contextOrg.getId());

        if (duplicate != null) {
            returnVal = true;
            List<Organization> parentOrganizations = orgService.getImmediateParents(duplicate.getId());

            if (parentOrganizations != null) {
                // If the parent relationship exists, then the organization is not valid.
                boolean found = false;
                for (Organization parent : parentOrganizations) {
                    if (parent.getId() == uploadedOrganization.getParentOrganizationId()) {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    //create the parent-child relationship
                	//TODO change it to one service call, to prevent orphan organizations from being created.
                    orgService.addParentOrganization(duplicate.getId(), uploadedOrganization.getParentOrganizationId());
                }
            }
        }

        return returnVal;
    }

    /**
     * Takes the list of uploaded organizations and place them into buckets by their organization type.
     * @param uploadedOrgs List<UploadedOrganization>
     * @param invalidOrgs List<UploadedOrganization>
     * @return Map<String, List<UploadedOrganization>>
     */
    private Map<String, List<UploadedOrganization>> convertListToMap(List<UploadedOrganization> uploadedOrgs,
            List<UploadedOrganization> invalidOrgs) {
        Map<String, List<UploadedOrganization>> orderedOrgs = new HashMap<String, List<UploadedOrganization>>();

        for (UploadedOrganization uploadedOrg : uploadedOrgs) {
            if (uploadedOrg.getInValidDetails().size() == 0) {
                String orgTypeCode = uploadedOrg.getOrganizationTypeCode().toUpperCase();
                List<UploadedOrganization> value;
                if (orderedOrgs.containsKey(orgTypeCode)) {
                    value = orderedOrgs.get(orgTypeCode);
                } else {
                    value = new ArrayList<UploadedOrganization>();
                }
                value.add(uploadedOrg);
                orderedOrgs.put(orgTypeCode, value);
            } else {
                invalidOrgs.add(uploadedOrg);
            }
        }

        return orderedOrgs;
    }

    /**
     *
     *@param orderedOrgs Map<String, List<UploadedOrganization>>
     * @param invalidOrgs List<UploadedOrganization>
     *@return List<OrganizationType>
     */
    private List<OrganizationType> getOrderedTypes(Map<String, List<UploadedOrganization>> orderedOrgs,
    		List<UploadedOrganization> invalidOrgs) {
        List<OrganizationType> orgTypes = orgTypeService.getAll();
        List<OrganizationType> orderedTypes = new ArrayList<OrganizationType>();

        for (String key : orderedOrgs.keySet()) {
            boolean found = false;
            for (OrganizationType orgType : orgTypes) {
                if (orgType.getTypeCode().toUpperCase().equals(key)) {
                    orderedTypes.add(orgType);
                    found = true;
                }
            }

            if (!found) {
                for (UploadedOrganization uploadedOrg : orderedOrgs.get(key)) {
                    uploadedOrg.addInvalidField("OrganizationType", key);
                    if (invalidOrgs == null) {
                        invalidOrgs = new ArrayList<UploadedOrganization>();
                    }

                    invalidOrgs.add(uploadedOrg);
                }
            }
        }

        Collections.sort(orderedTypes, new Comparator<OrganizationType>() {
            @Override
            public int compare(OrganizationType o1, OrganizationType o2) {
                return o1.getTypeLevel() - o2.getTypeLevel();
            }
        });

        return orderedTypes;
    }

    /**
     *
     */
    private void setRecordTypesMap() {
        //get category type.
        CategoryTypeExample categoryTypeExample = new CategoryTypeExample();
        categoryTypeExample.createCriteria().andTypeCodeEqualTo(orgUploadSpecification.getCsvRecordTypeCode());
        //This should not be empty.Failure to deploy is the expected behavior.
        List<CategoryType> categoryTypes = categoryTypeDao.selectByExample(categoryTypeExample);
        if (CollectionUtils.isEmpty(categoryTypes)) {
            logger.debug("No category Types");
            return;
        }
        CategoryExample categoryExample = new CategoryExample();
        //there is only one category type for record type.
        categoryExample.createCriteria().andCategoryTypeIdEqualTo(categoryTypes.get(0).getId());
        recordTypeMap = new HashMap<Long, Category>();
        List<Category> recordTypes = categoryService.selectByExample(categoryExample);
        if (CollectionUtils.isEmpty(recordTypes)) {
            logger.debug("No categories of record type");
            return;
        }
        for (Category recordType:recordTypes) {
            recordTypeMap.put(recordType.getId(), recordType);
        }
    }

    /**
     * @return Map<String, FieldSpecification>
     */
    private Map<String, FieldSpecification> setOrgFieldSpecificationMap() {
        Map<String, FieldSpecification> userFieldSpecificationMap = new HashMap<String, FieldSpecification>();
        FieldSpecificationExample fieldSpecificationExample = new FieldSpecificationExample();
        Category userRecordType = getRecordType(orgUploadSpecification.getOrgRecordType());
        if (userRecordType == null) {
            return null;
        }
        fieldSpecificationExample.createCriteria().andRecordTypeIdEqualTo(userRecordType.getId());
        List<FieldSpecification> userFieldSpecifications = fieldSpecificationDao.selectByExample(fieldSpecificationExample);

        if (CollectionUtils.isNotEmpty(userFieldSpecifications)) {
            for (FieldSpecification userFieldSpecification : userFieldSpecifications) {
                userFieldSpecificationMap.put(userFieldSpecification.getFieldName(), userFieldSpecification);
            }
        }

        return userFieldSpecificationMap;
    }

    /**
     * This should not be empty.Failure to deploy is the expected behavior.In this case it will throw null pointer.
     * @param recordTypeCode {@link String}
     * @return {@link Category}
     */
    private Category getRecordType(String recordTypeCode) {
        for (Category recordType : recordTypeMap.values()) {
            if (recordType.getCategoryCode().equalsIgnoreCase(recordTypeCode)) {
                return recordType;
            }
        }
        return null;
    }

    /**
     * Setter for the messageSource.
     * @param arg0 {@link MessageSource}
     */
    @Override
    public final void setMessageSource(MessageSource arg0) {
        this.messageSource = arg0;
    }
}
