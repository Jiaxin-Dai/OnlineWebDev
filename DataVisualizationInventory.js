// Load the Visualization API and the corechart package.
google.charts.load("current", { packages: ["corechart"] });

// Set a callback to run when the Google Visualization API is loaded.
google.charts.setOnLoadCallback(drawChart);

// Callback that creates and populates a data table,
// instantiates the pie chart, passes in the data and
// draws it.
function drawChart() {
  // Create the data table.
  function getSyncScriptParams() {
    var scripts = document.getElementsByTagName("script");
    var lastScript = scripts[scripts.length - 1];
    var scriptName = lastScript;
    return {
      data: scriptName.getAttribute("data-1"),
    };
  }
  data = getSyncScriptParams();
  const result = data.data;
  var obj = JSON.parse(result);
  var data = new google.visualization.DataTable();
  data.addColumn("string", "Product Name");
  data.addColumn("number", "Total Numbers Available");
  var result1 = [];
  for (var i in obj) {
    result1.push(obj[i]);
  }
  console.log(result1);
  result2 = result1.map((object) => [object.productName, object.stock]);
  console.log(result2);
  data.addRows(result2);

  // Set chart options
  var options = {
    title: "Stock by Product",
    width: 600,
    height: 1500,
  };

  // Instantiate and draw our chart, passing in some options.
  var chart = new google.visualization.BarChart(
    document.getElementById("chart_div")
  );
  chart.draw(data, options);
}
