package com.br.spectrum.service.SharedLayer.Models;

import java.util.UUID;

public class Node {
    private String id;
    private Boolean shouldConvert;
    private String name;
    private Coordinate position;

    // Front-end vars
    private Double posX;
    private Double posY;

    public Node(){

    }

    public Node(Coordinate position) {
        this.id = UUID.randomUUID().toString();
        this.name = this.id;
        this.shouldConvert = true;
        this.position = position;
    }

    public Node(Boolean shouldConvert, Coordinate position) {
        this.id = UUID.randomUUID().toString();
        this.name = this.id;
        this.shouldConvert = shouldConvert;
        this.position = position;
    }

    public Node(Boolean shouldConvert, String name, Coordinate position) {
        this.shouldConvert = shouldConvert;
        this.name = name;
        this.position = position;
    }

    public Double getPosX() {
        return posX;
    }

    public void setPosX(Double posX) {
        this.posX = posX;
    }

    public Double getPosY() {
        return posY;
    }

    public void setPosY(Double posY) {
        this.posY = posY;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getShouldConvert() {
        return shouldConvert;
    }

    public void setShouldConvert(Boolean shouldConvert) {
        this.shouldConvert = shouldConvert;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        this.position = position;
    }
}
