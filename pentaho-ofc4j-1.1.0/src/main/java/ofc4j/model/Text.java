/*
This file is part of OFC4J.

OFC4J is free software: you can redistribute it and/or modify
it under the terms of the Lesser GNU General Public License as
published by the Free Software Foundation, either version 3 of
the License, or (at your option) any later version.

OFC4J is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

See <http://www.gnu.org/licenses/lgpl-3.0.txt>.
*/

package ofc4j.model;

public class Text {
    private String text;
    private String style;
    
    public Text() {
        this(null, null);
    }
    
    public Text(String text) {
        this(text, null);
    }
    
    public Text(String text, String style) {
        setText(text);
        setStyle(style);
    }
    
    public String getText() {
        return text;
    }
    public Text setText(String text) {
        this.text = text;
        return this;
    }
    public String getStyle() {
        return style;
    }
    public Text setStyle(String style) {
        this.style = style;
        return this;
    }
}
