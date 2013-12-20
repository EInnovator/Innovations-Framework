/**
 * 
 */
package org.einnovator.validation.impl;

import org.einnovator.meta.CompositeMetaDataSource;

/**
 * A {@code BuiltinValidationFactory}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class BuiltinValidationConstraintSource extends CompositeMetaDataSource {

	/**
	 * Create instance of {@code BuiltinValidationFactory}.
	 *
	 */
	public BuiltinValidationConstraintSource() {
		init();
	}
	
	public void init() {
		addSimpleSources(
			new NotNullValidatorFactory.NotNullConstraintSource(),
			new NullValidatorFactory.NullConstraintSource(),
			new DecimalMaxValidatorFactory.DecimalMaxConstraintSource(),
			new DecimalMinValidatorFactory.DecimalMinConstraintSource(),
			new MaxValidatorFactory.MaxConstraintSource(),
			new MinValidatorFactory.MinConstraintSource(),
			new FutureValidatorFactory.FutureConstraintSource(),
			new PastValidatorFactory.PastConstraintSource(),
			new SizeValidatorFactory.SizeConstraintSource(),
			new PatternValidatorFactory.PatternConstraintSource(),
			new DigitsValidatorFactory.DigitsConstraintSource()
		);

		addSimpleSources(
				new NotNullValidatorFactory.EnvironmentNotNullConstraintSource(),
				new NullValidatorFactory.EnvironmentNullConstraintSource(),
				new DecimalMaxValidatorFactory.EnvironmentDecimalMaxConstraintSource(),
				new DecimalMinValidatorFactory.EnvironmentDecimalMinConstraintSource(),
				new MaxValidatorFactory.EnvironmentMaxConstraintSource(),
				new MinValidatorFactory.EnvironmentMinConstraintSource(),
				new FutureValidatorFactory.EnvironmentFutureConstraintSource(),
				new PastValidatorFactory.EnvironmentPastConstraintSource(),
				new SizeValidatorFactory.EnvironmentSizeConstraintSource(),
				new PatternValidatorFactory.EnvironmentPatternConstraintSource(),
				new DigitsValidatorFactory.EnvironmentDigitsConstraintSource()
		);

	}
}
