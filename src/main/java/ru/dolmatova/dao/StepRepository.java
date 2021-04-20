package ru.dolmatova.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.dolmatova.models.Step;

@Repository
public interface StepRepository extends CrudRepository<Step, Long> {
}
