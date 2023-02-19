package com.teacher.reference.service;

import com.teacher.reference.repository.RefereeRepository;
import com.teacher.reference.exception.ReferenceNotFoundException;
import com.teacher.reference.model.Referee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RefereeServiceImpl implements ReferenceService{

    @Autowired
    private RefereeRepository refereeRepository;

    @Override
    public Referee saveReferee(Referee referee) {
        return refereeRepository.save(referee);
    }

    @Override
    public Referee findRefereeById(Long id) throws ReferenceNotFoundException {
        Referee referee=refereeRepository.findById(id)
                .orElseThrow(()->new ReferenceNotFoundException("Reference ID "+id+" Not Found"));
        return referee;
    }

    @Override
    public List<Referee> findAllReferees(Integer pageNumber) {
        Pageable pageable= PageRequest.of(pageNumber,10);
        return refereeRepository.findAll(pageable).toList();
    }

    @Override
    public Referee updateReferee(Referee referee, Long id) throws ReferenceNotFoundException {

        Referee savedReferee=refereeRepository
                .findById(id)
                .orElseThrow(()->new ReferenceNotFoundException("Reference ID "+id+" Not Found"));

        if (Objects.nonNull(referee.getFirstName()) && !"".equalsIgnoreCase(referee.getFirstName())){
            savedReferee.setFirstName(referee.getFirstName());
        }if (Objects.nonNull(referee.getLastName()) && !"".equalsIgnoreCase(referee.getLastName())){
            savedReferee.setLastName(referee.getLastName());
        }if (Objects.nonNull(referee.getPhone()) && !"".equalsIgnoreCase(referee.getPhone())){
            savedReferee.setPhone(referee.getPhone());
        }if (Objects.nonNull(referee.getEmail()) && !"".equalsIgnoreCase(referee.getEmail())){
            savedReferee.setEmail(referee.getEmail());
        }if (Objects.nonNull(referee.getReferenceLetters()) &&!"".equals(referee.getReferenceLetters())){
            savedReferee.setReferenceLetters(referee.getReferenceLetters());
        }

        return refereeRepository.save(savedReferee);
    }

    @Override
    public void deleteRefereeById(Long id) throws ReferenceNotFoundException {
        refereeRepository.deleteById(id);

        Optional<Referee> optionalReferee=refereeRepository.findById(id);

        if (optionalReferee.isPresent()){
            throw new ReferenceNotFoundException("Reference ID "+id+" is not deleted");
        }
    }

    @Override
    public void deleteAllReferees() {
        refereeRepository.deleteAll();
    }
}
