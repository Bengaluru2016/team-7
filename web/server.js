var express=require('express');
var app = express();
var mongojs = require('mongojs');
var db =mongojs('jpc',['users','contents','questions','quize']);
var bodyParser = require('body-parser');
var ObjectId = mongojs.ObjectId;
app.use(express.static(__dirname+"/public"));
 //app.use(bodyParser.json());
app.use(bodyParser.urlencoded({extended: false}));

//SIGN UP
app.post('/signup',function(req,res){
console.log(req.body);

db.users.insert({"uname":req.body.uname,"email":req.body.email,"password":req.body.password,"type":req.body.type,"courses":[],"quize":[]},function(err,docs){
console.log(docs);
if(err==null)
{
  res.json({"status":1,"message":docs[0]});
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
  res.json({"status":1,"message":docs[0]});
}else{
  res.json({"status":0,"message":"no user found"});
}


});

});

// GET COURSE DEPENDING ON type

app.post('/getCourseDependingOnType',function(req,res){
  db.contents.find({"content_category":req.body.content_category},function(err,docs){
   console.log(docs);
    res.json({"status":1,"message":docs});
  });
});

// CREATE CONTENT

app.post('/createContent',function(req,res){

  console.log(req.body);

  db.contents.insert({"name":req.body.name,"intro":req.body.intro,"youtube":req.body.youtube,"course_creator_id":req.body.ccid,"content_category":req.body.cat},function(err,docs){
  console.log(docs);
  if(err==null)
  {
    res.json({"status":1,"message":docs[0]});
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
});

//GET PROFILE COURSES

app.post('/getProfileCourses',function(req,res){

  db.contents.find({"_id":ObjectId(req.body.courseId)},function(err,docs){

    res.json({"status":1,"message":docs[0]});
  });
});

//ADD QUESTION TO FORUN

app.post('/addQuestion',function(req,res){

  db.questions.insert({"user_id":ObjectId(req.body.id),"question":req.body.question,"answers":[]},function(err,docs){

    res.json({"status":1,"message":docs});
  });
});

//ADD ANSWER TO FORUM

app.post('/addAnswer',function(req,res){
var answers=[];
var ansObj={};
  db.questions.find({"_id":ObjectId(req.body.question_id)},function(err,docs){
    answers=docs[0].answers
ansObj[req.body.answerer_id]=req.body.answer
    answers.push(ansObj);
console.log(answers);

    db.questions.update({"_id":ObjectId(req.body.question_id)},{$set:{"answers":answers}},function(err,docs){

      res.json(docs);
    });
  });
});

//ADD QUIZE QUESTION

app.post('/addQuizeQuestion',function(req,res){

var answers=JSON.parse(req.body.answers);

db.quize.insert({"question":req.body.question,"answers":answers,"qposter_id":req.body.qposter_id,"course_id":req.body.course_id,"correct_ans":req.body.cans},function(err,docs){

  res.json(docs);
   });
});

//GET QUIZE QUESTION

app.post('/getQuize',function(req,res){

db.quize.find({"course_id":req.body.course_id},function(err,docs){
  res.json(docs[0]);
});


});

// ADD RESULTS TO USER
app.post("/addResults",function(req,res){
var quize=[];
var obj={};
  db.users.find({"_id":ObjectId(req.body.id)},function(err,docs){
quize=docs[0].quize;
obj[req.body.course_id]=req.body.result;
quize.push(obj);
console.log(quize)
//res.send("yes");
db.users.update({"_id":ObjectId(req.body.id)},{$set:{"quize":quize}},function(err,docs){

  res.send(docs);
});


  });
});

//  db.quize.find({"course_id":req.body.course_id},function(err,docs){
 //
//  res.json(docs);
//  });

//res.json(docs[0]);


//VERIFY ANSWER

//app.post('/verifyAnswer',function(req,res));
app.listen(3001);
console.log("running 3001");
