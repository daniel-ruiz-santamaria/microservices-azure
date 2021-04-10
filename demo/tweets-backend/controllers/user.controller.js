const User = require("../model/user.js")

// Retrieve all User from the database.
exports.findAll = (req, res) => {
  console.log('controller','findAll');
  User.getAll((err, data) => {
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
  User.getAllByParty( req.params.party,(err, data) => {
      if (err)
        res.status(500).send({
          message:
            err.message || "Some error occurred while retrieving customers."
        });
      else res.send(data);
    });
};

// Retrieve all User from the database.
exports.findAllNested = (req, res) => {
  console.log('controller','findAllNested');
  User.getAllNested((err, data) => {
        if (err)
          res.status(500).send({
            message:
              err.message || "Some error occurred while retrieving customers."
          });
        else res.send(data);
      });
};

// Retrieve all User from party in the database.
exports.getAllNestedByParty = (req, res) => {
  console.log('controller','getAllNestedByParty');
  User.getAllNestedByParty( req.params.party,(err, data) => {
      if (err)
        res.status(500).send({
          message:
            err.message || "Some error occurred while retrieving customers."
        });
      else res.send(data);
    });
};