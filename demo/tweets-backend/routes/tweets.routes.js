module.exports = app => {
    const tweets = require("../controllers/tweet.controller.js");

    // Retrieve all Tweets Nested
    app.get("/tweets", tweets.findAll);

    // Retrieve all Tweets by Party Nested
    app.get("/tweets/:party", tweets.getAllByParty);

  };