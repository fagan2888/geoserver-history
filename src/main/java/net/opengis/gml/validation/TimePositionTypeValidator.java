/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.opengis.gml.validation;

import net.opengis.gml.TimeIndeterminateValueType;

/**
 * A sample validator interface for {@link net.opengis.gml.TimePositionType}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface TimePositionTypeValidator {
    boolean validate();

    boolean validateValue(Object value);
    boolean validateCalendarEraName(String value);
    boolean validateFrame(String value);
    boolean validateIndeterminatePosition(TimeIndeterminateValueType value);
}
