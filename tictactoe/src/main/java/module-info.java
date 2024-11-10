module com.projects {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.projects to javafx.fxml;
    exports com.projects;
}
