var express    = require('express');
var mysql      = require('mysql');
var dbconfig   = require('./config/database.js');
var connection = mysql.createConnection(dbconfig);

var app = express();

var fs = require('fs');
// configuration ===============================================================
app.set('port', process.env.PORT || 8080);
app.get('/', function(req, res){

  fs.createReadStream("index.html").pipe(res);

});

app.get('/gps_ex', function(req, res){

  connection.query('SELECT no, context from coap_data', function(err, rows) {
    if(err) throw err;

    console.log('The solution is: ', rows);
    res.send(rows);
  });
});

app.get('/gps_real', function(req, res){

  connection.query('SELECT lat, lon from GPS', function(err, rows) {
    if(err) throw err;

    console.log('The solution is: ', rows);
    res.send(rows);
  });
});

app.listen(app.get('port'), function () {
  console.log('Express server listening on port ' + app.get('port'));
});
