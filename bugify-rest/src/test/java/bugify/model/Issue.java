package bugify.model;

public class Issue {

    private int id;
    private String summary;
    private String description;
    private String resolution;

    public int getId() {
        return id;
    }

    public String getSummary() {
        return summary;
    }

    public String getDescription() {
        return description;
    }

    public String getResolution() {
        return resolution;
    }

    public Issue withId(int id) {
        this.id = id;
        return this;
    }

    public Issue withSummary(String summary) {
        this.summary = summary;
        return this;
    }

    public Issue withDescription(String description) {
        this.description = description;
        return this;
    }

    public Issue withResolution(String resolution) {
        this.resolution = resolution;
        return this;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", description='" + description + '\'' +
                ", resolution='" + resolution + '\'' +
                '}';
    }
}
