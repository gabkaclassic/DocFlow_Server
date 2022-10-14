module application {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires spring.context;
    requires spring.data.jpa;
    requires spring.data.commons;
    requires spring.tx;
    requires spring.beans;
    requires spring.boot;
    requires spring.security.core;
    requires spring.security.config;
    requires spring.security.crypto;
    requires spring.security.web;
    requires org.apache.tomcat.embed.core;
    requires spring.web;
    requires spring.core;
    requires spring.webflux;
    requires reactor.core;
    requires lombok;
    requires java.persistence;
    requires spring.boot.autoconfigure;


    opens views to javafx.fxml;
    exports gui.application;
}