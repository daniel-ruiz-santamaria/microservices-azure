const sql = require("../repository/db")

// Constructor
const Party = function(customer) {
    this.id = customer.id;
}

Party.getAll = result => {
    sql.query('SELECT * from politic_party', (err, res) => {
        if (err) {
            console.log("error:", err);
            result(null, err);
            return;
        }

        console.log("politic_party: ", res);
        result(null, res); 
    });
};

module.exports = Party;