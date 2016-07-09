var express=require('express');
var app = express();
var mongojs = require('mongojs');
var db =mongojs('jpc',['users','contents']);
var bodyParser = require('body-parser');
app.use(express.static(__dirname+"/public"));
 //app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

//SIGN UP
app.post('/signup',function(req,res){
console.log(req.body);

db.users.insert({"uname":req.body.uname,"email":req.body.email,"password":req.body.password,"type":req.body.type},function(err,docs){
console.log(docs);
if(err==null)
{
  res.json({"status":1,"message":docs});
}


});

});
// SIGN IN
app.post('/signin',function(req,res){
console.log("sign in");
console.log(req.body);
db.users.find({"email":req.body.email,"password":req.body.password,"type":req.body.type},function(err,docs){

console.log(docs);
if(docs)
{
  res.json({"status":1,"message":docs[0]._id});
}else{
  res.json({"status":0,"message":"no user found"});
}


});

});

// CREATE CONTENT

app.post('/createContent',function(req,res){

  console.log(req.body);

  db.contents.insert({"name":req.body.name,"intro":req.body.intro,"youtube":req.body.youtube,"course_creator_id":req.body.ccid,"content_category":req.body.cat},function(err,docs){
  console.log(docs);
  if(err==null)
  {
    res.json({"status":1,"message":docs});
  }else{
    res.json({"status":0,"message":err});
  }


  });

});

app.listen(3001);
console.log("running 3001");
