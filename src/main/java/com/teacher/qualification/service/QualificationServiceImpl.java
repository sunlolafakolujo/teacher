package com.teacher.qualification.service;

import com.teacher.qualification.repository.QualificationRepository;
import com.teacher.qualification.exception.QualificationNotFoundException;
import com.teacher.qualification.model.Qualification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class QualificationServiceImpl implements QualificationService{

    @Autowired
    private QualificationRepository qualificationRepository;

    @Override
    public Qualification saveQualification(Qualification qualification) {

        log.info("Saving Qualification to database");

        return qualificationRepository.save(qualification);
    }

    @Override
    public Qualification findQualificationById(Long id) throws QualificationNotFoundException {
        log.info("fetching Qualification by id {}", id);

        Qualification qualification=qualificationRepository.findById(id)
                .orElseThrow(()->new QualificationNotFoundException("Qualification ID "+id+" Not found"));

        return qualification;
    }

    @Override
    public List<Qualification> findAllQualifications(Pageable pageable) {

        log.info("fetching all Qualification");

        pageable = PageRequest.of(0, 10);

        Page<Qualification> pageResult= qualificationRepository.findAll(pageable);

        return pageResult.toList();
    }

    @Override
    public void deleteQualificationById(Long id) throws QualificationNotFoundException {

        log.info("deleting Qualification by id {}", id);

        qualificationRepository.deleteById(id);

        Optional<Qualification> optionalQualification=qualificationRepository.findById(id);

        if (optionalQualification.isPresent()){
            throw new QualificationNotFoundException("Qualification ID "+id+" is not deleted");
        }
    }

    @Override
    public void deleteAllQualification() {
        log.info("deleting all Qualifications");

        qualificationRepository.deleteAll();
    }
}
