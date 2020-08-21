module invoiceApp {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.web;

    requires spring.boot;
    requires spring.boot.starter.web;
    requires spring.boot.autoconfigure;
    requires spring.beans;
    requires spring.context;

    requires json;
    requires fontawesomefx;
    requires spring.security.web;
    requires spring.boot.starter.data.jpa;
    requires spring.core;
    requires spring.data.commons;
    requires spring.security.config;
    requires spring.data.jpa;
    requires spring.jdbc;
    requires net.bytebuddy;
    requires spring.web;

    requires java.sql;

    requires spring.orm;

    requires moe.tristan.easyfxml;

    requires java.base;
    requires java.se;
    requires slf4j.api;
    requires slf4j.simple;
    requires spring.tx;
    requires java.persistence;
    requires org.hibernate.orm.core;

    requires spring.webmvc;
    requires java.validation;
    requires jdk.jsobject;
    requires javafx.swing;
    requires layout;
    requires google.api.client;
//    requires com.google.api.client;
//    requires com.google.api.client.json.jackson2;
//    requires google.api.services.gmail.v1.rev110;
//    requires com.google.api.client.auth;
//    requires com.google.api.client.extensions.java6.auth;
//    requires com.google.api.client.extensions.jetty.auth;
    requires html2pdf;
    requires google.oauth.client;
    requires google.oauth.client.java6;
    requires google.oauth.client.jetty;
    requires google.http.client;
    requires google.http.client.jackson2;
    requires google.api.services.gmail.v1.rev83;
    requires jakarta.activation;
    requires java.mail;


    opens com.hubertstruminski.invoice.app;
    opens com.hubertstruminski.invoice.app.repository;
    opens com.hubertstruminski.invoice.app.controller;
    opens com.hubertstruminski.invoice.app.config;
    opens com.hubertstruminski.invoice.app.component;
    opens com.hubertstruminski.invoice.app.fx.manager;
    opens com.hubertstruminski.invoice.app.model;
    opens com.hubertstruminski.invoice.app.service;
    opens com.hubertstruminski.invoice.app.util;
    opens com.hubertstruminski.invoice.app.dto;

}