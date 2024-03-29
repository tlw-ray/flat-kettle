/*
 * Copyright 2006 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
*/
package org.pentaho.pms.cwm.pentaho.meta.behavioral;

/**
 * ParameterDirectionKind enumeration class implementation.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public final class ParameterDirectionKindEnum implements ParameterDirectionKind {
    /**
     * Enumeration constant corresponding to literal pdk_in.
     */
    public static final ParameterDirectionKindEnum PDK_IN = new ParameterDirectionKindEnum("pdk_in");
    /**
     * Enumeration constant corresponding to literal pdk_inout.
     */
    public static final ParameterDirectionKindEnum PDK_INOUT = new ParameterDirectionKindEnum("pdk_inout");
    /**
     * Enumeration constant corresponding to literal pdk_out.
     */
    public static final ParameterDirectionKindEnum PDK_OUT = new ParameterDirectionKindEnum("pdk_out");
    /**
     * Enumeration constant corresponding to literal pdk_return.
     */
    public static final ParameterDirectionKindEnum PDK_RETURN = new ParameterDirectionKindEnum("pdk_return");

    private static final java.util.List typeName;
    private final java.lang.String literalName;

    static {
        java.util.ArrayList temp = new java.util.ArrayList();
        temp.add("Pentaho");
        temp.add("Meta");
        temp.add("Behavioral");
        temp.add("ParameterDirectionKind");
        typeName = java.util.Collections.unmodifiableList(temp);
    }

    private ParameterDirectionKindEnum(java.lang.String literalName) {
        this.literalName = literalName;
    }

    /**
     * Returns fully qualified name of the enumeration type.
     * @return List containing all parts of the fully qualified name.
     */
    public java.util.List refTypeName() {
        return typeName;
    }

    /**
     * Returns a string representation of the enumeration value.
     * @return A string representation of the enumeration value.
     */
    public java.lang.String toString() {
        return literalName;
    }

    /**
     * Returns a hash code for this the enumeration value.
     * @return A hash code for this enumeration value.
     */
    public int hashCode() {
        return literalName.hashCode();
    }

    /**
     * Indicates whether some other object is equal to this enumeration value.
     * @param o The reference object with which to compare.
     * @return true if the other object is the enumeration of the same type and 
     * of the same value.
     */
    public boolean equals(java.lang.Object o) {
        if (o instanceof ParameterDirectionKindEnum) return (o == this);
        else if (o instanceof ParameterDirectionKind) return (o.toString().equals(literalName));
        else return ((o instanceof javax.jmi.reflect.RefEnum) && ((javax.jmi.reflect.RefEnum) o).refTypeName().equals(typeName) && o.toString().equals(literalName));
    }

    /**
     * Translates literal name to correspondent enumeration value.
     * @param name Enumeration literal.
     * @return Enumeration value corresponding to the passed literal.
     */
    public static ParameterDirectionKind forName(java.lang.String name) {
        if (name.equals("pdk_in")) return PDK_IN;
        if (name.equals("pdk_inout")) return PDK_INOUT;
        if (name.equals("pdk_out")) return PDK_OUT;
        if (name.equals("pdk_return")) return PDK_RETURN;
        throw new java.lang.IllegalArgumentException("Unknown literal name '" + name + "' for enumeration 'Pentaho.Meta.Behavioral.ParameterDirectionKind'");
    }
    /**
     * Resolves serialized instance of enumeration value.
     * @return Resolved enumeration value.
     */
    protected java.lang.Object readResolve() throws java.io.ObjectStreamException {
        try {
            return forName(literalName);
        } catch (java.lang.IllegalArgumentException e) {
            throw new java.io.InvalidObjectException(e.getMessage());
        }
    }
}
