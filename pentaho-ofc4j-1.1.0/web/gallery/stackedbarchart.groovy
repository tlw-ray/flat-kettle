def c = new Chart(new Date().toString())
    .setXAxis(new XAxis()
        .setLabels("a", "b", "c", "d"));

def yaxis = new YAxis();
yaxis.setRange(0, 14, 7);
c.setYAxis(yaxis);

def sbc = new StackedBarChart();
sbc.newStack().addValues(2.5f, 5);
sbc.newStack().addValues(7.5f);
sbc.newStack().addValues(5);
sbc.lastStack().addStackValues(new StackValue(5, "#ff0000"));
sbc.newStack().addValues(2, 2, 2, 2);
sbc.lastStack().addStackValues(new StackValue(2, "#ff00ff"));
c.addElements(sbc);
return c;