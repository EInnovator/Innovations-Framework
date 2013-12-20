package org.einnovator.format;

import java.util.Locale;

import org.einnovator.convert.ConversionService;
import org.einnovator.convert.ConversionServiceHolder;
import org.einnovator.log.Level;
import org.einnovator.meta.ObjectRef;
import org.einnovator.meta.ProjectionOptions;
import org.einnovator.text.TextTransform;

/**
 * Formatting Options.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class FormatOptions extends ObjectSupport implements Cloneable {
	
	/**
	 * Default text representation of <code>null</code> value.
	 */
	public static final String NULL = "null";

	/**
	 * The default separator between object type (and hash) and first field.
	 */
	public static final String BEGIN_MARKER = "(";

	/**
	 * The default separator after last field or property.
	 */
	public static final String END_MARKER = ")";

	/**
	 * The default separator between label and value.
	 */
	public static final String VALUE_SEPARATOR = "=";

	/**
	 * The default separator between field in formatting non-simple objects.
	 */
	public static final String FIELD_SEPARATOR = ", ";

	/**
	 * The default character to use in quotes (String values only).
	 */
	private static char QUOTE_CHAR = '\"';

	/**
	 * The default marker/separator before first element of a collection or array.
	 */
	public static final String COLLECTION_BEGIN_MARKER = "{";

	/**
	 * The default marker/separator after last element of a collection or array.
	 */
	public static final String COLLECTION_END_MARKER = "}";

	/**
	 * The default marker/separator before first element of an array.
	 */
	public static final String ARRAY_BEGIN_MARKER = "[";

	/**
	 * The default marker/separator after last element of an array.
	 */
	public static final String ARRAY_END_MARKER = "]";

	/**
	 * The default separator between elements in the collection or array.
	 */
	public static final String ELEMENT_SEPARATOR = ", ";

	/**
	 * The default separator between keys and values (Maps only).
	 */
	public static final String KEY_VALUE_SEPARATOR = " = ";
	
	/**
	 * {@code ConversionService} to use.
	 */
	private ConversionService conversionService;

	/**
	 * {@code Locale} to pass to the {@code ConversionService}.
	 */
	private Locale locale;

	/**
	 * Flag specifying if field or property should be ignored if it is <code>null</code> valued.
	 */
	private Boolean ignoreNull;

	/**
	 * Flag specifying if field or property should be ignored if it is empty (Strings, arrays, and collections).
	 */
	private Boolean ignoreEmpty;

	/**
	 * Flag specifying if field or property should be ignored if it is zero (Number sub-types only).
	 */
	private Boolean ignoreZero;	

	/**
	 * Specifies if value type name should be printed in a qualified.
	 */
	private Boolean qualified;
	
	/**
	 * Specified if hash value should be printed in formatting non-simple objects.
	 */
	private Boolean hash;

	/**
	 * Text representation of <code>null</code> value.
	 */
	private String nullValue;

	/**
	 * The separator between object type (and hash) and first field.
	 */
	private String beginMarker;

	/**
	 * The separator after last field or property.
	 */
	private String endMarker;

	/**
	 * The separator between label and value.
	 */
	private String valueSeparator;
	
	/**
	 * The separator between field in formatting non-simple objects.
	 */
	private String fieldSeparator;

	/**
	 * Flag specifying if value should be quoted (String values only).
	 */
	private Boolean quote;

	/**
	 * The character to use in quotes (String values only).
	 */
	private Character quoteChar;
	
	/**
	 * The marker/separator before first element of a collection.
	 */
	private String collectionBeginMarker;

	/**
	 * The marker/separator after last element of a collection.
	 */
	private String collectionEndMarker;

	/**
	 * The marker/separator before first element of an array.
	 */
	private String arrayBeginMarker;

	/**
	 * The marker/separator after last element of an array.
	 */
	private String arrayEndMarker;

	/**
	 * The separator between elements in the collection or array.
	 */
	private String elementSeparator;
	
	/**
	 * The separator between keys and values (Maps only).
	 */
	private String keyValueSeparator;
	
	/**
	 * Flag for access type: <code>true</code> (default) for field access;
	 *  <code>false</code> for getter access type.
	 */
	private Boolean fieldAccess;

	/**
	 * Flag that specified if cycles should be checked.
	 */
	private Boolean checkCycles;

	/**
	 * The {@code ProjectionOptions} to use when cycles are detected. 
	 */
	private ProjectionOptions cycleProjection;

	/**
	 * Flag that specifies whether path projections should supported.
	 */
	private Boolean paths;
	
	/**
	 * Flag that specifies whether path projections should be compiled/optimized.
	 */
	private Boolean compilePaths;

	/**
	 * The {@code ProjectionOptions} to use. 
	 */
	private ProjectionOptions projection;
	
	/**
	 * The {@code ObjectRef} to use to get a {@code TextTransform} to apply to the formatted text.
	 */
	private ObjectRef<TextTransform> transform;

	/**
	 * The {@code ObjectRef} to use to get a {@code TextTransform} to apply to the label of fields.
	 */
	private ObjectRef<TextTransform> labelTransform;

	/**
	 * Level of logging required to have a field/property being printed.
	 */
	private Level level;
	
	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code PropertiesOptions}.
	 *
	 */
	public FormatOptions() {
	}
	
	//
	// Getters and Setters
	//

	/**
	 * Get the value of property {@code conversionService}.
	 *
	 * @return the conversionService
	 */
	public ConversionService getConversionService() {
		return conversionService;
	}

	/**
	 * Set the value of property {@code conversionService}.
	 *
	 * @param conversionService the conversionService to set
	 */
	public void setConversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
	}

	/**
	 * Get the value of property {@code locale}.
	 *
	 * @return the locale
	 */
	public Locale getLocale() {
		return locale;
	}

	/**
	 * Set the value of property {@code locale}.
	 *
	 * @param locale the locale to set
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	/**
	 * Get the value of property {@code ignoreNull}.
	 *
	 * @return the ignoreNull
	 */
	public Boolean getIgnoreNull() {
		return ignoreNull;
	}

	/**
	 * Set the value of property {@code ignoreNull}.
	 *
	 * @param ignoreNull the ignoreNull to set
	 */
	public void setIgnoreNull(Boolean ignoreNull) {
		this.ignoreNull = ignoreNull;
	}

	/**
	 * Get the value of property {@code ignoreEmpty}.
	 *
	 * @return the ignoreEmpty
	 */
	public Boolean getIgnoreEmpty() {
		return ignoreEmpty;
	}

	/**
	 * Set the value of property {@code ignoreEmpty}.
	 *
	 * @param ignoreEmpty the ignoreEmpty to set
	 */
	public void setIgnoreEmpty(Boolean ignoreEmpty) {
		this.ignoreEmpty = ignoreEmpty;
	}

	/**
	 * Get the value of property {@code ignoreZero}.
	 *
	 * @return the ignoreZero
	 */
	public Boolean getIgnoreZero() {
		return ignoreZero;
	}

	/**
	 * Set the value of property {@code ignoreZero}.
	 *
	 * @param ignoreZero the ignoreZero to set
	 */
	public void setIgnoreZero(Boolean ignoreZero) {
		this.ignoreZero = ignoreZero;
	}

	/**
	 * Get the value of property {@code qualified}.
	 *
	 * @return the qualified
	 */
	public Boolean getQualified() {
		return qualified;
	}

	/**
	 * Set the value of property {@code qualified}.
	 *
	 * @param qualified the qualified to set
	 */
	public void setQualified(Boolean qualified) {
		this.qualified = qualified;
	}

	/**
	 * Get the value of property {@code hash}.
	 *
	 * @return the hash
	 */
	public Boolean getHash() {
		return hash;
	}

	/**
	 * Set the value of property {@code hash}.
	 *
	 * @param hash the hash to set
	 */
	public void setHash(Boolean hash) {
		this.hash = hash;
	}

	/**
	 * Get the value of property {@code nullValue}.
	 *
	 * @return the nullValue
	 */
	public String getNullValue() {
		return nullValue;
	}

	/**
	 * Set the value of property {@code nullValue}.
	 *
	 * @param nullValue the nullValue to set
	 */
	public void setNullValue(String nullValue) {
		this.nullValue = nullValue;
	}

	/**
	 * Get the value of property {@code beginMarker}.
	 *
	 * @return the beginMarker
	 */
	public String getBeginMarker() {
		return beginMarker;
	}

	/**
	 * Set the value of property {@code beginMarker}.
	 *
	 * @param beginMarker the beginMarker to set
	 */
	public void setBeginMarker(String beginMarker) {
		this.beginMarker = beginMarker;
	}

	/**
	 * Get the value of property {@code endMarker}.
	 *
	 * @return the endMarker
	 */
	public String getEndMarker() {
		return endMarker;
	}

	/**
	 * Set the value of property {@code endMarker}.
	 *
	 * @param endMarker the endMarker to set
	 */
	public void setEndMarker(String endMarker) {
		this.endMarker = endMarker;
	}

	/**
	 * Get the value of property {@code valueSeparator}.
	 *
	 * @return the valueSeparator
	 */
	public String getValueSeparator() {
		return valueSeparator;
	}

	/**
	 * Set the value of property {@code valueSeparator}.
	 *
	 * @param valueSeparator the valueSeparator to set
	 */
	public void setValueSeparator(String valueSeparator) {
		this.valueSeparator = valueSeparator;
	}

	/**
	 * Get the value of property {@code fieldSeparator}.
	 *
	 * @return the fieldSeparator
	 */
	public String getFieldSeparator() {
		return fieldSeparator;
	}

	/**
	 * Set the value of property {@code fieldSeparator}.
	 *
	 * @param fieldSeparator the fieldSeparator to set
	 */
	public void setFieldSeparator(String fieldSeparator) {
		this.fieldSeparator = fieldSeparator;
	}

	/**
	 * Get the value of property {@code quote}.
	 *
	 * @return the quote
	 */
	public Boolean getQuote() {
		return quote;
	}

	/**
	 * Set the value of property {@code quote}.
	 *
	 * @param quote the quote to set
	 */
	public void setQuote(Boolean quote) {
		this.quote = quote;
	}

	/**
	 * Get the value of property {@code quoteChar}.
	 *
	 * @return the quoteChar
	 */
	public Character getQuoteChar() {
		return quoteChar;
	}

	/**
	 * Set the value of property {@code quoteChar}.
	 *
	 * @param quoteChar the quoteChar to set
	 */
	public void setQuoteChar(Character quoteChar) {
		this.quoteChar = quoteChar;
	}

	/**
	 * Get the value of property {@code collectionBeginMarker}.
	 *
	 * @return the collectionBeginMarker
	 */
	public String getCollectionBeginMarker() {
		return collectionBeginMarker;
	}

	/**
	 * Set the value of property {@code collectionBeginMarker}.
	 *
	 * @param collectionBeginMarker the collectionBeginMarker to set
	 */
	public void setCollectionBeginMarker(String collectionBeginMarker) {
		this.collectionBeginMarker = collectionBeginMarker;
	}

	/**
	 * Get the value of property {@code collectionEndMarker}.
	 *
	 * @return the collectionEndMarker
	 */
	public String getCollectionEndMarker() {
		return collectionEndMarker;
	}

	/**
	 * Set the value of property {@code collectionEndMarker}.
	 *
	 * @param collectionEndMarker the collectionEndMarker to set
	 */
	public void setCollectionEndMarker(String collectionEndMarker) {
		this.collectionEndMarker = collectionEndMarker;
	}

	/**
	 * Get the value of property {@code arrayBeginMarker}.
	 *
	 * @return the arrayBeginMarker
	 */
	public String getArrayBeginMarker() {
		return arrayBeginMarker;
	}

	/**
	 * Set the value of property {@code arrayBeginMarker}.
	 *
	 * @param arrayBeginMarker the arrayBeginMarker to set
	 */
	public void setArrayBeginMarker(String arrayBeginMarker) {
		this.arrayBeginMarker = arrayBeginMarker;
	}

	/**
	 * Get the value of property {@code arrayEndMarker}.
	 *
	 * @return the arrayEndMarker
	 */
	public String getArrayEndMarker() {
		return arrayEndMarker;
	}

	/**
	 * Set the value of property {@code arrayEndMarker}.
	 *
	 * @param arrayEndMarker the arrayEndMarker to set
	 */
	public void setArrayEndMarker(String arrayEndMarker) {
		this.arrayEndMarker = arrayEndMarker;
	}

	/**
	 * Get the value of property {@code elementSeparator}.
	 *
	 * @return the elementSeparator
	 */
	public String getElementSeparator() {
		return elementSeparator;
	}

	/**
	 * Set the value of property {@code elementSeparator}.
	 *
	 * @param elementSeparator the elementSeparator to set
	 */
	public void setElementSeparator(String elementSeparator) {
		this.elementSeparator = elementSeparator;
	}

	/**
	 * Get the value of property {@code keyValueSeparator}.
	 *
	 * @return the keyValueSeparator
	 */
	public String getKeyValueSeparator() {
		return keyValueSeparator;
	}

	/**
	 * Set the value of property {@code keyValueSeparator}.
	 *
	 * @param keyValueSeparator the keyValueSeparator to set
	 */
	public void setKeyValueSeparator(String keyValueSeparator) {
		this.keyValueSeparator = keyValueSeparator;
	}

	/**
	 * Get the value of property {@code fieldAccess}.
	 *
	 * @return the fieldAccess
	 */
	public boolean isFieldAccess() {
		return fieldAccess;
	}

	/**
	 * Set the value of property {@code fieldAccess}.
	 *
	 * @param fieldAccess the fieldAccess to set
	 */
	public void setFieldAccess(boolean fieldAccess) {
		this.fieldAccess = fieldAccess;
	}
	
	/**
	 * Get the value of property {@code projection}.
	 *
	 * @return the projection
	 */
	public ProjectionOptions getProjection() {
		return projection;
	}

	/**
	 * Set the value of property {@code projection}.
	 *
	 * @param projection the projection to set
	 */
	public void setProjection(ProjectionOptions projection) {
		this.projection = projection;
	}

	/**
	 * Get the value of property {@code fieldAccess}.
	 *
	 * @return the fieldAccess
	 */
	public Boolean getFieldAccess() {
		return fieldAccess;
	}

	/**
	 * Set the value of property {@code fieldAccess}.
	 *
	 * @param fieldAccess the fieldAccess to set
	 * @return this {@code FormatOptions}
	 */
	public void setFieldAccess(Boolean fieldAccess) {
		this.fieldAccess = fieldAccess;
	}
	
	/**
	 * Get the value of property {@code transform}.
	 *
	 * @return the transform
	 */
	public ObjectRef<TextTransform> getTransform() {
		return transform;
	}

	/**
	 * Set the value of property {@code transform}.
	 *
	 * @param transform the transform to set
	 */
	public void setTransform(ObjectRef<TextTransform> transform) {
		this.transform = transform;
	}

	/**
	 * Get the value of property {@code labelTransform}.
	 *
	 * @return the transform
	 */
	public ObjectRef<TextTransform> getLabelTransform() {
		return labelTransform;
	}

	/**
	 * Set the value of property {@code labelTransform}.
	 *
	 * @param transform the transform to set
	 */
	public void setLabelTransform(ObjectRef<TextTransform> labelTransform) {
		this.labelTransform = labelTransform;
	}

	/**
	 * Check if {@code ProjectionOptions} is <code>null</code> or empty.
	 * 
	 * @return <code>true</code>, if the if {@code ProjectionOptions} is <code>null</code> or empty; 
	 * <code>false</code>, otherwise.
	 */
	public boolean isProjectionEmpty() {
		return ProjectionOptions.isEmpty(projection);
	}
	
	/**
	 * Check if {@code ProjectionOptions} is <code>null</code> or empty.
	 * 
	 * @return <code>true</code>, if the if {@code ProjectionOptions} is <code>null</code> or empty; 
	 * <code>false</code>, otherwise.
	 */
	public boolean isCycleProjectionEmpty() {
		return ProjectionOptions.isEmpty(cycleProjection);
	}

	/**
	 * Get the value of property {@code checkCycles}.
	 *
	 * @return the checkCycles
	 */
	public Boolean getCheckCycles() {
		return checkCycles;
	}

	/**
	 * Set the value of property {@code checkCycles}.
	 *
	 * @param checkCycles the checkCycles to set
	 */
	public void setCheckCycles(Boolean checkCycles) {
		this.checkCycles = checkCycles;
	}

	/**
	 * Get the value of property {@code cycleProjection}.
	 *
	 * @return the cycleProjection
	 */
	public ProjectionOptions getCycleProjection() {
		return cycleProjection;
	}

	/**
	 * Set the value of property {@code cycleProjection}.
	 *
	 * @param cycleProjection the cycleProjection to set
	 */
	public void setCycleProjection(ProjectionOptions cycleProjection) {
		this.cycleProjection = cycleProjection;
	}

	/**
	 * Get the value of property {@code paths}.
	 *
	 * @return the paths
	 */
	public Boolean getPaths() {
		return paths;
	}

	/**
	 * Set the value of property {@code paths}.
	 *
	 * @param paths the paths to set
	 */
	public void setPaths(Boolean paths) {
		this.paths = paths;
	}

	/**
	 * Get the value of property {@code compilePaths}.
	 *
	 * @return the compilePaths
	 */
	public Boolean getCompilePaths() {
		return compilePaths;
	}

	/**
	 * Set the value of property {@code compilePaths}.
	 *
	 * @param compilePaths the compilePaths to set
	 */
	public void setCompilePaths(Boolean compilePaths) {
		this.compilePaths = compilePaths;
	}
	
	/**
	 * Get the value of property {@code level}.
	 *
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * Set the value of property {@code level}.
	 *
	 * @param level the level to set
	 */
	public void setLevel(Level level) {
		this.level = level;
	}
	
	//
	// Fluent API
	//
	
	/**
	 * Set the value of property {@code conversionService}.
	 *
	 * @param conversionService the conversionService to 
	 * @return this {@code FormatOptions} 
	 */
	public FormatOptions conversionService(ConversionService conversionService) {
		this.conversionService = conversionService;
		return this;
	}

	/**
	 * Set the value of property {@code locale}.
	 *
	 * @param locale the locale to 
	 * @return this {@code FormatOptions} 
	 */
	public FormatOptions locale(Locale locale) {
		this.locale = locale;
		return this;
	}

	/**
	 * Set the value of property {@code ignoreNull}.
	 *
	 * @param ignoreNull the ignoreNull to 
	 * @return this {@code FormatOptions} 
	 */
	public FormatOptions ignoreNull(Boolean ignoreNull) {
		this.ignoreNull = ignoreNull;
		return this;}

	/**
	 * Set the value of property {@code ignoreEmpty}.
	 *
	 * @param ignoreEmpty the ignoreEmpty to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions ignoreEmpty(Boolean ignoreEmpty) {
		this.ignoreEmpty = ignoreEmpty;
		return this;
	}

	/**
	 * Set the value of property {@code ignoreZero}.
	 *
	 * @param ignoreZero the ignoreZero to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions ignoreZero(Boolean ignoreZero) {
		this.ignoreZero = ignoreZero;
		return this;
	}

	/**
	 * Set the value of property {@code qualified}.
	 *
	 * @param qualified the qualified to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions qualified(Boolean qualified) {
		this.qualified = qualified;
		return this;
	}

	/**
	 * Set the value of property {@code hash}.
	 *
	 * @param hash the hash to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions hash(Boolean hash) {
		this.hash = hash;
		return this;
	}

	/**
	 * Set the value of property {@code nullValue}.
	 *
	 * @param nullValue the nullValue to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions nullValue(String nullValue) {
		this.nullValue = nullValue;
		return this;
	}

	/**
	 * Set the value of property {@code beginMarker}.
	 *
	 * @param beginMarker the beginMarker to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions beginMarker(String beginMarker) {
		this.beginMarker = beginMarker;
		return this;
	}

	/**
	 * Set the value of property {@code endMarker}.
	 *
	 * @param endMarker the endMarker to 
	 */
	public FormatOptions endMarker(String endMarker) {
		this.endMarker = endMarker;
		return this;
	}

	/**
	 * Set the value of property {@code valueSeparator}.
	 *
	 * @param valueSeparator the valueSeparator to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions valueSeparator(String valueSeparator) {
		this.valueSeparator = valueSeparator;
		return this;
	}

	/**
	 * Set the value of property {@code fieldSeparator}.
	 *
	 * @param fieldSeparator the fieldSeparator to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions fieldSeparator(String fieldSeparator) {
		this.fieldSeparator = fieldSeparator;
		return this;
	}

	/**
	 * Set the value of property {@code quote}.
	 *
	 * @param quote the quote to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions quote(Boolean quote) {
		this.quote = quote;
		return this;
	}

	/**
	 * Set the value of property {@code quoteChar}.
	 *
	 * @param quoteChar the quoteChar to 
	 * @return this {@code FormatOptions} 
	 */
	public FormatOptions quoteChar(Character quoteChar) {
		this.quoteChar = quoteChar;
		return this;
	}

	/**
	 * Set the value of property {@code collectionBeginMarker}.
	 *
	 * @param collectionBeginMarker the collectionBeginMarker to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions collectionBeginMarker(String collectionBeginMarker) {
		this.collectionBeginMarker = collectionBeginMarker;
		return this;
	}

	/**
	 * Set the value of property {@code collectionEndMarker}.
	 *
	 * @param collectionEndMarker the collectionEndMarker to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions collectionEndMarker(String collectionEndMarker) {
		this.collectionEndMarker = collectionEndMarker;
		return this;
	}

	/**
	 * Set the value of property {@code arrayBeginMarker}.
	 *
	 * @param arrayBeginMarker the arrayBeginMarker to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions arrayBeginMarker(String arrayBeginMarker) {
		this.arrayBeginMarker = arrayBeginMarker;
		return this;
	}

	/**
	 * Set the value of property {@code arrayEndMarker}.
	 *
	 * @param arrayEndMarker the arrayEndMarker to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions arrayEndMarker(String arrayEndMarker) {
		this.arrayEndMarker = arrayEndMarker;
		return this;
	}

	/**
	 * Set the value of property {@code elementSeparator}.
	 *
	 * @param elementSeparator the elementSeparator to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions elementSeparator(String elementSeparator) {
		this.elementSeparator = elementSeparator;
		return this;
	}

	/**
	 * Set the value of property {@code keyValueSeparator}.
	 *
	 * @param keyValueSeparator the keyValueSeparator to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions keyValueSeparator(String keyValueSeparator) {
		this.keyValueSeparator = keyValueSeparator;
		return this;
	}

	/**
	 * Set the value of property {@code fieldAccess}.
	 *
	 * @param fieldAccess the fieldAccess to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions fieldAccess(boolean fieldAccess) {
		this.fieldAccess = fieldAccess;
		return this;
	}

	/**
	 * Set the value of property for the {@code ProjectionOptions}.
	 *
	 * @param projection the projection to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions projection(ProjectionOptions projection) {
		this.projection = projection;
		return this;
	}

	/**
	 * Set the value of property for the {@code transform}
	 *
	 * @param transform the {@code ObjectRef} 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions transform(ObjectRef<TextTransform> transform) {
		this.transform = transform;
		return this;
	}

	/**
	 * Set the value of property for the {@code labelTransform}
	 *
	 * @param transform the {@code ObjectRef} 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions labelTransform(ObjectRef<TextTransform> labelTransform) {
		this.labelTransform = labelTransform;
		return this;
	}

	/**
	 * Set the value of property for the {@code checkCycles}.
	 *
	 * @param checkCycles the value of checkCycles
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions checkCycles(boolean checkCycles) {
		this.checkCycles = checkCycles;
		return this;
	}

	/**
	 * Set the value of property for the {@code cycleProjection}.
	 *
	 * @param projection the {@code ProjectionOptions} to 
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions cycleProjection(ProjectionOptions cycleProjection) {
		this.cycleProjection = cycleProjection;
		return this;
	}

	/**
	 * Set the value of property {@code paths}.
	 *
	 * @param paths the value of paths
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions paths(boolean paths) {
		this.paths = paths;
		return this;
	}

	/**
	 * Set the value of property {@code compilePaths}.
	 *
	 * @param paths the value of compilePaths
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions compilePaths(boolean compilePaths) {
		this.compilePaths = compilePaths;
		return this;
	}

	/**
	 * Set the value of property {@code level}.
	 *
	 * @param level the value of {@code level}
	 * @return this {@code FormatOptions}
	 */
	public FormatOptions level(Level level) {
		this.level = level;
		return this;
	}

	//
	// Object Overrides
	//

	/**
	 * Deep clone this {@code FormatOptions}.
	 * 
	 * Deep cloned properties include {@code projection},  {@code transform},
	 * and {@code cycleProjection}.
	 * 
	 * @return the {@code FormatOptions} clone
	 * @see java.lang.Object#clone()
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		FormatOptions options = (FormatOptions) super.clone();
		if (projection!=null) {
			options.projection = ProjectionOptions.clone(projection);
		}
		if (cycleProjection!=null) {
			options.cycleProjection = ProjectionOptions.clone(cycleProjection);
		}
		if (transform!=null) {
			options.transform = ObjectRef.clone(transform);
		}
		if (labelTransform!=null) {
			options.labelTransform = ObjectRef.clone(labelTransform);
		}
		return options;
	}
	

	//
	// Static Utilities
	//

	/**
	 * Deep clone a {@code PropertiesOptions}.
	 * 
	 * Delegates to {@link #clone()}.
	 * 
	 * @param options the {@code PropertiesOptions} to clone
	 * @return the {@code PropertiesOptions} clone
	 */
	public static FormatOptions clone(FormatOptions options) {
		try {
			return (FormatOptions) options.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Return a new {@code FormatOptions} instance with default settings.
	 * 
	 * @return the {@code FormatOptions}
	 */
	public static FormatOptions newInstance() {
		FormatOptions options = new FormatOptions();
		options.ignoreNull = true;
		options.ignoreEmpty = false;
		options.ignoreZero = false;
		options.qualified = false;
		options.hash = false;
		options.nullValue = NULL;
		options.beginMarker = BEGIN_MARKER;
		options.endMarker = END_MARKER;
		options.valueSeparator = VALUE_SEPARATOR;
		options.fieldSeparator = FIELD_SEPARATOR;
		options.quote = true;
		options.quoteChar = QUOTE_CHAR;
		options.collectionBeginMarker = COLLECTION_BEGIN_MARKER;
		options.collectionEndMarker = COLLECTION_END_MARKER;
		options.arrayBeginMarker = ARRAY_BEGIN_MARKER;
		options.arrayEndMarker = ARRAY_END_MARKER;
		options.elementSeparator = ELEMENT_SEPARATOR;
		options.keyValueSeparator = KEY_VALUE_SEPARATOR;
		options.conversionService = ConversionServiceHolder.getConversionService();
		options.fieldAccess = true;
		options.checkCycles = true;
		options.paths = true;
		options.compilePaths = false; //true;
		options.level = Level.ALL;
		return options;
	}
	
	//
	// Assignment
	//
	
	FormatOptions assign(TypeFormat format) {
		hash = format.hash();
		if (!format.beginMarker().isEmpty()) {
			beginMarker = format.beginMarker();			
		}
		if (!format.endMarker().isEmpty()) {
			endMarker = format.endMarker();			
		}
		if (!format.valueSeparator().isEmpty()) {
			valueSeparator = format.valueSeparator();			
		}
		if (!format.fieldSeparator().isEmpty()) {
			fieldSeparator = format.fieldSeparator();
		}
		qualified = format.qualified();
		return this;
	}

	FormatOptions assign(Format format) {
		ignoreNull = format.ignoreNull();
		ignoreEmpty = format.ignoreEmpty();
		ignoreZero = format.ignoreDefault();
		quote = format.quote();
		if (format.quoteChar()!='\0') {
			quoteChar = format.quoteChar();			
		}
		if (this.projection!=null) {
			this.projection.assign(format.projection());					
		} else if (!ProjectionOptions.isEmpty(format.projection())) {			
			this.projection = new ProjectionOptions().assign(format.projection());					
		}
		if (this.cycleProjection!=null) {
			this.cycleProjection.assign(format.cycleProjection());					
		} else if (!ProjectionOptions.isEmpty(format.cycleProjection())) {			
			this.cycleProjection = new ProjectionOptions().assign(format.cycleProjection());					
		}
		if (!ObjectRef.isEmpty(format.transform())) {
			this.transform = ObjectRef.newInstance(TextTransform.class).assign(format.transform());
		}
		if (!ObjectRef.isEmpty(format.labelTransform())) {
			this.labelTransform = ObjectRef.newInstance(TextTransform.class).assign(format.labelTransform());
		}
		level = format.level();
		return this;
	}
	
	FormatOptions assign(CollectionFormat format, boolean array) {
		if (!array) {
			collectionBeginMarker = format.beginMarker();
			collectionEndMarker = format.endMarker();
		} else {
			arrayBeginMarker = format.beginMarker();
			arrayEndMarker = format.endMarker();
		}
		elementSeparator = format.elementSeparator();
		keyValueSeparator = format.keyValueSeparator();
		return this;
	}
}
