package ru.dolmatova.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name = "steps")
public class Step implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String question;
    private String answer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bot_id", referencedColumnName = "id", nullable = false, unique = true)
    private Bot bot;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
