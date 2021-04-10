module.exports = app => {
    const users = require("../controllers/user.controller.js");

    // Retrieve all Customers Nested
    app.get("/users/nested/", users.findAllNested);

    // Retrieve all Customers by Party Nested
    app.get("/users/nested/:party", users.getAllNestedByParty);

    // Retrieve all Customers 
    app.get("/users", users.findAll);

    // Retrieve all Customers by Party
    app.get("/users/:party", users.getAllByParty);

  };