# Map Reduce

The map reduce is a program that will read all data from the NoSQL database, process it and will then place it in the mysql server.


## Configuration parameters
the configuration paramters can be found in the `CONFIG.TXT` file.
 
 | Parameter     | Goal                | 
  | ------------- | :-----------------: |
  | SQL.URL       | This url will point to the SQL database.  | 
  | SQL.USER / SQL.PASSWORD        | These are the credentials for the SQL database  | 
  | TIMEOUT        | The code will constantly loop and repeat itself, this is done every x milli seconds  |
  | MONGO.DB / MONGO.COLLECTION          | These parameters point to the correct NO-SQL database. | 

To run the program, execute `MapReduce`