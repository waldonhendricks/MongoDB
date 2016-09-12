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

public class MongoDBJDBC {
	public static void main(String[] args) {
		MongoClient mongoClient = null;
		try {
			// 连接到 mongodb 服务
			mongoClient = new MongoClient("localhost", 27017);

			// 连接到数据库
			MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
			System.out.println("Connect to database successfully");

			/*
			 * mongoDatabase.createCollection("testCollection");
			 * System.out.println("集合创建成功");
			 */

			MongoCollection<Document> collection = mongoDatabase.getCollection("testCollection");
			System.out.println("集合 test 选择成功");

			Document document = new Document("title", "MongoDB").append("description", "database").append("likes", 100)
					.append("by", "Fly");
			List<Document> documents = new ArrayList<Document>();
			documents.add(document);
			collection.insertMany(documents);
			System.out.println("文档插入成功");

			FindIterable<Document> findIterable = collection.find();
			MongoCursor<Document> mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				System.out.println(mongoCursor.next());
			}

			// 更新文档 将文档中likes=100的文档修改为likes=200
			collection.updateMany(Filters.eq("likes", 100), new Document("$set", new Document("likes", 200)));
			// 检索查看结果
			findIterable = collection.find();
			mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				System.out.println(mongoCursor.next());
			}

			// 删除符合条件的第一个文档
			collection.deleteOne(Filters.eq("likes", 200));
			// 删除所有符合条件的文档
			collection.deleteMany(Filters.eq("likes", 200));
			// 检索查看结果
			findIterable = collection.find();
			mongoCursor = findIterable.iterator();
			while (mongoCursor.hasNext()) {
				System.out.println(mongoCursor.next());
			}
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
		} finally {
			mongoClient.close();
		}
	}
}
