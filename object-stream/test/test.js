var assert = require("assert"),
	should = require('should'),
	ObjectStream = require('../object-stream.js');

describe('DuplexStream tests', function(){

	describe('basic read and write', function(){

		var expectedObj;

		before(function(){
			expectedObj = {a:1};
		});

		it('can_read_and_write', function(done){
			var counter = 0;

			var streamReader = new ObjectStream.Duplex({
				read:function(){
					return counter++ === 0 ? expectedObj : undefined;
				}
			});

			var streamWriter = new ObjectStream.Duplex({
				write : function(obj){
					obj.should.eql(expectedObj);	
					done();
				}
			});

			streamReader.pipe(streamWriter);

		});

		it('can_read_and_write_multiple_messages', function(done){

			var numOfMessages=10,
			reads = 0,
			writes = 0;

			var streamReader = new ObjectStream.Duplex({
				read:function(){
					if(reads++ < numOfMessages){
						return expectedObj;		
					}
				}
			});

			var streamWriter = new ObjectStream.Duplex({
				write : function(obj){
					obj.should.eql(expectedObj);	

					if(++writes === 10){
						done();	
					}
				}
			});

			streamReader.pipe(streamWriter);

		});

	});

	describe('extreme piping', function(){

		var print;

		before(function(){
			print = false;
		});

		it('can_read_wite_transform_log_the_universe', function(){
			var beforelogStream = new ObjectStream.Log("before", print),
				afterlogStream = new ObjectStream.Log("after", print),
				counterStream = new ObjectStream.Counter();

			var numOfMessages=10,
				reads = 0,
				writes = 0;

			var cloneTotalCounter = 0,
				cloneCounter = 0;

			var streamReader = new ObjectStream.Duplex({
				read:function(){
					if(reads++ < numOfMessages){
						return {a:1};		
					}
				}
			});

			var cloneObjectStream = new ObjectStream.Duplex({
				read : function(){
					if(cloneCounter < cloneTotalCounter){
						cloneCounter++;
						return {a:2};
					}
				}, 
				write: function(obj){
					cloneTotalCounter += 2;
				}
			});

			streamReader.pipe(beforelogStream)
				.pipe(cloneObjectStream)
				.pipe(afterlogStream)
				.pipe(counterStream);
		});
	});

});

