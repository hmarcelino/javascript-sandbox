var BufferedArray = function(size, cb) {
    var ar = [];

    return {
        push: function(k) {
            ar.push(k);

            if (ar.length === size) {
                // clone array
                this.flush();
            }
        },
        flush: function() {
            cb(ar.slice(0));
            ar = [];
        }
    }
};

module.exports = BufferedArray;