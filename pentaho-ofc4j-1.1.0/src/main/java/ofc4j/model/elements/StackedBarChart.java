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
import java.util.Arrays;
import java.util.List;

import ofc4j.model.metadata.Alias;
import ofc4j.model.metadata.Converter;
import ofc4j.util.StackKeyConverter;
import ofc4j.util.StackValueConverter;

/**
 * The stacked bar chart allows you to draw a bar chart divided
 * into value regions.
 */
/*
 * Implementation note: the Stack class wraps standard List objects.
 * The List objects are preserved in the element value field rather than
 * the Stack object itself so that XStream renders the data correctly. I
 * didn't have much luck trying to use a custom converter or an implicit
 * collection.
 */
public class StackedBarChart extends Element {
	private List<StackKey> keys = new ArrayList<StackKey>();
    public StackedBarChart() {
        super("bar_stack");
    }
    
    /**
     * Add stacks to the chart (var-args version).
     * @param stacks the stacks that have not yet been placed into the chart
     * @return the chart element object being operated on
     */
    public StackedBarChart addStack(Stack... stacks) {
        return copy(Arrays.asList(stacks));
    }
    
    /**
     * Add stacks to the chart (Collections version).
     * @param stacks the stacks that have not yet been placed into the chart
     * @return the chart element object being operated on
     */
    public StackedBarChart addStack(List<Stack> stacks) {
        return copy(stacks);
    }
    
    public StackedBarChart addKeys(StackKey... keys)
    {
    	this.keys.addAll(Arrays.asList(keys));
    	return this;
    }
    
    /**
     * Create a stack and add it into the chart.  You do not need to
     * pass this Stack object to addStack.
     * @return the stack that has been created in the chart
     */
    public Stack newStack() {
        Stack s = new Stack();
        copy(Arrays.asList(s));
        return s;
    }
    
    /**
     * Find the most recently created stack, or create one if
     * there are none.
     * @return the last stack in the chart
     */
    public Stack lastStack() {
        if (getValues().isEmpty()) {
            return newStack();
        } else {
            return stack(getStackCount() - 1);
        }
    }
    
    /**
     * Find an arbitrary stack by index number. (Starts at 0.)
     * @param index the index of the stack, 0 to getStackCount() - 1.
     * @return the stack at the specified index
     */
    @SuppressWarnings("unchecked")
    public Stack stack(int index) {
        return new Stack((List<Object>) getValues().get(index));
    }
    
    /**
     * The number of stacks in the chart.
     * @return the number of stacks in the chart
     */
    public int getStackCount() {
        return getValues().size();
    }
    
    private StackedBarChart copy(List<Stack> stacks) {
        for (Stack s : stacks) {
            getValues().add(s.getBackingList());
        }
        return this;
    }

    /**
     * Representation of a stack in the chart.  This class allows
     * you to add numbers or complex values with custom data.
     */
    public static class Stack {
        private transient List<Object> values;
        
        public Stack() {
            values = new ArrayList<Object>();
        }
        
        Stack(List<Object> values) {
            this.values = values;
        }
        
        public Stack addStackValues(StackValue... values) {
            return doAdd(Arrays.asList(values));
        }
        
        public Stack addStackValues(List<StackValue> values) {
            return doAdd(values);
        }
        
        public Stack addValues(Number... numbers) {
            return doAdd(Arrays.asList(numbers));
        }
        
        public Stack addValues(List<Number> numbers) {
            return doAdd(numbers);
        }
        
        private Stack doAdd(List<? extends Object> values) {
            this.values.addAll(values);
            return this;
        }
        
        List<Object> getBackingList() {
            return this.values;
        }
    }
    
    /**
     * Representation of data in the stacked bar chart. 
     */
    @Converter(StackValueConverter.class)
    public static class StackValue {
        private Number val;
        private String colour;
        @Alias("on-click") private String onClick;    
        
        public StackValue(Number value) {
            this(value, null);
        }
        
        public StackValue(Number value, String colour) {
            setValue(value);
            setColour(colour);
        }
        
        public Number getValue() {
            return val;
        }
        
        public StackValue setValue(Number val) {
            this.val = val;
            return this;
        }
        
        public String getColour() {
            return colour;
        }
        
        public StackValue setColour(String colour) {
            this.colour = colour;
            return this;
        }

        public String getOnClick() {
          return onClick;
        }

        public void setOnClick(String onClick) {
          this.onClick = onClick;
        }
    }
    
    /**
     * Representation of key in the stacked bar chart. 
     */
    @Converter(StackKeyConverter.class)
    public static class StackKey
    {
    	private String text;
    	private String colour;
    	
    	@Alias("font-size")    	private String fontSize;
    	
    	public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getColour() {
			return colour;
		}

		public void setColour(String colour) {
			this.colour = colour;
		}

		public String getFontSize() {
			return fontSize;
		}

		public void setFontSize(String fontSize) {
			this.fontSize = fontSize;
		}

	
    }
    
}
