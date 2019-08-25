def lc = new LineChart(LineChart.Style.DOT);
lc.setWidth(2)
.setColour("#DFC329")
.setDotSize(3);

for(int i = 0; i < 8; i += 0.2) {
  def val = Math.sin(i) + 1.5;
  // OK, lets do something interesting.
  // any value above 1.75 we will plot
  // in RED
  if( val > 1.75 ) lc.addDots(new LineChart.Dot(val, "#D02020"));
  else lc.addValues(val);
}

def c = new Chart("Advanced dot lines")
    .addElements(lc);

def yaxis = new YAxis();
yaxis.setRange(0, 3, 1);
c.setYAxis(yaxis);

return c;