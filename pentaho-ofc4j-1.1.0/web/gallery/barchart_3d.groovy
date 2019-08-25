def c = new Chart(new Date().toString())
    .setXAxis(new XAxis()
        .setLabels(OFC.stringify(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)));

c.getXAxis().set3D(5);
c.getXAxis().setColour("#909090");

def e = new BarChart(BarChart.Style.THREED)
    .setColour("#D54C78");
        
Random r = new Random();

for (int i = 0; i < 10; ++i) {
    e.addValues(2 + r.nextInt(7));
}

c.addElements(e);
return c;