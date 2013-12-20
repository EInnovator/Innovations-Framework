/**
 * 
 */
package org.einnovator.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A {@code MapUtil}.
 *
 * @author Jorge Simão
 */
public class MapUtil {


	public static Map<String, List<String>> mapToListValues(Map<String, String> map) {
		Map<String, List<String>> map2 = new LinkedHashMap<String, List<String>>();
		for (Map.Entry<String, String> e: map.entrySet()) {
			List<String> values = map2.get(e.getKey());
			if (values==null) {
				values = new ArrayList<String>();
				map2.put(e.getKey(), values);
			}
			values.add(e.getValue());
		}
		return map2;
	}

}
