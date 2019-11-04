var assert = require("assert"),
	should = require('should'),
	BufferedArray = require("../buffered-array");

describe('BufferedArray', function(){
	
	it('should invoke callback on threashold reached', function(done){
		var bufArray = new BufferedArray(2, function(items){
			items.length.should.equal(2);
			done();
		});

		bufArray.push(1);
		bufArray.push(2);
	});

	it('can flush remaining items on request', function(done){
		var bufArray = new BufferedArray(10, function(items){
			items.length.should.equal(2);
			done();
		});
		
		bufArray.push(1);
		bufArray.push(2);

		bufArray.flush();
	});

	it('flush on empty buffer dont throw an error', function(done){
		var bufArray = new BufferedArray(10, function(items){
			done();
		});

		bufArray.flush();
	});

});