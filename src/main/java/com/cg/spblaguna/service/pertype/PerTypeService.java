package com.cg.spblaguna.service.pertype;

import com.cg.spblaguna.model.PerType;
import com.cg.spblaguna.repository.IPerTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerTypeService implements IPerTypeService{
    @Autowired
    private IPerTypeRepository perTypeRepository;

    @Override
    public List<PerType> findAll() {
        return perTypeRepository.findAll();
    }

    @Override
    public Optional<PerType> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public PerType save(PerType perType) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
