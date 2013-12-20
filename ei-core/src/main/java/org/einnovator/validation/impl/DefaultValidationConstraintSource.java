package org.einnovator.validation.impl;

import org.einnovator.meta.CompositeMetaDataSource;

public class DefaultValidationConstraintSource extends CompositeMetaDataSource {

	//
	// Constructors
	//
	
	public DefaultValidationConstraintSource() {
		add(new BuiltinValidationConstraintSource());
	}
	
}
