module playlistGenerator.core {
    exports core.repository;
    exports core.services;

    requires playlistGenerator.domain;
    requires transitive playlistGenerator.json;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;

    opens core.repository;
    opens core.services;
}
