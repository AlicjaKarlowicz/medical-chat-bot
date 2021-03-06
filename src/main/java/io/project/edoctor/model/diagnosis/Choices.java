package io.project.edoctor.model.diagnosis;

public class Choices {

    private String id;

    private String label;

    public Choices() {
    }

    public Choices(String id, String label) {
        this.id = id;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
