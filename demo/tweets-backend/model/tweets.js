const sql = require("../repository/db")

// Constructor
const Tweet = function(tweet) {
    this.id = tweet.t_id;
    this.coordinates = tweet.t_coordinates;
    this.created_at = tweet.t_created_at;
    this.favorites = tweet.t_favorites;
    this.place = tweet.t_place;
    this.retweets = tweet.t_retweets;
    this.sentiment_polarity = tweet.t_sentiment_polarity;
    this.sentiment_subjectivity = tweet.t_sentiment_subjectivity;
    this.source = tweet.t_source;
    this.text = tweet.t_text;
    this.party = tweet.u_party;
}

Tweet.getAll = result => {
    tweets = [];
    sql.query(`SELECT u.politic_party_id u_party,
    t.id t_id, t.coordinates t_coordinates, t.created_at t_created_at, t.favorites t_favorites, t.place t_place, t.retweets t_retweets, 
    t.sentiment_polarity t_sentiment_polarity, t.sentiment_subjectivity t_sentiment_subjectivity, t.source t_source, t.text t_text
    from user as u
    left join tweet as t on u.id = t.user_id`, (err, res) => {
        if (err) {
            console.log("error:", err);
            result(null, err);
            return;
        }

        for (i = 0 ; i < res.length ; i++) {
            var t = new Tweet(res[i]);
            tweets.push(t);
        }

        result(null, tweets); 
    });
};

Tweet.getAllByParty = (party, result) => {
    tweets = [];
    sql.query(`SELECT u.id u_id,u.description u_description, u.followers u_followers, u.friends u_friends, u.location u_location,u.name u_name, u.politic_party_id u_party,
    t.id t_id, t.coordinates t_coordinates, t.created_at t_created_at, t.favorites t_favorites, t.place t_place, t.retweets t_retweets, 
    t.sentiment_polarity t_sentiment_polarity, t.sentiment_subjectivity t_sentiment_subjectivity, t.source t_source, t.text t_text
    from user as u
    left join tweet as t on u.id = t.user_id WHERE politic_party_id = '${party}'`, (err, res) => {
        if (err) {
            console.log("error:", err);
            result(null, err);
            return;
        }

        for (i = 0 ; i < res.length ; i++) {
            var t = new Tweet(res[i]);
            tweets.push(t);
        }

        result(null, tweets); 
    });
};



module.exports = Tweet;