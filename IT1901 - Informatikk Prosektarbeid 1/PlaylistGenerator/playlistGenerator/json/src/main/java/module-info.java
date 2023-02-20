module playlistGenerator.json {
    requires transitive playlistGenerator.domain;
    requires transitive com.fasterxml.jackson.core;
    requires transitive com.fasterxml.jackson.databind;
    exports json.deserializers;
    exports json.serializers;
    exports json.modules;
    opens json.deserializers;
    opens json.serializers;
    opens json.modules;
}
