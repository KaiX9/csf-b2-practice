package ibf2022.batch2.csf.backend.models;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;

public class Archives {
    
    private String bundleId;
    private String date;
    private String title;
    private String name;
    private String comments;
    private List<String> urls = new ArrayList<String>();
    
    public String getBundleId() {
        return bundleId;
    }
    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
    public List<String> getUrls() {
        return urls;
    }
    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    @Override
    public String toString() {
        return "Archives [bundleId=" + bundleId + ", date=" + date + ", title=" + title + ", name=" + name
                + ", comments=" + comments + ", urls=" + urls + "]";
    }

    public JsonObject toJSON() {
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (String url : urls) {
            arrBuilder.add(url);
        }
        return Json.createObjectBuilder()
            .add("bundleId", getBundleId())
            .add("date", getDate())
            .add("title", getTitle())
            .add("name", getName())
            .add("comments", getComments())
            .add("urls", arrBuilder.build())
            .build();
    }

    public JsonObject toJSONForBundles() {
        return Json.createObjectBuilder()
            .add("bundleId", getBundleId())
            .add("date", getDate())
            .add("title", getTitle())
            .build();
    }

    public static Archives createFromDoc(Document doc) {
        Archives a = new Archives();
        a.setBundleId(doc.getString("bundleId"));
        a.setDate(doc.getString("date"));
        a.setTitle(doc.getString("title"));
        a.setName(doc.getString("name"));
        a.setComments(doc.getString("comments"));
        a.setUrls(doc.getList("urls", String.class));
        return a;
    }

}
