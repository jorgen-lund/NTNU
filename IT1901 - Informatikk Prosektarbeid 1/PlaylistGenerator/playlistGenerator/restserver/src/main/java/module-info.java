module playlistGenerator.restserver {
    requires playlistGenerator.domain;
    requires playlistGenerator.core;

    requires com.fasterxml.jackson.databind;

    requires spring.web;
    requires spring.beans;
    requires spring.boot;
    requires spring.context;
    requires spring.boot.autoconfigure;

    requires playlistGenerator.json;

    opens playlistGenerator.restserver to spring.beans, spring.context, spring.web;
}
