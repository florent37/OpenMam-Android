package florent37.github.com.mam.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by florentchampigny on 18/06/2017.
 */

public class App implements Serializable {

    @SerializedName("name")
    private String name;
    @SerializedName("last_version")
    private String lastVersion;
    private String image;
    @SerializedName("last_uploaded_date")
    private String dateLastUpdate;
    private String packageName;
    @SerializedName("last_code")
    private String lastCode;

    public App() {
    }

    public App(String name, String lastVersion, String image, String dateLastUpdate) {
        this.name = name;
        this.packageName = name;
        this.lastVersion = lastVersion;
        this.image = image;
        this.dateLastUpdate = dateLastUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastVersion() {
        return lastVersion;
    }

    public void setLastVersion(String lastVersion) {
        this.lastVersion = lastVersion;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDateLastUpdate() {
        return dateLastUpdate;
    }

    public void setDateLastUpdate(String dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
    }

    public String getLastCode() {
        return lastCode;
    }

    public void setLastCode(String lastCode) {
        this.lastCode = lastCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
}
