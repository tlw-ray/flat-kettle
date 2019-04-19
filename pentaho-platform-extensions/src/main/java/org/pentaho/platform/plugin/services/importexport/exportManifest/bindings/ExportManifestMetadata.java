/*!
 *
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 *
 * Copyright (c) 2002-2018 Hitachi Vantara. All rights reserved.
 *
 */

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.07.25 at 11:25:28 AM EDT 
//

package org.pentaho.platform.plugin.services.importexport.exportManifest.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for ExportManifestMetadata complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExportManifestMetadata">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="domainId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="file" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType( XmlAccessType.FIELD )
@XmlType( name = "ExportManifestMetadata" )
public class ExportManifestMetadata {

  @XmlAttribute( name = "domainId" )
  protected String domainId;
  @XmlAttribute( name = "file" )
  protected String file;

  /**
   * Gets the value of the domainId property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getDomainId() {
    return domainId;
  }

  /**
   * Sets the value of the domainId property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setDomainId( String value ) {
    this.domainId = value;
  }

  /**
   * Gets the value of the file property.
   * 
   * @return possible object is {@link String }
   * 
   */
  public String getFile() {
    return file;
  }

  /**
   * Sets the value of the file property.
   * 
   * @param value
   *          allowed object is {@link String }
   * 
   */
  public void setFile( String value ) {
    this.file = value;
  }

}
