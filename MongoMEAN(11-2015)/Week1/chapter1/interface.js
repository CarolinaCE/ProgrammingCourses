/*
 *  Inserts "doc" into the collection "movies".
 */
exports.insert = function(db, doc, callback) {
  	// Implement a simple insert function that returns the error
  	// callback if something failed
  	db.collection('movies').insert(doc, function(error, doc) {
		if (error) {
			return callback(error);
	  	}

  	  	callback(error, doc);
  	}); 
};

/*
 *  Finds all documents in the "movies" collection
 *  whose "director" field equals the given director,
 *  ordered by the movie's "title" field. See
 *  http://mongodb.github.io/node-mongodb-native/2.0/api/Cursor.html#sort
 */ 
exports.byDirector = function(db, director, callback) {
  	// Given the db handler, sort the results of a query
  	// made by director
  	var query = {director: director};

  	db.collection('movies').find(query).sort({title: 1}).toArray(function(error, docs) {
  		console.log("Length is " + docs.length);
  		if (error) {
  			return callback(error);
  		}

  		console.log('Sorted movies for director');
  		console.log(docs);
  		callback(error, docs);
  	});
};