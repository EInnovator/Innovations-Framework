/**
 * 
 */
package org.einnovator.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.einnovator.util.Ordered;


/**
 * A {@code OrderedTextTransform}.
 *
 * @author Jorge Simão {@code {jorge.simao@einnovator.org}}
 */
public class OrderedTextTransform extends CompositeTextTransform {

	//
	// Constructors
	//
	
	/**
	 * Create instance of {@code OrderedTextTransform}.
	 *
	 */
	public OrderedTextTransform() {
	}
	
	/**
	 * Create instance of {@code OrderedTextTransform}.
	 *
	 * @param transforms the list of {@code TextTransform}
	 * @throws NullPointerException, if {@code transforms} in <code>null</code>
	 */
	public OrderedTextTransform(Collection<TextTransform> transforms) {
		super(new ArrayList<TextTransform>(transforms.size()));
		addAll(transforms);
	}	

	/**
	 * Create instance of {@code OrderedTextTransform}.
	 *
	 * @param transforms the list of {@code TextTransform}
	 * @throws NullPointerException, if {@code transforms} in <code>null</code>
	 */
	public OrderedTextTransform(TextTransform... transforms) {
		super(new ArrayList<TextTransform>(transforms.length));
		addAll(transforms);
	}	

	//
	// Builders
	//
	
	/**
	 * Add a transform to this chain.
	 * 
	 * @param transform the transform
	 */
	@Override
	public void addTransform(TextTransform transform) {
		int order = 0;
		if (transform instanceof Ordered) {
			order = ((Ordered)transform).getOrder();
		}
		addTransform(order, transform);
	}

	/**
	 * Add a transform to this chain.
	 * 
	 * @param order the order of the {@code TextTransform}
	 * @param transform the transform
	 */
	public void addTransform(int order, TextTransform transform) {
		List<TextTransform> transforms = getTransforms();
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
		super.addTransform(transform);
	}

	
	/**
	 * Add a variable number of transforms to this chain.
	 * 
	 * @param order the order of the {@code TextTransform}
	 * @param transforms the transforms
	 */
	public void addTransforms(int order, TextTransform... transforms) {
		for (TextTransform transform: transforms) {
			addTransform(order, transform);
		}
	}

	/**
	 * Add a collection of transforms to this chain.
	 * 
	 * @param order the order of the {@code TextTransform}
	 * @param transforms the transforms
	 */
	public void addTransforms(int order, Collection<TextTransform> transforms) {
		for (TextTransform transform: transforms) {
			addTransform(order, transform);
		}
	}


	//
	// Fluent API
	//

	/**
	 * Add a transform to this chain.
	 * 
	 * @param order the order of the {@code TextTransform}
	 * @param transform the transform
	 */
	public CompositeTextTransform add(int order, TextTransform transform) {
		addTransform(order, transform);
		return this;
	}

	/**
	 * Add a variable number of transforms to this chain.
	 * 
	 * @param order the order of all the {@code TextTransform}
	 * @param transforms the transforms
	 */
	public CompositeTextTransform addAll(int order, TextTransform... transforms) {
		addTransforms(order, transforms);
		return this;
	}

	/**
	 * Add a variable number of transforms to this chain.
	 * 
	 * @param order the order of all the {@code TextTransform}
	 * @param transforms the transforms
	 */
	public OrderedTextTransform addAll(int order, Collection<TextTransform> transforms) {
		addTransforms(order, transforms);
		return this;
	}


}
