package ibf2022.batch2.csf.backend.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ibf2022.batch2.csf.backend.models.Archives;
import ibf2022.batch2.csf.backend.repositories.ArchiveRepository;
import ibf2022.batch2.csf.backend.repositories.ImageRepository;
import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

@Controller
// @CrossOrigin(origins="*")
@RequestMapping
public class UploadController {

	@Autowired
	private ImageRepository imgRepo;

	@Autowired
	private ArchiveRepository arcRepo;

	// TODO: Task 2, Task 3, Task 4
	@PostMapping(path="/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public ResponseEntity<String> postUpload(@RequestPart String name, 
		@RequestPart String title, @RequestPart String comments, 
		@RequestPart MultipartFile archive) {
		
		System.out.printf(">> name: %s\n title: %s\n comments: %s\n", name, title, comments);
		System.out.println(">> filename: " + archive.getOriginalFilename());

		try {
			String key = this.imgRepo.upload(name, title, comments, archive);
			Archives arc = new Archives();
			arc.setBundleId(key);
			arc.setDate(LocalDateTime.now().
				format(DateTimeFormatter.ofPattern("MMM dd yyyy")));
			arc.setComments(comments);
			arc.setName(name);
			arc.setTitle(title);
			this.arcRepo.recordBundle(arc);
			JsonObject jObj = Json.createObjectBuilder()
				.add("bundleId", key)
				.build();
			
			return ResponseEntity.status(HttpStatus.CREATED)
			.contentType(MediaType.APPLICATION_JSON)
			.body(jObj.toString());
		} catch (Exception ex) {
			JsonObject jObj = Json.createObjectBuilder()
				.add("error", "Internal Server Error")
				.build();

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.contentType(MediaType.APPLICATION_JSON)
				.body(jObj.toString());
		}
		
	}

	// TODO: Task 5
	@GetMapping(path="/bundle/{bundleId}")
	@ResponseBody
	public ResponseEntity<String> getBundleByBundleId(@PathVariable String bundleId) {
		
		Archives archive = this.arcRepo.getBundleByBundleId(bundleId);

		if (archive == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.contentType(MediaType.APPLICATION_JSON)
				.body("bundleId is not found");
		}
		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(archive.toJSON().toString());
	}

	// TODO: Task 6
	@GetMapping(path="/bundles")
	@ResponseBody
	public ResponseEntity<String> getBundles() {

		List<Archives> bundles = this.arcRepo.getBundles();

		JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
		for (Archives a : bundles) {
			arrBuilder.add(a.toJSONForBundles());
		}

		return ResponseEntity.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(arrBuilder.build().toString());
	}
}
