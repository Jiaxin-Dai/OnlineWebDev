import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.AggregationOutput;
import java.util.*;

public class GenerateRandomReviews {

    static DBCollection myReviews;

    public static String getRandomItem(List<String> items) {
        return items.get(new Random().nextInt(items.size()));
    }

    public static Product getRandomProduct(List<Product> items) {
        return items.get(new Random().nextInt(items.size()));
    }

    public static BasicDBObject generateReview() {
        ArrayList<String> usernameList = new ArrayList<>();
        ArrayList<String> reviewratingList = new ArrayList<>();
        ArrayList<String> reviewdateList = new ArrayList<>();
        ArrayList<String> reviewtextList = new ArrayList<>();
        ArrayList<String> retailerpinList = new ArrayList<>();
        ArrayList<String> ageList = new ArrayList<>();
        ArrayList<String> genderList = new ArrayList<>();
        ArrayList<String> occupationList = new ArrayList<>();
        HashMap<String, User> map = new HashMap();
        try {
            map = MySqlDataStoreUtilities.selectUser();
        } catch(Exception e) {
            System.out.println(e);
        }

        for (int i = 0; i < 20; i++) {
            ArrayList<User> userList = new ArrayList<User>(map.values());
            System.out.println(userList.size());
            usernameList.add(userList.remove(i).getName());
        }

        for (int i = 3; i <= 5; i++) {
            reviewratingList.add(Integer.toString(i));
        }

        for (int i = 1; i <= 30; i++) {
            reviewdateList.add("2020-01-" + Integer.toString(i));
        }

        reviewtextList.add("Good product");
        reviewtextList.add("Nicely done");
        reviewtextList.add("Really Cool");
        reviewtextList.add("I like it");
        reviewtextList.add("Awesome");
        reviewtextList.add("Good Price");
        reviewtextList.add("Good Performance");

        retailerpinList.add("31827");
        retailerpinList.add("92037");
        retailerpinList.add("60616");
        retailerpinList.add("10001");
        retailerpinList.add("60120");

        for (int i = 10; i < 60; i++) {
            ageList.add(Integer.toString(i));
        }

        genderList.add("male");
        genderList.add("female");
        occupationList.add("doctor");
        occupationList.add("lawyer");
        occupationList.add("teacher");
        occupationList.add("engineer");
        occupationList.add("software developer");

        String zip = getRandomItem(retailerpinList);
        HashMap<Integer, Product> productMap = null;
        try {
            productMap = MySqlDataStoreUtilities.selectAllProducts();
            System.out.println(productMap.size());
        } catch (Exception e) {
            System.out.println(e);
        }
        ArrayList<Product> products = new ArrayList<>(productMap.values());

        Product p = getRandomProduct(products);
        BasicDBObject doc = new BasicDBObject("title", "myReviews").append("userName", getRandomItem(usernameList))
                .append("productName", p.getProductName()).append("productType", p.getType()).append("productMaker", p.getBrand())
                .append("reviewRating", Integer.parseInt(getRandomItem(reviewratingList)))
                .append("reviewDate", getRandomItem(reviewdateList)).append("reviewText", getRandomItem(reviewtextList))
                .append("retailerpin", zip).append("onSale", "no").append("rebate", "no")
                .append("age", getRandomItem(ageList)).append("gender", getRandomItem(genderList))
                .append("occupation", getRandomItem(occupationList)).append("price", p.getPrice());

        doc = GenerateRandomReviews.insertMore(doc, zip);
        return doc;
    }

    public static void main(String[] args) {
        try {
            MongoClient mongo;
            mongo = new MongoClient("localhost", 27017);

            DB db = mongo.getDB("ProjectReviews");
            myReviews = db.getCollection("myReviews");
            for (int i = 0; i < 50; i++) {
                BasicDBObject review = generateReview();
                myReviews.insert(review);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static BasicDBObject insertMore(BasicDBObject doc, String zip) {
        switch (zip) {
            case ("31827"): {
                doc.append("retailercity", "Shiloh").append("storeName", "Shiloh Homehub").append("retailerState",
                        "Georgia");
                break;
            }
            case ("92037"): {
                doc.append("retailercity", "La Jolla").append("storeName", "San Diego Homehub").append("retailerState",
                        "California");
                break;
            }
            case ("60616"): {
                doc.append("retailercity", "Chicago").append("storeName", "Chicago Homehub").append("retailerState",
                        "Illinois");
                break;
            }
            case ("10001"): {
                doc.append("retailercity", "New York").append("storeName", "New York Homehub").append("retailerState",
                        "New York");
                break;
            }
            case ("60120"): {
                doc.append("retailercity", "Elgin").append("storeName", "Elgin Homehub").append("retailerState",
                        "Illinois");
                break;
            }
        }
        return doc;
    }
}
