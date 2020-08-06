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

    opens com.hubertstruminski.invoice.app;
    opens com.hubertstruminski.invoice.app.repository;
    opens com.hubertstruminski.invoice.app.controller;
    opens com.hubertstruminski.invoice.app.config;
    opens com.hubertstruminski.invoice.app.component;
    opens com.hubertstruminski.invoice.app.fx.manager;
    opens com.hubertstruminski.invoice.app.model;
    opens com.hubertstruminski.invoice.app.service;

}