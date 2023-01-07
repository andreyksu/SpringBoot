package ru.annikonenkov.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.annikonenkov.enity.repast.Repast;

@Service
public class RepastService {

    @Autowired
    private ru.annikonenkov.repository.RepastRepositoy repastRepository;

    public Repast saveNew(Repast repast) {
        return repastRepository.save(repast);
    }

}
