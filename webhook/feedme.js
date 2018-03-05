var express = require('express');
var bodyParser = require("body-parser");

var app = express();

app.use(bodyParser.urlencoded({ extended: false }));
app.use(bodyParser.json());


app.get('/*', function(req, res){
    console.log(req.method, req.url );
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.end('Use POST method!\n');
});

app.post('/*', function(req, res){
    console.log(req.method, req.url );
    // console.log(req.headers);
    console.log(JSON.stringify(req.body, null, 2));
    res.writeHead(200, {'Content-Type': 'text/html'});
    res.end('Node: Got it!\n');
});

port = 3000;
app.listen(port);
console.log("Server started....");