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
package org.pentaho.pms.cwm.pentaho.meta.relational.enumerations;

/**
 * ReferentialRuleType enumeration class implementation.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public final class ReferentialRuleTypeEnum implements ReferentialRuleType {
    /**
     * Enumeration constant corresponding to literal importedKeyNoAction.
     */
    public static final ReferentialRuleTypeEnum IMPORTED_KEY_NO_ACTION = new ReferentialRuleTypeEnum("importedKeyNoAction");
    /**
     * Enumeration constant corresponding to literal importedKeyCascade.
     */
    public static final ReferentialRuleTypeEnum IMPORTED_KEY_CASCADE = new ReferentialRuleTypeEnum("importedKeyCascade");
    /**
     * Enumeration constant corresponding to literal importedKeySetNull.
     */
    public static final ReferentialRuleTypeEnum IMPORTED_KEY_SET_NULL = new ReferentialRuleTypeEnum("importedKeySetNull");
    /**
     * Enumeration constant corresponding to literal importedKeyRestrict.
     */
    public static final ReferentialRuleTypeEnum IMPORTED_KEY_RESTRICT = new ReferentialRuleTypeEnum("importedKeyRestrict");
    /**
     * Enumeration constant corresponding to literal importedKeySetDefault.
     */
    public static final ReferentialRuleTypeEnum IMPORTED_KEY_SET_DEFAULT = new ReferentialRuleTypeEnum("importedKeySetDefault");

    private static final java.util.List typeName;
    private final java.lang.String literalName;

    static {
        java.util.ArrayList temp = new java.util.ArrayList();
        temp.add("Pentaho");
        temp.add("Meta");
        temp.add("Relational");
        temp.add("Enumerations");
        temp.add("ReferentialRuleType");
        typeName = java.util.Collections.unmodifiableList(temp);
    }

    private ReferentialRuleTypeEnum(java.lang.String literalName) {
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
        if (o instanceof ReferentialRuleTypeEnum) return (o == this);
        else if (o instanceof ReferentialRuleType) return (o.toString().equals(literalName));
        else return ((o instanceof javax.jmi.reflect.RefEnum) && ((javax.jmi.reflect.RefEnum) o).refTypeName().equals(typeName) && o.toString().equals(literalName));
    }

    /**
     * Translates literal name to correspondent enumeration value.
     * @param name Enumeration literal.
     * @return Enumeration value corresponding to the passed literal.
     */
    public static ReferentialRuleType forName(java.lang.String name) {
        if (name.equals("importedKeyNoAction")) return IMPORTED_KEY_NO_ACTION;
        if (name.equals("importedKeyCascade")) return IMPORTED_KEY_CASCADE;
        if (name.equals("importedKeySetNull")) return IMPORTED_KEY_SET_NULL;
        if (name.equals("importedKeyRestrict")) return IMPORTED_KEY_RESTRICT;
        if (name.equals("importedKeySetDefault")) return IMPORTED_KEY_SET_DEFAULT;
        throw new java.lang.IllegalArgumentException("Unknown literal name '" + name + "' for enumeration 'Pentaho.Meta.Relational.Enumerations.ReferentialRuleType'");
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
