package ru.dolmatova.models;

import java.io.Serializable;
import java.util.List;

public class Bot implements Serializable {
    private int id;
    private String name;
    private String token;
    private List<Step> steps;
}
