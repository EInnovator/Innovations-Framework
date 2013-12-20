package org.einnovator.validation;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.einnovator.log.Logger;
import org.einnovator.log.LoggerUtil;



/**
 * A Collection of errors from binding, validation, or other application defined.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class Errors {
	
	public static final Logger log = LoggerUtil.getLogger(Errors.class);
	
	private Map<String, List<Error>> errorsMap;

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code Errors}.
	 *
	 */
	public Errors() {
	}

	//
	// Builders
	//
	
	/**
	 * @param error
	 */
	public void addError(Error error) {
		if (errorsMap==null) {
			errorsMap = new LinkedHashMap<String, List<Error>>();
		}
		log.info(this,  "addError:", error);
		List<Error> errorList = errorsMap.get(error.getPath());
		if (errorList==null) {
			errorList = new ArrayList<Error>();
			errorsMap.put(error.getPath(), errorList);
		}
		errorList.add(error);
	}
	
	public void addError(Object invalidValue, String path, String key, String message, Object... params) {
		addError(new Error(invalidValue, path, key, message, params));
	}
	
	
	public List<Error> getErrors(String path) {
		return errorsMap!=null ? errorsMap.get(path) : null;
	}

	public String messages(String path) {
		List<Error> errorList = errorsMap.get(path);
		if (errorList!=null) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i<errorList.size(); i++) {
				sb.append(errorList.get(i).getRequiredMessage());
				if (i<errorList.size()-1) {
					sb.append(";");
				}
			}
			return sb.toString();
		} else {
			return "";
		}
	}
	
	public void addAll(Errors errorsMap) {
		if (errorsMap.ok()) {
			return;
		}
		for (List<Error> errorList: errorsMap.getErrors().values()) {
			for (Error error: errorList) {
				addError(error);				
			}
		}
	}

	/**
	 * Check whether any {@code Error} was added.
	 * 
	 * @return <code>true</code>, if no {@code Error} was added; <code>false</code> otherwise.
	 */
	public boolean isEmpty() {
		return errorsMap==null || errorsMap.size()==0;
	}

	
	/**
	 * Check whether any {@code Error} was added.
	 * 
	 * (same as {@link #isEmpty()})
	 * @return <code>true</code>, if no {@code Error} was added; <code>false</code> otherwise.
	 */
	public boolean ok() {
		return isEmpty();
	}

	public boolean ok(String path) {
		if (ok()) {
			return true;
		}
		List<Error> errorList = getErrors(path);
		return errorList== null || errorList.isEmpty();
	}

	public int size() {
		return errorsMap==null ? 0 : errorsMap.size();		
	}

	public Map<String, List<Error>> getErrors() {
		return errorsMap;
	}

	public void setErrors(Map<String, List<Error>> errorsMap) {
		this.errorsMap = errorsMap;
	}
	
	public String toString() {
		if (ok()) {
			return "OK";
		}
		return errorsMap.toString();
	}
	
	//
	// Static utility
	//
	
	/**
	 * @param errorsMap0
	 * @param errorsMap1
	 * @return
	 */
	static public Errors merge(Errors errorsMap0, Errors errorsMap1) {
		if (errorsMap0!=null && errorsMap1!=null) {
			errorsMap0.addAll(errorsMap1);
			return errorsMap0;
		} else if (errorsMap0!=null) {
			return errorsMap0;
		} else {
			return errorsMap1;
		}
	}
	

	public List<Error> getErrorList() {
		List<Error> errors = new ArrayList<Error>(); 
		for (List<Error> errors2: this.errorsMap.values()) {
			errors.addAll(errors2);
		}
		return errors;
	}


}
