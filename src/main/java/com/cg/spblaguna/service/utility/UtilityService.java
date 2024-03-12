package com.cg.spblaguna.service.utility;

import com.cg.spblaguna.model.Utilitie;
import com.cg.spblaguna.repository.IUtilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilityService implements IUtilityService {
    @Autowired
    private IUtilityRepository utilityRepository;
    @Override
    public List<Utilitie> findAll() {
        return utilityRepository.findAll();
    }

    @Override
    public Optional<Utilitie> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void save(Utilitie utilitie) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
