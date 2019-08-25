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

package ofc4j.model.elements;

import java.util.ArrayList;
import java.util.List;

import ofc4j.model.metadata.Alias;

public abstract class Element {
    private final String type;
    private Float alpha;
    private String text;
    @Alias("font-size") private Integer fontSize;
    @Alias("tip") private String tooltip;
    private List<Object> values = new ArrayList<Object>();
    @Alias("on-click")
    private String on_click;
    private String link;
    public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getOn_click() {
		return on_click;
	}

	public Element setOn_click(String on_click) {
		this.on_click = on_click;
		return this;
	}

	protected Element(String type) {
        this.type = type;
    }
    
    public String getType() {
        return type;
    }

    public Float getAlpha() {
        return alpha;
    }

    public Element setAlpha(Float alpha) {
        this.alpha = alpha;
        return this;
    }

    public String getText() {
        return text;
    }

    public Element setText(String text) {
        this.text = text;
        return this;
    }

    public Integer getFontSize() {
        return fontSize;
    }

    public Element setFontSize(Integer fontSize) {
        this.fontSize = fontSize;
        return this;
    }
    
    public List<Object> getValues() {
        return values;
    }
    
    public Element setTooltip(String tooltip) {
        this.tooltip = tooltip;
        return this;
    }
    
    public String getTooltip() {
        return tooltip;
    }
    
}
