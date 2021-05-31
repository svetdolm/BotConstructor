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
    private long id;
    private String question;
    private String answer;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "bot_id", referencedColumnName = "id", nullable = false)
    private Bot bot;

    public Step() {
    }

    public Step(String question, String answer, Bot bot) {
        this.question = question;
        this.answer = answer;
        this.bot = bot;
    }

    public long getId() {
        return id;
    }

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

    public Bot getBot() {
        return bot;
    }

    @Override
    public String toString() {
        return "Step{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer +
                '}';
    }
}