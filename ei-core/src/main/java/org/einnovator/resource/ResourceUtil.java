/**
 * 
 */
package org.einnovator.resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.einnovator.meta.MetaUtil;

/**
 * A ResourceUtil.
 * 
 * @author jsimao
 */
public class ResourceUtil {

	static public Map<String, String[]> splitResourcesByPrefix(String[] resources, boolean keepPrefixes) {
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		for (String url: resources) {
			String prefix = getResourcePrefix(url);
			List<String> l = map.get(prefix);
			if (l==null) {
				l = new ArrayList<String>();
				map.put(prefix, l);
			}
			l.add(keepPrefixes ? url : getResourceName(url));
		}
		//build simplified (List free) output Map
		Map<String, String[]> out = new HashMap<String, String[]>();		
		for (Map.Entry<String, List<String>> e : map.entrySet()) {
			String[] a = new String[e.getValue().size()];
			out.put(e.getKey(), a);
			int i = 0;
			for (String name: e.getValue()) {
				a[i++] = name;
			}
		}
		return out;
	}

	static public String getResourcePrefix(String url) {
		int i = url.indexOf(':');
		return (i > 0) ? url.substring(0, i) : "";
	}

	static public String getResourceName(String url) {
		int i = url.indexOf(':');
		return (i >= 0) ? url.substring(i + 1) : url;
	}

	/**
	 * Create a {@code classpath:*} prefix resource name, with a specified filename and the
	 * same directory as the package of a type.
	 * 
	 * @param type the type that determines the package/directory of the resource
	 * @param filename the resource file name
	 * @return thre classpath prefix resource name
	 */
	static public String makeClasspathResourceName(Class<?> type, String filename) {
		return "classpath:" + MetaUtil.getPackageName(type).replace(".",  "/")
				+ "/" + filename;

	}

}
