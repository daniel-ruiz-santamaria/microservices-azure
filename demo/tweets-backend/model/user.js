const sql = require("../repository/db")
const Tweet = require("../model/tweets.js")


// Constructor
const UserComplex = function(user) {
    this.id = user.u_id;
    this.description = user.u_description;
    this.followers = user.u_followers;
    this.friends = user.u_friends;
    this.location = user.u_location;
    this.name = user.u_name;
    this.party = user.u_party;
    this.tweets = [];
}

// Constructor
const User = function(user) {
    this.id = user.u_id;
    this.description = user.u_description;
    this.followers = user.u_followers;
    this.friends = user.u_friends;
    this.location = user.u_location;
    this.name = user.u_name;
    this.party = user.u_party;
}

User.getAllNested = result => {
    usersComplex = {}
    sql.query(`SELECT u.id u_id,u.description u_description, u.followers u_followers, u.friends u_friends, u.location u_location,u.name u_name, u.politic_party_id u_party,
    t.id t_id, t.coordinates t_coordinates, t.created_at t_created_at, t.favorites t_favorites, t.place t_place, t.retweets t_retweets, 
    t.sentiment_polarity t_sentiment_polarity, t.sentiment_subjectivity t_sentiment_subjectivity, t.source t_source, t.text t_text
    from user as u
    left join tweet as t on u.id = t.user_id`, (err, res) => {
        if (err) {
            console.log("error:", err);
            result(null, err);
            return;
        }
        console.log('getAllNested');
        //console.log("user: ", res);
        for (i = 0 ; i < res.length ; i++) {
            var u;
            if (res[i]['u_id'] in usersComplex) {
                u = usersComplex[res[i]['u_id']];
            } else {
                u = new UserComplex(res[i]);
            }
            var t = new Tweet(res[i]);
            u.tweets.push(t);
            usersComplex[res[i]['u_id']] = u;
        }

        result(null, Object.values(usersComplex)); 
    });
};

User.getAllNestedByParty = (party, result) => {
    usersComplex = {}
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

        console.log('getAllNestedByParty',party);
        for (i = 0 ; i < res.length ; i++) {
            var u;
            if (res[i]['u_id'] in usersComplex) {
                u = usersComplex[res[i]['u_id']];
            } else {
                u = new UserComplex(res[i]);
            }
            var t = new Tweet(res[i]);
            u.tweets.push(t);
            usersComplex[res[i]['u_id']] = u;
        }

        result(null, Object.values(usersComplex)); 
    });
};

User.getAll = result => {
    usersComplex = {}
    sql.query(`SELECT u.id u_id,u.description u_description, u.followers u_followers, u.friends u_friends, u.location u_location,u.name u_name, u.politic_party_id u_party
     from user as u`, (err, res) => {
        if (err) {
            console.log("error:", err);
            result(null, err);
            return;
        }

        console.log('getAll');
        userList = [];
        for (i = 0 ; i < res.length ; i++) {
            var u = new User(res[i]);
            userList.push(u);
        }

        result(null, userList);
    });
};

User.getAllByParty = (party, result) => {
    usersComplex = {}
    sql.query(`SELECT u.id u_id,u.description u_description, u.followers u_followers, u.friends u_friends, u.location u_location,u.name u_name, u.politic_party_id u_party
     from user as u WHERE politic_party_id = '${party}'`, (err, res) => {
        if (err) {
            console.log("error:", err);
            result(null, err);
            return;
        }

        console.log('getAllByParty');
        userList = [];
        for (i = 0 ; i < res.length ; i++) {
            var u = new User(res[i]);
            userList.push(u);
        }

        result(null, userList);
    });
};



module.exports = User;