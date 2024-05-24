module net.gameoflife {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires org.slf4j;
    requires spring.boot.autoconfigure;
    requires spring.boot;
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires org.apache.commons.lang3;

    opens net.gameoflife to javafx.fxml, com.fasterxml.jackson.databind, spring.core;
    opens net.gameoflife.servicios to spring.beans;

    exports net.gameoflife;
    exports net.gameoflife.point;
    opens net.gameoflife.point to com.fasterxml.jackson.databind, javafx.fxml;
    exports net.gameoflife.controladores;
    opens net.gameoflife.servicios.impl to spring.beans;
    opens net.gameoflife.objetos.configuracion to com.fasterxml.jackson.databind;
    exports net.gameoflife.appconfig;
    opens net.gameoflife.appconfig to com.fasterxml.jackson.databind, javafx.fxml, spring.core;
    opens net.gameoflife.controladores to com.fasterxml.jackson.databind, javafx.fxml, spring.core;
    opens net.gameoflife.eventos to spring.beans;
}