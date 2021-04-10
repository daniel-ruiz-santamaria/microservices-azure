const Party = require("../model/party.js")

// Retrieve all Parties from the database.
exports.findAll = (req, res) => {
    Party.getAll((err, data) => {
        if (err)
          res.status(500).send({
            message:
              err.message || "Some error occurred while retrieving customers."
          });
        else res.send(data);
      });
};