#!/usr/bin/env node

var Stream = require('stream');

var ObjectStream = {};

ObjectStream.Duplex = function(options){
	var stream = new Stream.Duplex({objectMode: true, allowHalfOpen: true});

	var buildReadFn = function(readFn){
		return function(){
			var obj = readFn();

			if(obj){
				stream.push(obj);
			}
		}
	};

	var buildWriteFn = function(writeFn){
		return function(obj, enc, next){
			writeFn(obj);	
			
			stream._read(0);
			next();
		}
	};

	(function init(){

		if(options && options.read){
			stream._read = buildReadFn(options.read);

		}else {
			stream._read = function(){};			
		}

		if(options && options.write){
			stream._write = buildWriteFn(options.write);

		}else {
			stream._write = function(){};
		}

	})();

	return stream;
};

ObjectStream.Counter = function(name){
	var counter = {inbound: 0, outbound: 0},
		buffer = [],
		stream;

	(function init(){
		stream = ObjectStream.Duplex({
			write: function(obj){
				buffer.push(obj);
				counter.inbound++;
			}, 
			read:function(){
				var obj = buffer.shift();

				if (obj) {
					counter.outbound++;
					return obj;
				}
			}, 
			counter: function(){
				console.log(name, counter);
			}
		})

	})();

	return stream;
};

ObjectStream.Log = function(name, print){
	var buffer = [],
		stream;

	(function init(){
		stream = ObjectStream.Duplex({
			write: function(obj){
				buffer.push(obj);
			}, 

			read:function(){
				var obj = buffer.shift();

				if (obj) {

					if(print) {
						console.log("LogStream "+name+" read", JSON.stringify(obj))
					};

					return obj;
				}
			}
		})

	})();

	return stream;
};

module.exports = ObjectStream;
