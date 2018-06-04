const express = require('express');
const bodyParser = require('body-parser');
const crypto = require('crypto');

const News = require('./models/news');

let hashValue = (string) => {
    let hash = crypto.createHash('sha512');
    hash.update(string);
    return hash.digest('hex');
};

let app = express();

//parse application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({
    extended: false
}));

// parse application/json
app.use(bodyParser.json());

// express config 
app.use(express.static('public'));

let xhrOnly = (req, res, next) => {
    if(req.xhr){
        next();
        return;
    }
    res.status(200).sendFile('index.html', {root: __dirname+'/public/'});
};

app.use(['/show', '/index'], xhrOnly);

app.get('/', (req, res) => {
    res.redirect('index.html');
});


app.get('/stories', (req, res) => {
    News.find(null, 'title', (err, docs) =>{
        res.status(200).send({
            'links': docs
        });
        res.end();
    });
});

app.get('/show/:id', (req, res) => {
    res.set({'Content-type': 'application/json'});
    if(req.params.id){
        News.findById(req.params.id, 'title from article stylesheets scripts', (err, blog) => {
            if(err != null){
                res.status(404).json({
                    error: 'No such document'
                });
            }
            else{
                res.status(200).json({
                    blog: blog
                });
            }
        });
    }
});

app.post('/post', (req, res) => {
    res.set({'Content-type':'application/json'});
    let postHash = hashValue(req.body.link);
    News.find({hash: postHash}, 'hash', (err, docs) => {
        if(err){
            global.console.log(err);
            res.redirect('index.html');
        }
        else if(docs.length == 0){
            let newNews = new News(req.body);
            newNews.hash = postHash;
            newNews.save();
            res.redirect('index.html');
        }
        else{
            res.redirect('index.html');
        }
    });
});

app.listen(8080, () => {
    global.console.log('Magic happening at localhost:8080...');
});
