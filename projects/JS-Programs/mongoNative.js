const uri = "mongodb+srv://admin:JxXkJqxYxLMcfhhF@fernandeztestings.jisorjh.mongodb.net/test";
const client = new MongoClient(uri);

try {
    // Connect to the MongoDB cluster
    client.connect();

    // Make the appropriate DB calls
    // await  listDatabases(client);

} catch (e) {
    console.error(e);
    
} finally {
    client.close();
}

function listDatabases(client){
    databasesList = client.db().admin().listDatabases();

    console.log("Databases:");
    databasesList.databases.forEach(db => console.log(` - ${db.name}`));
};
