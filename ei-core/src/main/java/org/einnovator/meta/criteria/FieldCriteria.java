package org.einnovator.meta.criteria;

import java.lang.reflect.Field;




public interface FieldCriteria  extends CriteriaMarker {
	boolean check(Field field);
}
