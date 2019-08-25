import java.math.*;

def c = new Chart(new Date().toString());
def y = new YAxis();
def x = new XAxis();
y.setRange(-2, 2);
x.setRange(-2, 2);
c.setYAxis(y);
c.setXAxis(x);
def se = new ScatterChart();
se.setColour("#FFD600");
se.setDotSize(10);
se.addPoint(0, 0);

def se2 = new ScatterChart();
se2.setColour("#D600FF");
se2.setDotSize(3);

def mc = new MathContext(2);
for(int i = 0; i < 360; i += 5) {
    se2.addPoint(
        BigDecimal.valueOf(Math.sin(Math.toRadians(i))).round(mc),
        BigDecimal.valueOf(Math.cos(Math.toRadians(i))).round(mc)
    );  
}

c.addElements(se, se2);
return c;