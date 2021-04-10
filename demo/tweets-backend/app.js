const express = require('express')
const mysql = require('mysql')
const bodyParser = require('body-parser')
const PORT = process.env.PORT || 3050
const cors = require('cors');

const app = express();

// parse requests of content-type: application/json
app.use(bodyParser.json());

// parse requests of content-type: application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: true }));

// Setting up the cors config


// Route
// simple route
app.get("/", (req, res) => {
    res.json({ message: "Welcome to bezkoder application." });
  });

require("./routes/party.routes.js")(app);
require("./routes/user.routes.js")(app);
require("./routes/tweets.routes.js")(app);


app.listen(PORT, () => console.log(`Server running on port ${PORT}`));