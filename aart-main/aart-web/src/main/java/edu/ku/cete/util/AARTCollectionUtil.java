/**
 * 
 */
package edu.ku.cete.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.StringUtils;

import edu.ku.cete.domain.property.Identifiable;

/**
 * @author m802r921
 *
 */
public class AARTCollectionUtil {
	/**
	 * Uses spring core reflection. do not use for large result sets.
	 * Invokes the getter of the field by reflection and returns the
	 * list of that string field.
	 * @param inputRecords {@link List}
	 * @return {@link List}
	 */
	public static List<String> getIds(List<? extends Object> inputRecords,String attributeName) {
		List<String> ids = new ArrayList<String>();
		if (inputRecords != null && CollectionUtils.isNotEmpty(inputRecords)) {
			for (Object inputRecord: inputRecords) {
				BeanWrapperImpl beanImpl = new BeanWrapperImpl(inputRecord);
				Object attributeValue = null;
				String attributeStringValue = null;
				if(beanImpl != null) {
					attributeValue = beanImpl.getPropertyValue(attributeName);
				}
				if(attributeValue != null) {
					attributeStringValue = (String) attributeValue;
				}
				if (attributeStringValue != null && StringUtils.hasText(attributeStringValue)) {
					ids.add(attributeStringValue);
				}
			}
		}
		return ids;
	}
	/**
	 * @param identifiables {@link List}
	 * @return {@link List}
	 */
	public static List<Long> getIds(Collection<? extends Identifiable> identifiables) {
		List<Long> ids = new ArrayList<Long>();
		if (identifiables != null && CollectionUtils.isNotEmpty(identifiables)) {
			for (Identifiable identifiable: identifiables) {
				if (identifiable != null && identifiable.getId() != null
						&&
						identifiable.getId() > 0) {
					ids.add(identifiable.getId());
				}
			}
		}
		return ids;
	}
	/**
	 * @param identifiables {@link List}
	 * @param order {@link Integer}
	 * @return {@link List}
	 */
	public static List<Long> getIds(List<? extends Identifiable> identifiables, int order) {
		List<Long> ids = new ArrayList<Long>();
		if (identifiables != null && CollectionUtils.isNotEmpty(identifiables)) {
			for (Identifiable identifiable: identifiables) {
				if (identifiable != null && identifiable.getId(order) != null
						&&
						identifiable.getId(order) > 0) {
					ids.add(identifiable.getId(order));
				}
			}
		}
		return ids;
	}
	
	/**
	 * @param validateableRecords
	 * @param order
	 * @return
	 */
	public static List<String> getStringIdentifiers(List<? extends Identifiable> identifiables, int order) {
		List<String> ids = new ArrayList<String>();
		if (identifiables != null && CollectionUtils.isNotEmpty(identifiables)) {
			for (Identifiable identifiable: identifiables) {
				if (identifiable != null && identifiable.getStringIdentifier(order) != null
						&&
						identifiable.getStringIdentifier(order) != null) {
					ids.add(identifiable.getStringIdentifier(order));
				}
			}
		}
		return ids;
	}	
	public static List<Long> getListAsLongType(String[] listValues) {
		List<Long> ids = new ArrayList<Long>();
		if (listValues != null && ArrayUtils.isNotEmpty(listValues)) {
			for (String listVal: listValues) {
				if(listVal != null && org.apache.commons.lang3.StringUtils.isNumeric(listVal))
				ids.add(Long.parseLong(listVal));				
			}
		}
		return ids;
	}
	//TODO write a generic method for this kind of parsing
	/**
	 * @param listValues
	 * @return
	 */
	public static List<Long> getListAsLongType(List<String> listValues) {
		List<Long> ids = new ArrayList<Long>();
		if (listValues != null && CollectionUtils.isNotEmpty(listValues)) {
			for (String listVal: listValues) {
				if(listVal != null && org.apache.commons.lang3.StringUtils.isNumeric(listVal))
				ids.add(Long.parseLong(listVal));				
			}
		}
		return ids;
	}
	
	/**
	 * @param stringMap
	 * @return
	 */
	public static String searchKeyInMap(Map<String,String> stringMap,String searchKey) {
		String result = exactSearchKeyInMap(stringMap, searchKey);
		if(result == null
				&& stringMap != null && searchKey != null && StringUtils.hasText(searchKey)) {
			for(String key: stringMap.keySet()) {
				if(key != null && StringUtils.hasText(key)
						&& key.contains(searchKey)) {
					result = stringMap.get(key);
				}
			}
		}
		return result;
	}
	
	/**
	 * @param stringMap
	 * @return
	 */
	public static String exactSearchKeyInMap(Map<String,String> stringMap,String searchKey) {
		String result = null;
		if(stringMap != null && searchKey != null && StringUtils.hasText(searchKey)) {
			for(String key: stringMap.keySet()) {
				if(key != null && StringUtils.hasText(key)
						&& key.equalsIgnoreCase(searchKey)) {
					result = stringMap.get(key);
				}
			}
		}
		return result;
	}
	
	public static Long getRandomElement(List<Long> inIds) {
		Random generator = new Random();
		Long result = null;
		if(inIds != null && CollectionUtils.isNotEmpty(inIds)) {
			result = inIds.get(generator.nextInt(inIds.size()));
		}
		return result;
	}
	public static List<Long> getList(Long inputTestId) {
		List<Long> outList = new ArrayList<Long>();
		if(inputTestId != null) {
			outList.add(inputTestId);
		}
		return outList;
	}
	public static <T> boolean contains(T[] inArray, T toBeFoundElement) {
		boolean found = false;
		if(inArray != null
				&& inArray.length > 0) {
			for(T inArrayElement:inArray) {
				if(!found && inArrayElement != null) {
					found = inArrayElement.equals(toBeFoundElement);
				}
			}
			
		}
		return found;
	}
	
	/**
	 * merge the values of string in the 2 given hash maps.
	 * @param initial
	 * @param append
	 * @return
	 */
	public static Map<String, String> merge(
			Map<String, String> initial,
			Map<String, String> append) {
		Map<String, String> result = new HashMap<String, String>();
		if(initial != null && MapUtils.isNotEmpty(initial)) {
			result = initial;
		}
		if(append != null && MapUtils.isNotEmpty(append)) {
			for(String appendKey:append.keySet()) {
				if(result.containsKey(appendKey)) {
					String existingValue = result.get(appendKey);
					String appendValue = append.get(appendKey);
					if(existingValue != null 
							&& appendValue != null
							&& ! existingValue.equalsIgnoreCase(appendValue)) {
						result.put(appendKey,
								existingValue + ParsingConstants.OUTER_DELIM + appendValue);
						} else if(appendValue != null) {
							result.put(appendKey,
									appendValue);						
							}
				} else {
					result.put(appendKey,
							append.get(appendKey));					
					}
			}
		}
		return result;
	}
	
}
