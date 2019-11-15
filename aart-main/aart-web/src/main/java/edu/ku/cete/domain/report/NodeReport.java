package edu.ku.cete.domain.report;

import java.math.BigDecimal;
import java.util.Set;

import org.springframework.util.StringUtils;

import edu.ku.cete.configuration.LmAttributeConfiguration;
import edu.ku.cete.domain.common.Reportable;
import edu.ku.cete.domain.lm.LmNode;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.StringUtil;

public class NodeReport extends NodeReportKey implements Reportable{
	/**
	 * autogenerated id for the sake of report.
	 */
	private Long id;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.state_student_identifier
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    private String stateStudentIdentifier;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.legal_first_name
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    private String legalFirstName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.legal_middle_name
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    private String legalMiddleName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.legal_last_name
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    private String legalLastName;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.comprehensive_race
     *
     * @mbggenerated Thu Mar 28 17:00:05 CDT 2013
     */
    private String comprehensiveRace;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.gender
     *
     * @mbggenerated Thu Mar 28 17:00:05 CDT 2013
     */
    private Integer gender;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.primary_disability_code
     *
     * @mbggenerated Thu Mar 28 17:00:05 CDT 2013
     */
    private String primaryDisabilityCode;
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.node_key
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    private String nodeKey;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.test_name
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    private String testName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.test_status_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    private Long testStatusId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.test_collection_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    private Long testCollectionId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.test_collection_name
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    private String testCollectionName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column public.node_report.no_of_responses
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    private Long noOfResponses;
    
    /**
     * noOfCorrectResponses.
     */
    private Long noOfCorrectResponses;
    
    /**
     * noOfIncorrectResponses.
     */
    private Long noOfIncorrectResponses;
    
    /**
     * noOfAnsweredItems.
     */
    private Long noOfAnsweredItems;
    /**
     * totalRawScore.
     */
    private BigDecimal totalRawScore;
    /**
     * lmNode.
     */
    private LmNode lmNode;
    /**
     * essentialElementsCode
     */
    private String essentialElementsCode;
    
    /**
     * essentialElementsDescription
     */
    private String essentialElementsDescription;
    
    /**
     * noOfItemsPresented
     */
    private Long noOfItemsPresented;
	/**
	 * lmAttributeConfiguration.
	 */
	private LmAttributeConfiguration lmAttributeConfiguration;
	
