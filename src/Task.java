public class Task {
    // atributos
    private String description;
    private boolean done;
    private String createdAt;

    // construtor(somente String description)
    public Task(String description, String createdAt) {
        this.description = description;
        this.done = false;
        this.createdAt = createdAt;

    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // set and Gets
    public String getDescription() {
        return description + " - " + createdAt + (isDone() ? " (Concluída)" : "");
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}