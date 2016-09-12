package wjw.mongoDB;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Relationship {
	public static void main(String[] args) {
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient("localhost", 27017);
			MongoDatabase db = mongoClient.getDatabase("weijingwei");
			MongoCollection<Document> userCol = db.getCollection("user");
			if (userCol == null) {
				db.createCollection("user");
				userCol = db.getCollection("user");
			}
			MongoCollection<Document> addressCol = db.getCollection("address");
			if (addressCol == null) {
				db.createCollection("address");
				addressCol = db.getCollection("address");
			}
			List<Document> documents = new ArrayList<Document>();
			Document document = new Document("state", "China").append("city", "PeiJing").append("location", "xierqi").append("pincode", 100000);
			documents.add(document);
			document = new Document("state", "China").append("city", "PeiJing").append("location", "shangdi").append("pincode", 100001);
			documents.add(document);
			addressCol.insertMany(documents);
			document = new Document("name", "weijingwei").append("contact", 82451479).append("address_ids", documents);
			userCol.insertOne(document);
			FindIterable<Document> find = userCol.find();
			MongoCursor<Document> iterator = find.iterator();
			while (iterator.hasNext()) {
				document = iterator.next();
//				System.out.println(document);
			}
			find = addressCol.find();
			iterator = find.iterator();
			while (iterator.hasNext()) {
				document = iterator.next();
//				System.out.println(document);
			}
			find = userCol.find(Filters.eq("name", "weijingwei"));
			iterator = find.iterator();
			while (iterator.hasNext()) {
				document = iterator.next();
				System.out.println(document);
			}
			userCol.deleteMany(Filters.eq("name", "weijingwei"));
			addressCol.deleteMany(Filters.eq("state", "China"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mongoClient.close();
		}
	}
}
