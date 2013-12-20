/**
 * 
 */
package org.einnovator.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.einnovator.meta.MetaDescriptor;
import org.einnovator.meta.MetaUtil;


/**
 * A ReflectiveValidatorFactory.
 *
 * @author Jorge Simão
 */
public class ReflectiveValidatorFactory implements ValidatorFactory {
	
	protected Constructor<?  extends Validator<?>> constructor;
	
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of ReflectiveValidatorFactory.
	 *
	 * @param paramModelType
	 */
	public ReflectiveValidatorFactory(Class<? extends Validator<?>> validatorType) {
		constructor = MetaUtil.getConstructorVArgs(validatorType, 
				Method.class, Class.class, Annotation[].class);
	}

	//
	// ValidatorFactory implementation
	//
	
	@Override
	public Validator<?> createValidator(MetaDescriptor descriptor) {
		return MetaUtil.newInstanceVArgs(constructor, descriptor);
	}

}
