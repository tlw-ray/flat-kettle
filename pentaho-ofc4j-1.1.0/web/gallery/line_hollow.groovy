def c = new Chart(new Date().toString())
    .setYAxis(new YAxis()
        .setRange(0, 15, 5));
def l1 = new LineChart(LineChart.Style.HOLLOW);
def l2 = new LineChart(LineChart.Style.HOLLOW);
def l3 = new LineChart(LineChart.Style.HOLLOW);

l1.setHaloSize(0)
    .setWidth(2)
    .setDotSize(4);
    
l2.setHaloSize(1)
    .setWidth(2)
    .setDotSize(4);
    
l3.setHaloSize(1)
    .setWidth(6)
    .setDotSize(4);

for( float i = 0; i < 6.2; i += 0.2 ) {
  l1.addValues(Math.sin(i) * 1.9 + 7);
  l2.addValues(Math.sin(i) * 1.9 + 10);
  l3.addValues(Math.sin(i) * 1.9 + 4);
}

c.addElements(l1, l2, l3);
return c;