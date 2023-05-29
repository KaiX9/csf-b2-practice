package ibf2022.batch2.csf.backend.repositories;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@Repository
public class ImageRepository {

	@Autowired
	private AmazonS3 s3;

	// TODO: Task 3
	// You are free to change the parameter and the return type
	// Do not change the method's name
	public String upload(String name, String title, String comments,
			MultipartFile archive) throws IOException {

		String key = UUID.randomUUID().toString().substring(0, 8);
		Tika tika = new Tika();
		try (ZipInputStream is = new ZipInputStream(archive.getInputStream())) {
			ZipEntry zipEntry = is.getNextEntry();
			while (zipEntry != null) {
				if (!zipEntry.isDirectory()) {
					// Set the object metadata
					ObjectMetadata objMetaData = new ObjectMetadata();
					objMetaData.setContentLength(zipEntry.getSize());

					// Create a temporary file to hold the contents of the entry
					File temp = File.createTempFile(UUID.randomUUID().toString(), "temp");
					try (FileOutputStream os = new FileOutputStream(temp)) {
						int len;
						byte[] buffer = new byte[1024];
						while ((len = is.read(buffer)) > 0) {
							os.write(buffer, 0, len);
						}
					}
					// Set content type
					String contentType = tika.detect(temp);
					objMetaData.setContentType(contentType);

					// Upload the file to S3
					PutObjectRequest putReq = new PutObjectRequest("kai",
							key + "/" + zipEntry.getName(), new FileInputStream(temp), objMetaData);
					putReq = putReq.withCannedAcl(CannedAccessControlList.PublicRead);
					PutObjectResult result = s3.putObject(putReq);
					System.out.printf(">>> result: %s\n".formatted(result));

					// Delete the temporary file
					temp.delete();
				}
				zipEntry = is.getNextEntry();
			}
			is.closeEntry();
		}
		return key;
	}
}
