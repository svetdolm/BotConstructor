package ru.dolmatova.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("token")
    private String token;

    @JsonIgnore
    @OneToMany(mappedBy = "bot", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Step> steps;

    public Bot() {
    }

    @JsonCreator
    public Bot(@JsonProperty("name") String name, @JsonProperty("token") String token) {
        this.name = name;
        this.token = token;
        this.steps = new ArrayList<>();
    }

    public long getId() {
        return id;
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
        return "{" +
                "'id':" + id +
                ", 'name':'" + name + '\'' +
                ", 'token':'" + token + '\'' +
                ", 'steps':'" + steps +
                '}';
    }
}
