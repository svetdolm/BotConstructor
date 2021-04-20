package ru.dolmatova.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dolmatova.models.Bot;

@Repository
public interface BotRepository extends CrudRepository<Bot, Long> {
}
