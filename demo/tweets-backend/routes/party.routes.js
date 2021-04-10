module.exports = app => {
    const customers = require("../controllers/party.controller.js");
  
    // Retrieve all Customers
    app.get("/parties", customers.findAll);

  };