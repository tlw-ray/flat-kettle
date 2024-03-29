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
package org.pentaho.pms.cwm.pentaho.meta.relationships;

/**
 * AssociationEnd object instance interface.
 * An association end is an endpoint of an association, which connects the 
 * association to a classifier. Each association end is part of one association. 
 * The association ends of each association are ordered.
 * In the metamodel, an AssociationEnd is part of an Association and specif
 * ies the connection of an Association to some other Classifier. Because Associa
 * tionEnds are a kind of StructuralFeature, they are owned and ordered by A
 * ssociation instances via the ClassifierFeature association. The Structura
 * lFeatureType association is used to identify the Classifier to which the
 *  AssociationEnd is attached. Each AssociationEnd has a name and defines
 *  a set of properties of the connection.
 * The multiplicity property of an association end specifies how many insta
 * nces of the classifier at a given end (the one bearing the multiplicity value)
 *  may be associated with a single instance of the classifier at the other 
 * end. The association end also states whether or not the connection may be
 *  traversed towards the instance playing that role in the connection (the
 *  isNavigable attribute), that is, if the instance is directly reachable
 *  via the association.
 *  
 * <p><em><strong>Note:</strong> This type should not be subclassed or implemented 
 * by clients. It is generated from a MOF metamodel and automatically implemented 
 * by MDR (see <a href="http://mdr.netbeans.org/">mdr.netbeans.org</a>).</em></p>
 */
public interface CwmAssociationEnd extends org.pentaho.pms.cwm.pentaho.meta.core.CwmStructuralFeature {
    /**
     * Returns the value of attribute aggregation.
     * When placed on one end (the "target" end), specifies whether the class 
     * on the target end is an aggregation with respect to the class on the other 
     * end (the "source" end). Only one end of an association can be an aggregation.
     * @return Value of attribute aggregation.
     */
    public org.pentaho.pms.cwm.pentaho.meta.relationships.AggregationKind getAggregation();
    /**
     * Sets the value of aggregation attribute. See {@link #getAggregation} for 
     * description on the attribute.
     * @param newValue New value to be set.
     */
    public void setAggregation(org.pentaho.pms.cwm.pentaho.meta.relationships.AggregationKind newValue);
    /**
     * Returns the value of attribute isNavigable.
     * When placed on a target end, specifies whether traversal from a source 
     * instance to its associated target instances is possible. A value of true 
     * means that the association can be navigated by the source class and the 
     * target rolename can be used in navigation expressions. Specification of 
     * navigability for each direction is defined independently.
     * @return Value of attribute isNavigable.
     */
    public boolean isNavigable();
    /**
     * Sets the value of isNavigable attribute. See {@link #isNavigable} for description 
     * on the attribute.
     * @param newValue New value to be set.
     */
    public void setNavigable(boolean newValue);
}
