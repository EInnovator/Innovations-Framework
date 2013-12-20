/**
 * 
 */
package org.einnovator.convert.number;


import java.math.BigDecimal;
import java.math.BigInteger;

import org.einnovator.convert.Converter;
import org.einnovator.convert.ConverterRegistrar;
import org.einnovator.convert.ConverterRegistry;


/**
 * A NumberConverterRegistrar.
 *
 * @author Jorge Simão
 */
public class NumberConverterRegistrar implements ConverterRegistrar {

	//
	// Constructors
	//
	
	/**
	 * Create instance of NumberConverterRegistrar.
	 *
	 */
	public NumberConverterRegistrar() {
	}
	
	//
	// ConverterRegistrar implementation
	//
	
	@Override
	public void registerConverters(ConverterRegistry registry) {
		addNumberConverters(registry, Integer.class);
		addNumberConverters(registry, Integer.TYPE);

		addNumberConverters(registry, Long.class);
		addNumberConverters(registry, Long.TYPE);
		
		addNumberConverters(registry, Short.class);
		addNumberConverters(registry, Short.TYPE);
		
		addNumberConverters(registry, Byte.class);
		addNumberConverters(registry, Byte.TYPE);
		
		addNumberConverters(registry, Float.class);
		addNumberConverters(registry, Float.TYPE);
		
		addNumberConverters(registry, Double.class);
		addNumberConverters(registry, Double.TYPE);
		
		IntToBigDecimalConverter<?> intToBigDecimalConverter = new IntToBigDecimalConverter<Number>();
		registry.addConverter(intToBigDecimalConverter, Integer.class, BigDecimal.class);
		registry.addConverter(intToBigDecimalConverter, Short.class, BigDecimal.class);
		registry.addConverter(intToBigDecimalConverter, Byte.class, BigDecimal.class);
		
		LongToBigDecimalConverter<?> longToBigDecimalConverter = new LongToBigDecimalConverter<Number>();
		registry.addConverter(longToBigDecimalConverter, Long.class, BigDecimal.class);

		DoubleToBigDecimalConverter<?> doubleToBigDecimalConverter = new DoubleToBigDecimalConverter<Number>();
		registry.addConverter(doubleToBigDecimalConverter, Double.class, BigDecimal.class);
		registry.addConverter(doubleToBigDecimalConverter, Float.class, BigDecimal.class);

		
		ToBigIntegerConverter<?> toBigIntegerConverter = new ToBigIntegerConverter<Number>();
		registry.addConverter(toBigIntegerConverter, Integer.class, BigInteger.class);
		registry.addConverter(toBigIntegerConverter, Long.class, BigInteger.class);		
		registry.addConverter(toBigIntegerConverter, Short.class, BigInteger.class);		
		registry.addConverter(toBigIntegerConverter, Byte.class, BigInteger.class);

		RoundingToBigIntegerConverter<?> toBigIntegerConverter2 = new RoundingToBigIntegerConverter<Number>();

		registry.addConverter(toBigIntegerConverter2, Float.class, BigInteger.class);
		registry.addConverter(toBigIntegerConverter2, Double.class, BigInteger.class);		
	}
		
	protected <T extends Number> void addNumberConverters(ConverterRegistry registry, Class<T> fromType) {
		addNumberConverter(registry, new ToIntegerConverter<T>(), fromType, Integer.class);
		addNumberConverter(registry, new ToIntegerConverter<T>(), fromType, Integer.TYPE);
		
		addNumberConverter(registry, new ToLongConverter<T>(), fromType, Long.class);
		addNumberConverter(registry, new ToLongConverter<T>(), fromType, Long.TYPE);
		
		addNumberConverter(registry, new ToShortConverter<T>(), fromType, Short.class);
		addNumberConverter(registry, new ToShortConverter<T>(), fromType, Short.TYPE);
		
		addNumberConverter(registry, new ToByteConverter<T>(), fromType, Byte.class);
		addNumberConverter(registry, new ToByteConverter<T>(), fromType, Byte.TYPE);
		
		addNumberConverter(registry, new ToCharacterConverter<T>(), fromType, Character.class);	
		addNumberConverter(registry, new ToCharacterConverter<T>(), fromType, Character.TYPE);	
		
		addNumberConverter(registry, new ToFloatConverter<T>(), fromType, Float.class);
		addNumberConverter(registry, new ToFloatConverter<T>(), fromType, Float.TYPE);	
		
		addNumberConverter(registry, new ToDoubleConverter<T>(), fromType, Double.class);
		addNumberConverter(registry, new ToDoubleConverter<T>(), fromType, Double.TYPE);		
	}

	protected <T extends Number, U>
	void addNumberConverter(ConverterRegistry registry, Converter<T,U> converter,
			Class<T> fromType, Class<U> toType) {
		if (!fromType.equals(toType)) {
			registry.addConverter(converter, fromType, toType);			
		}
	}
		

	//
	// Converter
	//
	
	public static class ToIntegerConverter<T extends Number> implements Converter<T, Integer> {
		@Override
		public Integer convert(T value) {
			return value.intValue();
		}
	}

	public static class ToLongConverter<T extends Number> implements Converter<T, Long> {
		@Override
		public Long convert(T value) {
			return value.longValue();
		}
	}

	public static class ToShortConverter<T extends Number> implements Converter<T, Short> {
		@Override
		public Short convert(T value) {
			return value.shortValue();
		}
	}

	public static class ToByteConverter<T extends Number> implements Converter<T, Byte> {
		@Override
		public Byte convert(T value) {
			return value.byteValue();
		}
	}

	public static class ToCharacterConverter<T extends Number> implements Converter<T, Character> {
		@Override
		public Character convert(T value) {
			return (char) value.intValue();
		}
	}

	public static class ToFloatConverter<T extends Number> implements Converter<T, Float> {
		@Override
		public Float convert(T value) {
			return value.floatValue();
		}
	}

	public static class ToDoubleConverter<T extends Number> implements Converter<T, Double> {
		@Override
		public Double convert(T value) {
			return value.doubleValue();
		}
	}

	//
	// To BigDecimal converters
	//
	
	public static class IntToBigDecimalConverter<T extends Number> implements Converter<T, BigDecimal> {
		@Override
		public BigDecimal convert(T value) {
			return new BigDecimal(value.intValue());
		}
	}

	public static class LongToBigDecimalConverter<T extends Number> implements Converter<T, BigDecimal> {
		@Override
		public BigDecimal convert(T value) {
			return new BigDecimal(value.longValue());
		}
	}

	public static class DoubleToBigDecimalConverter<T extends Number> implements Converter<T, BigDecimal> {
		@Override
		public BigDecimal convert(T value) {
			return new BigDecimal(value.doubleValue());
		}
	}

	//
	// To BigInteger converters
	//

	public static class ToBigIntegerConverter<T> implements Converter<T, BigInteger> {
		@Override
		public BigInteger convert(T value) {
			return new BigInteger(value.toString());
		}
	}

	public static class RoundingToBigIntegerConverter<T extends Number> implements Converter<T, BigInteger> {
		@Override
		public BigInteger convert(T value) {
			return new BigInteger(Long.valueOf(value.longValue()).toString());
		}
	}

}
