const mongoose = require('mongoose');

mongoose.connect('mongodb://localhost/blog');

let newsSchema = new mongoose.Schema({
    hash: {
        type: String,
        required: true,
        unique: true
    },

    title: {
        type: String,
        required: true
    },
    from: {
        type: String,
        required: true,
    },
    link: {
        type: String,
        required: true,
    },
    stylesheets: {
        type: String,
    },
    scripts: {
        type: String,
    },
    article: {
        type: String,
        required: true
    }
});

module.exports = mongoose.model('News', newsSchema);