	private String testSessionName;
	/**
	 * @return the id
	 */
	public final Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public final void setId(Long id) {
		this.id = id;
	}    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.node_report.state_student_identifier
     *
     * @return the value of public.node_report.state_student_identifier
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public String getStateStudentIdentifier() {
        return stateStudentIdentifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.node_report.state_student_identifier
     *
     * @param stateStudentIdentifier the value for public.node_report.state_student_identifier
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public void setStateStudentIdentifier(String stateStudentIdentifier) {
        this.stateStudentIdentifier = stateStudentIdentifier;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.node_report.legal_first_name
     *
     * @return the value of public.node_report.legal_first_name
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public String getLegalFirstName() {
        return legalFirstName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.node_report.legal_first_name
     *
     * @param legalFirstName the value for public.node_report.legal_first_name
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public void setLegalFirstName(String legalFirstName) {
        this.legalFirstName = legalFirstName == null ? null : legalFirstName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.node_report.legal_middle_name
     *
     * @return the value of public.node_report.legal_middle_name
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public String getLegalMiddleName() {
        return legalMiddleName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.node_report.legal_middle_name
     *
     * @param legalMiddleName the value for public.node_report.legal_middle_name
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public void setLegalMiddleName(String legalMiddleName) {
        this.legalMiddleName = legalMiddleName == null ? null : legalMiddleName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.node_report.legal_last_name
     *
     * @return the value of public.node_report.legal_last_name
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public String getLegalLastName() {
        return legalLastName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.node_report.legal_last_name
     *
     * @param legalLastName the value for public.node_report.legal_last_name
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public void setLegalLastName(String legalLastName) {
        this.legalLastName = legalLastName == null ? null : legalLastName.trim();
    }

    /**
	 * @return the comprehensiveRace
	 */
	public final String getComprehensiveRace() {
		return comprehensiveRace;
	}

	/**
	 * @param comprehensiveRace the comprehensiveRace to set
	 */
	public final void setComprehensiveRace(String comprehensiveRace) {
		this.comprehensiveRace = comprehensiveRace;
	}

	/**
	 * @return the gender
	 */
	public final Integer getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public final void setGender(Integer gender) {
		this.gender = gender;
	}

	/**
	 * @return the primaryDisabilityCode
	 */
	public final String getPrimaryDisabilityCode() {
		return primaryDisabilityCode;
	}

	/**
	 * @param primaryDisabilityCode the primaryDisabilityCode to set
	 */
	public final void setPrimaryDisabilityCode(String primaryDisabilityCode) {
		this.primaryDisabilityCode = primaryDisabilityCode;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.node_report.node_key
     *
     * @return the value of public.node_report.node_key
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public String getNodeKey() {
        return nodeKey;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.node_report.node_key
     *
     * @param nodeKey the value for public.node_report.node_key
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey == null ? null : nodeKey.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.node_report.test_name
     *
     * @return the value of public.node_report.test_name
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public String getTestName() {
        return testName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.node_report.test_name
     *
     * @param testName the value for public.node_report.test_name
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public void setTestName(String testName) {
        this.testName = testName == null ? null : testName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.node_report.test_status_id
     *
     * @return the value of public.node_report.test_status_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public Long getTestStatusId() {
        return testStatusId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.node_report.test_status_id
     *
     * @param testStatusId the value for public.node_report.test_status_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public void setTestStatusId(Long testStatusId) {
        this.testStatusId = testStatusId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.node_report.test_collection_id
     *
     * @return the value of public.node_report.test_collection_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public Long getTestCollectionId() {
        return testCollectionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.node_report.test_collection_id
     *
     * @param testCollectionId the value for public.node_report.test_collection_id
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public void setTestCollectionId(Long testCollectionId) {
        this.testCollectionId = testCollectionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.node_report.test_collection_name
     *
     * @return the value of public.node_report.test_collection_name
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public String getTestCollectionName() {
        return testCollectionName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.node_report.test_collection_name
     *
     * @param testCollectionName the value for public.node_report.test_collection_name
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public void setTestCollectionName(String testCollectionName) {
        this.testCollectionName = testCollectionName == null ? null : testCollectionName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column public.node_report.no_of_responses
     *
     * @return the value of public.node_report.no_of_responses
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public Long getNoOfResponses() {
        return noOfResponses;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column public.node_report.no_of_responses
     *
     * @param noOfResponses the value for public.node_report.no_of_responses
     *
     * @mbggenerated Fri Feb 01 18:40:42 CST 2013
     */
    public void setNoOfResponses(Long noOfResponses) {
        this.noOfResponses = noOfResponses;
    }

	/**
	 * @return the noOfCorrectResponses
	 */
	public final Long getNoOfCorrectResponses() {
		return noOfCorrectResponses;
	}

	/**
	 * @param noOfCorrectResponses the noOfCorrectResponses to set
	 */
	public final void setNoOfCorrectResponses(Long noOfCorrectResponses) {
		this.noOfCorrectResponses = noOfCorrectResponses;
	}

	/**
	 * @param noOfCorrectResponses the noOfCorrectResponses to set
	 */
	public final void addNoOfCorrectResponses(Long noOfCorrectResponses) {
		this.noOfCorrectResponses
		= this.noOfCorrectResponses + noOfCorrectResponses;
	}
	/**
	 * @return the noOfIncorrectResponses
	 */
	public final Long getNoOfIncorrectResponses() {
		return noOfIncorrectResponses;
	}

	/**
	 * @param noOfIncorrectResponses the noOfIncorrectResponses to set
	 */
	public final void setNoOfIncorrectResponses(Long noOfIncorrectResponses) {
		this.noOfIncorrectResponses = noOfIncorrectResponses;
	}
	/**
	 * @param noOfIncorrectResponses the noOfIncorrectResponses to set
	 */
	public final void addNoOfIncorrectResponses(Long noOfIncorrectResponses) {
		this.noOfIncorrectResponses
		= this.noOfIncorrectResponses + noOfIncorrectResponses;
	}

	/**
	 * @param nodeReportItem
	 */
//	public void setNoOfResponses(NodeReport nodeReportItem) {
//		if(nodeReportItem != null &&
//				nodeReportItem.getStudentNodeReportKey().equals(
//						getStudentNodeReportKey())
//				) {
//			this.addNoOfCorrectResponses(
//					nodeReportItem.getNoOfCorrectResponses());
//			this.addNoOfIncorrectResponses(
//					nodeReportItem.getNoOfIncorrectResponses());
//
//		}
//	}

	/**
	 * @return the lmNode
	 */
	public LmNode getLmNode() {
		return lmNode;
	}

	/**
	 * @param lmNode the lmNode to set
	 */
	public void setLmNode(LmNode lmNode) {
		this.lmNode = lmNode;
	}

	/**
	 * @return
	 */
	public String getEssentialElementsCode() {
		return essentialElementsCode;
	}

	/**
	 * @param essentialElementsCode
	 */
	public void setEssentialElementsCode(String essentialElementsCode) {
		this.essentialElementsCode = essentialElementsCode;
	}

	/**
	 * @return
	 */
	public String getEssentialElementsDescription() {
		return essentialElementsDescription;
	}

	/**
	 * @param essentialElementsDescription
	 */
	public void setEssentialElementsDescription(String essentialElementsDescription) {
		this.essentialElementsDescription = essentialElementsDescription;
	}

	/**
	 * @return
	 */
	public Long getNoOfItemsPresented() {
		return noOfItemsPresented;
	}

	/**
	 * @param noOfItemsPresented
	 */
	public void setNoOfItemsPresented(Long noOfItemsPresented) {
		this.noOfItemsPresented = noOfItemsPresented;
	}

	/**
	 * @return the noOfAnsweredItems
	 */
	public Long getNoOfAnsweredItems() {
		return noOfAnsweredItems;
	}

	/**
	 * @param noOfAnsweredItems the noOfAnsweredItems to set
	 */
	public void setNoOfAnsweredItems(Long noOfAnsweredItems) {
		this.noOfAnsweredItems = noOfAnsweredItems;
	}

	/**
	 * @return the totalRawScore
	 */
	public BigDecimal getTotalRawScore() {
		return totalRawScore;
	}

	/**
	 * @param totalRawScore the totalRawScore to set
	 */
	public void setTotalRawScore(BigDecimal totalRawScore) {
		this.totalRawScore = totalRawScore;
	}

	public void setLmNode(Set<LmNode> lmNodes) {
		if(getNodeKey() != null){
			for(LmNode lmNode:lmNodes) {
				if(lmNode!=null && getLmNode() ==null
						&& lmNode.getNodeKey().equals(getNodeKey())) {
					setLmNode(lmNode);
				}
			}
		}
	}
	
	public void setLmAttributeConfiguration(
			LmAttributeConfiguration lmAttributeConfiguration) {
		this.lmAttributeConfiguration = lmAttributeConfiguration;
	}
	
	public LmAttributeConfiguration getLmAttributeConfiguration() {
		return lmAttributeConfiguration;
	}

	public String getTestSessionName() {
		return testSessionName;
	}

	public void setTestSessionName(String testSessionName) {
		this.testSessionName = testSessionName;
	}

	@Override
	public int getNumberOfAttributes() {
		return 35;
	}

	
	/**
	 * @param i
	 * @param lmAttributeConfiguration
	 * @return
	 */
	public String getAttribute(int i, LmAttributeConfiguration lmAttributeConfiguration) {
		String result = null;
		if(i == 0) {
			result = StringUtil.convert(
					getStudentsTestsId(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i==1) {
			result = StringUtil.convert(
					getStudentId(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 2) {
			result = StringUtil.convert(
					getStateStudentIdentifier(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 3) {
			result = StringUtil.convert(
					getLegalFirstName(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 4) {
			result = StringUtil.convert(
					getLegalMiddleName(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 5) {
			result = StringUtil.convert(
					getLegalLastName(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 6) {
			result = StringUtil.convert(
					getComprehensiveRace(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 7) {
			result = StringUtil.convert(
					getGender(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 8) {
			result = StringUtil.convert(
					getPrimaryDisabilityCode(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 9) {
			result = StringUtil.convert(
					getTestId(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 10) {
			result = StringUtil.convert(
					getTestName(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 11) {
			result = StringUtil.convert(
					getTestCollectionId(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 12) {
			result = StringUtil.convert(
					getTestCollectionName(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 13) {
			if(getLmNode() != null) {
				result = StringUtil.convert(
						getLmNode().getNodeId(),
						ParsingConstants.NOT_AVAILABLE);
			} if(result == null
					|| !StringUtils.hasText(result)) {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 14) {
			result = StringUtil.convert(
					getNodeKey(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 15) {
			result = StringUtil.convert(
					getNoOfCorrectResponses(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 16) {
			result = StringUtil.convert(
					getNoOfIncorrectResponses(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 17) {
			result = StringUtil.convert(
					getNoOfResponses(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 18) {
			result = StringUtil.convert(
					getNoOfItemsPresented(),
					ParsingConstants.NOT_AVAILABLE);
		}  else if(i == 19) {
			result = StringUtil.convert(
					getNoOfAnsweredItems(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 20) {
			result = StringUtil.convert(
					getTotalRawScore(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 21) {
			if(getLmNode() != null) {
				result = StringUtil.convert(
						getLmNode().getProfiles(),
						ParsingConstants.NOT_AVAILABLE);
			} if(result == null
					|| !StringUtils.hasText(result)) {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 22) {
			if(getLmNode() != null) {
				result = StringUtil.convert(
						getLmNode().getAttributeInformation(lmAttributeConfiguration),
						ParsingConstants.NOT_AVAILABLE);
			} if(result == null
					|| !StringUtils.hasText(result)) {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 23) {			
			result = StringUtil.convert(
					getEssentialElementsCode(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 24) {			
			result = StringUtil.convert(
					getEssentialElementsDescription(),
					ParsingConstants.NOT_AVAILABLE);
		} else if(i == 25) {
			if(getLmNode() != null) {
				result = StringUtil.convert(
						getLmNode().getEssentialElement(lmAttributeConfiguration),
						ParsingConstants.NOT_AVAILABLE);
			} if(result == null
					|| !StringUtils.hasText(result)) {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		}else if(i == 26) {
			if(getLmNode() != null) {
				result = StringUtil.convert(
						getLmNode().getConceptualArea(lmAttributeConfiguration),
						ParsingConstants.NOT_AVAILABLE);
			} if(result == null
					|| !StringUtils.hasText(result)) {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 27) {
			if(getLmNode() != null) {
				result = StringUtil.convert(
						getLmNode().getBand(lmAttributeConfiguration),
						ParsingConstants.NOT_AVAILABLE);
			} if(result == null
					|| !StringUtils.hasText(result)) {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 28) {
			if(getLmNode() != null) {
				result = StringUtil.convert(
						getLmNode().getCAStandard(lmAttributeConfiguration),
						ParsingConstants.NOT_AVAILABLE);
			} if(result == null
					|| !StringUtils.hasText(result)) {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 29) {
			if(getLmNode() != null) {
				result = StringUtil.convert(
						getLmNode().getInitialPrecursor(lmAttributeConfiguration),
						ParsingConstants.NOT_AVAILABLE);
			} if(result == null
					|| !StringUtils.hasText(result)) {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 30) {
			if(getLmNode() != null) {
				result = StringUtil.convert(
						getLmNode().getDistalPrecursor(lmAttributeConfiguration),
						ParsingConstants.NOT_AVAILABLE);
			} if(result == null
					|| !StringUtils.hasText(result)) {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 31) {
			if(getLmNode() != null) {
				result = StringUtil.convert(
						getLmNode().getProximalPrecursor(lmAttributeConfiguration),
						ParsingConstants.NOT_AVAILABLE);
			} if(result == null
					|| !StringUtils.hasText(result)) {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 32) {
			if(getLmNode() != null) {
				result = StringUtil.convert(
						getLmNode().getTarget(lmAttributeConfiguration),
						ParsingConstants.NOT_AVAILABLE);
			} if(result == null
					|| !StringUtils.hasText(result)) {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		} else if(i == 33) {
			if(getLmNode() != null) {
				result = StringUtil.convert(
						getLmNode().getSuccessor(lmAttributeConfiguration),
						ParsingConstants.NOT_AVAILABLE);
			} if(result == null
					|| !StringUtils.hasText(result)) {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		}else if(i == 34) {
			if(getTestSessionName() != null) {
				result = getTestSessionName();
			} if(result == null
					|| !StringUtils.hasText(result)) {
				result = ParsingConstants.NOT_AVAILABLE;
			}
		}
		return result;
	}

	
	/**
	 * @param lmAttributeConfiguration
	 * @return
	 */
	public String[] getAttributes(LmAttributeConfiguration lmAttributeConfiguration) {
		String[] result = new String[getNumberOfAttributes()];
		for(int i=0;i<result.length;i++) {
			result[i] = getAttribute(i, lmAttributeConfiguration);
		}
		return result;
	}

	@Override
	public String getAttribute(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	
}