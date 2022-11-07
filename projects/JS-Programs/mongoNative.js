const uri = "";
const client = new MongoClient(uri);

    try {
        // Connect to the MongoDB cluster
        await client.connect();
        console.log("Successfully connected to MongoDB server!");

} catch (e) {
    console.error(e);
    
} finally {
    client.close();
}
