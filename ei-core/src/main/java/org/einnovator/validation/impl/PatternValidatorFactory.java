/**
 * 
 */
package org.einnovator.validation.impl;

import java.lang.annotation.ElementType;

import org.einnovator.environment.Environment;
import org.einnovator.environment.EnvironmentHolder;
import org.einnovator.environment.PropertiesMapperUtil;
import org.einnovator.i18n.MessageDescriptor;
import org.einnovator.meta.MetaDataSource;
import org.einnovator.meta.MetaDescriptor;
import org.einnovator.meta.SimpleMetaDataSource;
import org.einnovator.validation.ValidationContext;
import org.einnovator.validation.Validator;
import org.einnovator.validation.ValidatorSupport;
import org.einnovator.validation.contraints.Pattern;
import org.einnovator.validation.contraints.Pattern.Flag;

/**
 * A PatternValidatorFactory.
 *
 * @author Jorge Sim√£o
 */
public class PatternValidatorFactory extends ValidatorFactorySupport {

	/**
	 * Descriptor that specifies specifies a minimum and/or maximum value or length constraints.
	 * 
	 * @author Jorge Sim„o, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class PatternDescriptor {
		
		/**
		 * The regular expression to match.
		 */
		String regexp;
		
		/**
		 * @return array of {@code Flag} considered when resolving the regular expression.
		 */
		Flag[] flags;

		/**
		 * @return the message to produce or render.
		 */
		private MessageDescriptor message;
		
		/**
		 * Create instance of {@code PatternDescriptor}.
		 *
		 */
		public PatternDescriptor() {
		}
		
		/**
		 * Create instance of {@code PatternDescriptor}.
		 *
		 * @param pattern
		 */
		public PatternDescriptor(Pattern pattern) {
			this.regexp = pattern.regexp();
			this.flags = pattern.flags();
			message = new MessageDescriptor().assign(pattern.message());
		}
	}

	static class PatternValidator extends ValidatorSupport<CharSequence> {
		private PatternDescriptor pattern;
		
		private java.util.regex.Pattern pattern0;

		public PatternValidator(MetaDescriptor descriptor, PatternDescriptor pattern) {
			super(descriptor);
			this.pattern = pattern;
			pattern0 = java.util.regex.Pattern.compile(pattern.regexp, mapFlags(pattern.flags));
		}
		
		private int mapFlags(Pattern.Flag[] flags) {
			int flags0 = 0;
			for (int i=0; i<flags.length; i++) {
				flags0 |= mapFlag(flags[i]);
			}
			return flags0;
		}

		private int mapFlag(Pattern.Flag flag) {
			switch (flag) {
			case CANON_EQ:
				return java.util.regex.Pattern.CANON_EQ;
			case CASE_INSENSITIVE:
				return java.util.regex.Pattern.CASE_INSENSITIVE;
			case COMMENTS:
				return java.util.regex.Pattern.COMMENTS;
			case DOTALL:
				return java.util.regex.Pattern.DOTALL;
			case MULTILINE:
				return java.util.regex.Pattern.MULTILINE;
			case UNICODE_CASE:
				return java.util.regex.Pattern.UNICODE_CASE;
			case UNIX_LINES:
				return java.util.regex.Pattern.UNIX_LINES;
			default:				
				break;
			}
			return 0;
		}

		public boolean validate(CharSequence s, ValidationContext context) {
			boolean valid = true;
			if (s!=null && !pattern0.matcher(s).matches()) {
				addErrors(s, pattern.message, context, pattern.regexp);
				valid = false;
			}
			return valid;
		}
	}

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code PatternValidatorFactory}.
	 *
	 * @param metaDataSource a {@code MetaDataSource}
	 */
	public PatternValidatorFactory(MetaDataSource metaDataSource) {
		super(metaDataSource);
	}
	
	//
	// ValidatorFactory Implementation
	//

	/**
	 * @see org.einnovator.validation.ValidatorFactory#createValidator(org.einnovator.meta.MetaDescriptor)
	 */
	@Override
	public Validator<?> createValidator(MetaDescriptor descriptor) {
		PatternDescriptor pattern = getValidationDescriptor(descriptor, PatternDescriptor.class);
		if (pattern!=null) {
			if (CharSequence.class.isAssignableFrom(descriptor.getType())) {
				return new PatternValidator(descriptor, pattern);			
			}			
		}
		return null;
	}
	
	/**
	 * A {@code PatternConstraintSource}.
	 *
	 * @author Jorge Sim„o, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class PatternConstraintSource implements SimpleMetaDataSource<PatternDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public PatternDescriptor getMetaData(MetaDescriptor descriptor) {
			Pattern pattern = descriptor.getAnnotation(Pattern.class);
			if (pattern!=null) {
				return new PatternDescriptor(pattern);
			}
			return null;
		}
	}

	/**
	 * A {@code EnvironmentPatternConstraintSource}.
	 *
	 * @author Jorge Sim„o, {@code jorge.simao@einnovator.org}
	 *
	 */
	public static class EnvironmentPatternConstraintSource implements SimpleMetaDataSource<PatternDescriptor> {

		/**
		 * @see org.einnovator.meta.SimpleMetaDataSource#getMetaData(org.einnovator.meta.MetaDescriptor)
		 */
		@Override
		public PatternDescriptor getMetaData(MetaDescriptor descriptor) {
			if (!(descriptor.getElementType()==ElementType.FIELD || descriptor.getElementType()==ElementType.METHOD)) {
				return null;
			}
			Environment environment = EnvironmentHolder.getRequiredInstance();
			String prefix = getValidationConstraintPrefix(descriptor, Pattern.class);
			return PropertiesMapperUtil.getRequiredMapper().read(prefix, PatternDescriptor.class, environment);
		}
	}	

}
