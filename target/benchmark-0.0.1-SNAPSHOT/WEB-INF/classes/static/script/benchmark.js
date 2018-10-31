var drawingManager;
var selectedShape;
var allShape = [];
var sendChartData;
var receiveChartData;
var map;
var chart;

var options = {
  chart: {
    title: 'Wyniki'
  },
  backgroundColor: { fill:'transparent' },
  crosshair: {
      color: '#000',
      trigger: 'selection'
    }
};



function clearSelection () {
    if (selectedShape) {       
        selectedShape = null;
    }
}
function setSelection (shape) {
    selectedShape = shape;
}
function deleteAllShape () {
    if (allShape.length != 0) {
        for(i = 0; i<allShape.length; i++){
            allShape[i].setMap(null);            
        }
    }
}
//google map
function initMap() {
    map = new google.maps.Map(document.getElementById('map'), {
      center: {lat: 52.5, lng: 19.2},
      zoom: 6
    });

    drawingManager = new google.maps.drawing.DrawingManager({
        drawingMode: google.maps.drawing.OverlayType.POLYGON,
        drawingControlOptions: {
            position: google.maps.ControlPosition.TOP_CENTER,
            drawingModes: ['polygon']
          },
        polygonOptions: {
            strokeWeight: 0,
            fillOpacity: 0.5,
            editable: true,
            draggable: true
        },
        map: map
    });

    google.maps.event.addListener(drawingManager, 'overlaycomplete', function (e) {
        var newShape = e.overlay;
        newShape.type = e.type;

        allShape.push(newShape);

        
        if (e.type !== google.maps.drawing.OverlayType.MARKER) {
            drawingManager.setDrawingMode(null);
            google.maps.event.addListener(newShape, 'click', function (e) {
                if (e.vertex !== undefined) {
                    if (newShape.type === google.maps.drawing.OverlayType.POLYGON) {
                        var path = newShape.getPaths().getAt(e.path);
                        path.removeAt(e.vertex);
                        if (path.length < 3) {
                            newShape.setMap(null);
                        }
                    }
                }
                setSelection(newShape);
            });
            setSelection(newShape);
        }
        else {
            google.maps.event.addListener(newShape, 'click', function (e) {
                setSelection(newShape);
            });
            setSelection(newShape);
        }    
    });
    google.maps.event.addListener(drawingManager, 'drawingmode_changed', clearSelection);
    google.maps.event.addListener(map, 'click', clearSelection);
    google.maps.event.addDomListener(document.getElementById('delete-button'), 'click', deleteAllShape);

}

//chart

google.charts.load('current', {'packages':['line']});
google.charts.setOnLoadCallback(drawChart);

function drawChart() {
    chart = new google.charts.Line(document.getElementById('chart'));
    var data = new google.visualization.DataTable();
    sendChartData = new google.visualization.DataTable();
    receiveChartData = new google.visualization.DataTable();
    setUpColums(sendChartData);
    setUpColums(receiveChartData);
    
    data.addColumn('string', 'punkty');
    data.addColumn('number', 'MySQL');
    data.addColumn('number', 'PostGIS');
    data.addColumn('number', 'Neo4j');
    data.addColumn('number', 'MongoDb');
    data.addRows([
      ['1',  1, 1, 1, 1]
    ]);


chart.draw(data, google.charts.Line.convertOptions(options));
}

//===============

