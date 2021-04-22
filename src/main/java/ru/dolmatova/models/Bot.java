package ru.dolmatova.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "bots")
public class Bot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String token;

    @OneToMany(mappedBy = "bot", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Step> steps;


    public Bot(String name, String token) {
        this.name = name;
        this.token = token;
        this.steps = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public String toString() {
        return "Bot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", token='" + token + '\'' +
                ", steps=" + steps +
                '}';
    }
}
