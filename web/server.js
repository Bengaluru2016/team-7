var express=require('express');
var app = express();
var mongojs = require('mongojs');
var db =mongojs('jpc',['users','contents']);
var bodyParser = require('body-parser');
var ObjectId = mongojs.ObjectId;
app.use(express.static(__dirname+"/public"));
 //app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

//SIGN UP
app.post('/signup',function(req,res){
console.log(req.body);

db.users.insert({"uname":req.body.uname,"email":req.body.email,"password":req.body.password,"type":req.body.type,"courses":[]},function(err,docs){
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

// ENTER COURSE
app.post('/enterCourse',function(req,res){

var course=[];
var courseId;
  console.log(req.body);

  db.users.find({"_id":ObjectId(req.body.id)},function(err,docs){
course=docs[0].courses;

console.log(course);

course.push(req.body.courseId);
  db.users.update({"_id":ObjectId(req.body.id)},{$set:{courses:course}},function(err,docs){
    //res.json(docs)

    db.contents.find({"_id":ObjectId(req.body.courseId)},function(err,docs){
      res.json(docs);
    });
  });

  });
});

//GET PROFILE

app.post('/getProfile',function(req,res){

  db.users.find({"_id":ObjectId(req.body.id)},function(err,docs){

    res.json({"status":1,"message":docs[0]});
  });
})
app.listen(3001);
console.log("running 3001");
