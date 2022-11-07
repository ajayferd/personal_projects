async function main(){
    /**
     * Connection URI. Update <username>, <password>, and <your-cluster-url> to reflect your cluster.
     * See https://docs.mongodb.com/ecosystem/drivers/node/ for more details
     */
    const uri = "mongodb+srv://admin:JxXkJqxYxLMcfhhF@fernandeztestings.jisorjh.mongodb.net/?retryWrites=true&w=majority";

    const client = new MongoClient(uri);

    try {
        // Connect to the MongoDB cluster
        await client.connect();
        console.log("Successfully connected to MongoDB server!");

    } catch (e) {
        console.error(e);
    } finally {
        await client.close();
    }
}
