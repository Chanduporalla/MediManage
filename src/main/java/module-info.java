module org.example.finall_project_1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.finall_project_1 to javafx.fxml;
    exports org.example.finall_project_1;
}