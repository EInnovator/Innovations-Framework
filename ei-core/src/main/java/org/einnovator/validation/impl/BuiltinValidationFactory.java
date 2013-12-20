/**
 * 
 */
package org.einnovator.validation.impl;

import org.einnovator.meta.MetaDataSource;
import org.einnovator.validation.CompositeValidatorFactory;

/**
 * AA {@code BuiltinValidationFactory}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class BuiltinValidationFactory extends CompositeValidatorFactory {

	private MetaDataSource metaDataSource;
	
	/**
	 * Create instance of {@code BuiltinValidationFactory}.
	 *
	 */
	public BuiltinValidationFactory(MetaDataSource metaDataSource) {
		init(metaDataSource);
	}

	public BuiltinValidationFactory() {
		this(new DefaultValidationConstraintSource());
	}

	public void init(MetaDataSource metaDataSource) {
		this.metaDataSource = metaDataSource;
		addAll(
			new NotNullValidatorFactory(metaDataSource),
			new NullValidatorFactory(metaDataSource),
			new DecimalMaxValidatorFactory(metaDataSource),
			new DecimalMinValidatorFactory(metaDataSource),
			new MaxValidatorFactory(metaDataSource),
			new MinValidatorFactory(metaDataSource),
			new FutureValidatorFactory(metaDataSource),
			new PastValidatorFactory(metaDataSource),
			new SizeValidatorFactory(metaDataSource),
			new PatternValidatorFactory(metaDataSource),
			new DigitsValidatorFactory(metaDataSource)
		);
	}

	
	//
	// Getters and Setters
	//
	
	/**
	 * Get the value of property {@code metaDataSource}.
	 *
	 * @return the metaDataSource
	 */
	public MetaDataSource getMetaDataSource() {
		return metaDataSource;
	}
	
}
