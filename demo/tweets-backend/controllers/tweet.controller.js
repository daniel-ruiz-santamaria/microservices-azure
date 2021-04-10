const Tweet = require("../model/tweets.js")

// Retrieve all User from the database.
exports.findAll = (req, res) => {
  console.log('controller','findAll');
  Tweet.getAll((err, data) => {
        if (err)
          res.status(500).send({
            message:
              err.message || "Some error occurred while retrieving customers."
          });
        else res.send(data);
      });
};

// Retrieve all User from party in the database.
exports.getAllByParty = (req, res) => {
  console.log('controller','getAllByParty');
  Tweet.getAllByParty( req.params.party,(err, data) => {
      if (err)
        res.status(500).send({
          message:
            err.message || "Some error occurred while retrieving customers."
        });
      else res.send(data);
    });
};
