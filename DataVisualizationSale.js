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
    var lastScript = scripts[scripts.length - 4];
    var scriptName = lastScript;
    console.log(scriptName);
    return {
      data: scriptName.getAttribute("data"),
    };
  }
  data = getSyncScriptParams();
  const result = data.data;
  var obj = JSON.parse(result);
  var data = new google.visualization.DataTable();
  data.addColumn("string", "Product Name");
  data.addColumn("number", "Total Sales Dollar Amount");
  var result1 = [];
  for (var i in obj) {
    result1.push(obj[i]);
  }
  result2 = result1.map((object) => [
    object.productname,
    object.price * object.count,
  ]);
  console.log(result2);
  data.addRows(result2);

  // Set chart options
  var options = {
    title: "Sales By Product",
    width: 600,
    height: 600,
  };

  // Instantiate and draw our chart, passing in some options.
  var chart = new google.visualization.BarChart(
    document.getElementById("chart_div")
  );
  chart.draw(data, options);
}
