/**
 * 
 */
package org.einnovator.validation;

import org.einnovator.format.ObjectSupport;
import org.einnovator.i18n.Message;
import org.einnovator.i18n.MessageDescriptor;
import org.einnovator.meta.MetaDescriptor;
import org.einnovator.util.StringUtil;

/**
 * An abstract support class to implement {@code Validator}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
abstract public class ValidatorSupport<T> extends ObjectSupport implements Validator<T> {
	
	private MetaDescriptor descriptor;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code ValidatorSupport}.
	 *
	 * @param descriptor
	 */
	public ValidatorSupport(MetaDescriptor descriptor) {
		this.descriptor = descriptor;
	}


	//
	// Getters and Setters
	//
	
	/**
	 * Get the value of property {@code descriptor}.
	 *
	 * @return the descriptor
	 */
	public MetaDescriptor getDescriptor() {
		return descriptor;
	}

	/**
	 * Set the value of property {@code descriptor}.
	 *
	 * @param descriptor the descriptor to set
	 */
	public void setDescriptor(MetaDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	protected Error createError(Object value, String path, Message message, Object[] parameters) {
		return new Error(value, path, mapMessage(message.key()), mapMessage(message.value()), parameters);
	}

	protected Error createError(Object value, String path, MessageDescriptor message, Object[] parameters) {
		return new Error(value, path, mapMessage(message.getKey()), mapMessage(message.getDefaultValue()), parameters);
	}

	protected void addErrors(Object value, Message message, ValidationContext context, Object... parameters) {
		context.getErrors().addError(createError(value, context.getPath().toString(), message, parameters));
	}

	protected void addErrors(Object value, MessageDescriptor message, ValidationContext context, Object... parameters) {
		context.getErrors().addError(createError(value, context.getPath().toString(), message, parameters));
	}

	protected String getPath() {
		Class<?> type = descriptor.getType();
		if (type.isArray()) {
			type = type.getComponentType();
		}
		return type.getName() + "." +  descriptor.getMember().getName();
	}
	
	protected String mapMessage(String message) {
		if (StringUtil.isEmpty(message)) {
			message = this.getClass().getName();
			int i = message.indexOf("$");
			if (i>0) {
				String inner = message.substring(i+1);
				message = message.substring(0,i);
				i = message.lastIndexOf(".");
				if (i>0) {
					message = message.substring(0,i) + "." + inner;
				}
			}
			message = message.replace(".impl.", ".");
		}
		return message;
	}
}
