return new Chart(new Date().toString(), "{color: #567300; font-size: 14px}")
    .addElements(new SketchBarChart("#81AC00", "#567300", 5)
        .addValues(9, 8, 7, 6, 5, 4, 3, 2, 1));