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
    requires spring.security.oauth2;
    requires spring.boot.starter.data.jpa;
    requires spring.core;
    requires spring.data.commons;
    requires spring.security.config;
    requires spring.data.jpa;
    requires spring.jdbc;

    requires java.sql;

    requires java.persistence;
    requires spring.orm;

    opens com.hubertstruminski.invoice.app;
    opens com.hubertstruminski.invoice.app.view;
    opens com.hubertstruminski.invoice.app.repository;
    opens com.hubertstruminski.invoice.app.controller;
    opens com.hubertstruminski.invoice.app.config;
    opens com.hubertstruminski.invoice.app.service;

}