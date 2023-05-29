package ibf2022.batch2.csf.backend.repositories;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import ibf2022.batch2.csf.backend.models.Archives;

@Repository
public class ArchiveRepository {

	@Autowired
	private AmazonS3 s3;

	@Autowired
	private MongoTemplate mongoTemplate;

	//TODO: Task 4
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	// db.archives.insertOne(doc)
	public void recordBundle(Archives arc) {
		ObjectListing objListing = s3.listObjects("kai", arc.getBundleId());
		List<String> keys = new ArrayList<String>();
		for (S3ObjectSummary objectSummary : objListing.getObjectSummaries()) {
			GetObjectRequest getReq = new GetObjectRequest("kai", objectSummary.getKey());
			S3Object result = s3.getObject(getReq);
			keys.add("https://kai.sgp1.digitaloceanspaces.com/" + result.getKey());
		}
		System.out.println("keylist: " + keys);
		arc.setUrls(keys);

		Document doc = Document.parse(arc.toJSON().toString());
		mongoTemplate.insert(doc, "archives");
	}

	//TODO: Task 5
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public Object getBundleByBundleId(/* any number of parameters here */) {
		return null;
	}

	//TODO: Task 6
	// You are free to change the parameter and the return type
	// Do not change the method's name
	// Write the native mongo query that you will be using in this method
	//
	//
	public Object getBundles(/* any number of parameters here */) {
		return null;
	}


}
