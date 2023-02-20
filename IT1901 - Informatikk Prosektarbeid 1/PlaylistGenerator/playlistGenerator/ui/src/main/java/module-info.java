module playlistGenerator.ui {
    exports ui;

    requires transitive playlistGenerator.core;
    requires transitive playlistGenerator.domain;
    requires transitive playlistGenerator.json;
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.net.http;

    opens ui to javafx.graphics, javafx.fxml;
}
