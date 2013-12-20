/**
 * 
 */
package org.einnovator.format.simple;

import java.awt.Color;

import org.einnovator.format.FormatterRegistrar;
import org.einnovator.format.FormatterRegistry;


/**
 * A SimpleFormatterRegistrar.
 *
 * @author Jorge Simão
 */
public class SimpleFormatterRegistrar implements FormatterRegistrar {

	@Override
	public void registerFormatters(FormatterRegistry registry) {

		registry.addFormatter(new BooleanFormatter(), Boolean.class);
		registry.addFormatter(new BooleanFormatter(), Boolean.TYPE);
		registry.addFormatter(new CharacterFormatter(), Character.class);
		registry.addFormatter(new CharacterFormatter(), Character.TYPE);

		registry.addFormatter(new ColorFormatter(), Color.class);
		registry.addFormatter(new ClassFormatter(), Class.class);
	}

}
