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
package org.pentaho.pms.cwm.pentaho.meta.relational;

/**
 * Row class proxy interface.
 * An instance of a ColumnSet.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface CwmRowClass extends javax.jmi.reflect.RefClass {
    /**
     * The default factory operation used to create an instance object.
     * @return The created instance object.
     */
    public CwmRow createCwmRow();
    /**
     * Creates an instance object having attributes initialized by the passed 
     * values.
     * @param name An identifier for the ModelElement within its containing Namespace.
     * @param visibility Specifies extent of the visibility of the ModelElement 
     * within its owning Namespace.
     * @return The created instance object.
     */
    public CwmRow createCwmRow(java.lang.String name, org.pentaho.pms.cwm.pentaho.meta.core.VisibilityKind visibility);
}
