/**
 * 
 */
package org.einnovator.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.einnovator.util.Ordered;


/**
 * AA composite or chain of {@code TextTransform}.
 *
 * @author Jorge Simão, {@code jorge.simao@einnovator.org}
 *
 */
public class CompositeTextTransform implements TextTransform { 
	private List<TextTransform> transforms;

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code CompositeTextTransform}.
	 *
	 */
	public CompositeTextTransform() {
		this.transforms = new ArrayList<TextTransform>();
	}
	
	/**
	 * Create instance of {@code CompositeTextTransform}.
	 *
	 * @param transforms a list of {@code TextTransform}
	 */
	public CompositeTextTransform(List<TextTransform> transforms) {
		this.transforms = transforms;
	}	
	
	/**
	 * Create instance of {@code CompositeTextTransform}.
	 *
	 * @param transforms a variable number of {@code TextTransform}
	 */
	public CompositeTextTransform(TextTransform... transforms) {
		this.transforms = new ArrayList<TextTransform>(transforms.length);
		addAll(transforms);
	}	

	//
	// Getters and setters
	//
	
	/**
	 * Get the value of transforms.
	 *
	 * @return the transforms
	 */
	public List<TextTransform> getTransforms() {
		return transforms;
	}

	/**
	 * Set the value of transforms.
	 *
	 * @param transforms the transforms to set
	 */
	public void setTransforms(List<TextTransform> transforms) {
		this.transforms = transforms;
	}
	
	/**
	 * Get the number of transforms in this chain.
	 * 
	 * @return the number of transforms
	 */
	public int size() {
		return transforms.size();
	}

	//
	// Builders
	//
	
	/**
	 * Add a transform to this chain
	 * 
	 * @param transform the transform
	 */
	public void addTransform(TextTransform transform) {
		int order = 0;
		if (transform instanceof Ordered) {
			order = ((Ordered)transform).getOrder();
		}
		for (int i=0; i<transforms.size(); i++) {
			TextTransform transform2 = transforms.get(i);
			int order2 = 0;
			if (transform2 instanceof Ordered) {
				order2 = ((Ordered)transform2).getOrder();
			} 
			if (order<order2) {
				transforms.add(i, transform);
				return;
			}
		}
		this.transforms.add(transform);
	}

	/**
	 * Add a variable number of transforms to this chain
	 * 
	 * @param transforms the transforms
	 */
	public void addTransforms(TextTransform... transforms) {
		for (TextTransform transform: transforms) {
			addTransform(transform);
		}
	}

	/**
	 * Add a collection of transforms to this chain
	 * 
	 * @param transforms the transforms
	 */
	public void addTransforms(Collection<TextTransform> transforms) {
		for (TextTransform transform: transforms) {
			addTransform(transform);
		}
	}

	//
	// Fluent API
	//

	/**
	 * Add a transform to this chain
	 * 
	 * @param transform the transform
	 */
	public CompositeTextTransform add(TextTransform transform) {
		addTransform(transform);
		return this;
	}

	/**
	 * Add a variable number of transforms to this chain
	 * 
	 * @param transforms the transforms
	 */
	public CompositeTextTransform addAll(TextTransform... transforms) {
		addTransforms(transforms);
		return this;
	}

	/**
	 * Add a variable number of transforms to this chain
	 * 
	 * @param transforms the transforms
	 */
	public CompositeTextTransform addAll(Collection<TextTransform> transforms) {
		addTransforms(transforms);
		return this;
	}

	//
	//
	// TextTransform implementation
	//

	/**
	 * @see org.einnovator.text.TextTransform#transform(java.lang.String)
	 */
	@Override
	public String transform(String text) {
		for (TextTransform transform: transforms) {
			text = transform.transform(text);
		}
		return text;
	}

}