$(document).ready(function() {
    getPolygonNames();

    $("#save-coordinate-button").on("click", function(){
        if($("#shape-name").val() != ""){
            var polygonLatLng = getPolygonCoords();
            var numberOfPoint = JSON.parse(polygonLatLng).length;
            sendCoordinateAndDisplayTimeOfSave(polygonLatLng, numberOfPoint, $("#shape-name").val());
        } else {
            $("#input-alert").css("background-color", "red")
        }
    });
    
    $("#get-coordinate-button").click(function(){
        var shapeName = $("#selected-coordinate").val();
        getCoordinateFromServer(shapeName);
    });
    
    $("#shape-name").keyup(function(){
        $("#input-alert").css("background-color", "white")
    });
    
    $("#disp-send-result").click(function(){
        refreshChart(sendChartData);
    });

    $("#disp-receive-result").click(function(){
        refreshChart(receiveChartData);
    });
    
    $("#generate").click(function () {
       var numberOfPoints = $("#generate_value").val();
       var numberGeneratePoint = numberOfPoints - 1;
       var startLat = 51;
       var lng = 18;
       var shapeLatLng = [];
       for (var i = 0; i<numberGeneratePoint; i++){
           shapeLatLng.push({
               lat: startLat,
               lng: lng
           });
           startLat = startLat + 0.001;
       }
        shapeLatLng.push({
            lat: 51,
            lng: 19
        });
       sendCoordinateAndDisplayTimeOfSave(JSON.stringify(shapeLatLng), numberOfPoints, numberOfPoints + "_GENERATE");
    });
});
function getCoordinateFromServer(shapeName){
    $.ajax({
          type:'get',
          url:'http://localhost:8080/coordinate/'+shapeName,
          dataType:'json',
          success:function(data){
              var numberOfPoint = data.coordinates.length;
              var row = prepareDataForDisplay(data,numberOfPoint);
              dislayOnReceiveChart(row);
              var coordinates = jsonToGoogleCoordinateObj(data.coordinates);
              drawShapeOnMap(coordinates);
          }
    });
}

function drawShapeOnMap(coordinates){
    var newShape = new google.maps.Polygon({
        paths: coordinates,
        strokeWeight: 0,
        fillOpacity: 0.5,
        editable: true,
        draggable: true
    });
    newShape.setMap(map);
    allShape.push(newShape);
}

function jsonToGoogleCoordinateObj(json){
    var length = json.length;
    var coordinates = [];
    for(i=0; i<length; i++){
        coordinates.push({
            lat: parseFloat(json[i].lat),
            lng: parseFloat(json[i].lng)
        });
    }
    return coordinates;
}

function getPolygonCoords() {
    var len = selectedShape.getPath().getLength();
    var shapeLatLng = [];
    for (var i = 0; i < len; i++) {
        var latlng = selectedShape.getPath().getAt(i).toUrlValue(5);
        latlng = latlng.split(',');
        shapeLatLng.push({
            lat: latlng[0],
            lng: latlng[1]
        });
    }
    return JSON.stringify(shapeLatLng);
}

function sendCoordinateAndDisplayTimeOfSave(shapeLatLng, numberOfPoint, shapeName){
    console.log("wysłane wpolrzedne:")
    console.log(shapeLatLng);
   $.ajax({
        url: 'http://localhost:8080/coordinate/' + shapeName,
        dataType: 'JSON',
        type: 'POST',
        crossDomain: true,
        contentType: 'application/json',
        data: shapeLatLng,
        processData: false,
        success: function( data){
            var row = prepareDataForDisplay(data, numberOfPoint);
            displayOnSendChart(row);
            getPolygonNames();
        },
        error: function( jqXhr, textStatus, errorThrown ){
            console.log( errorThrown );
        }
    });
}

function prepareDataForDisplay(saveTime, numberOfPoint){
    return [numberOfPoint.toString(),
        saveTime.mySQL,
        saveTime.postGIS,
        saveTime.neo4j,
        saveTime.mongoDb];
}

function displayOnSendChart(row){
    sendChartData.addRow(row);
    refreshChart(sendChartData);
}

function dislayOnReceiveChart(row){
    receiveChartData.addRow(row);
    refreshChart(receiveChartData);
}

function refreshChart(chartData){
    chart.draw(chartData, google.charts.Line.convertOptions(options));
}

function getPolygonNames(){
    $.ajax({
          type:'get',
          url:'http://localhost:8080/coordinateName',
          dataType:'json',
          success:function(data){
              $.each(data, function(k, obj){
                  $("#selected-coordinate").append( $('<option>', { text: obj.name }));
              });
          }
      });
}

function setUpColums(data){
    data.addColumn('string', 'punkty');
    data.addColumn('number', 'MySQL');
    data.addColumn('number', 'PostGIS');
    data.addColumn('number', 'Neo4j');
    data.addColumn('number', 'MongoDb');
}
