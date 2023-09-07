package com.medical.equipment.service;

import com.medical.equipment.entity.DictionaryEntity;

import java.util.List;

public interface DictionaryService {
    void addDic(DictionaryEntity dictionary);

    void delDic(Long id);

    List<DictionaryEntity> findAll();

    void updateDic(DictionaryEntity dictionary);

    DictionaryEntity findById(Long id);

    List<DictionaryEntity> findByType(String type);
}
