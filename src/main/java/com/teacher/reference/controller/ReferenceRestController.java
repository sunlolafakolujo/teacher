package com.teacher.reference.controller;

import com.teacher.reference.exception.ReferenceNotFoundException;
import com.teacher.reference.model.NewReference;
import com.teacher.reference.model.Referee;
import com.teacher.reference.model.ReferenceDto;
import com.teacher.reference.model.UpdateReferee;
import com.teacher.reference.service.ReferenceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/referee")
@RequiredArgsConstructor
public class ReferenceRestController {
    private final ReferenceService referenceService;

    private final ModelMapper modelMapper;

    @PostMapping("/saveReference")
    public ResponseEntity<NewReference> saveReference(@Valid @RequestBody NewReference newReference){
        Referee referee= modelMapper.map(newReference, Referee.class);

        Referee post=referenceService.saveReferee(referee);

        NewReference posted=modelMapper.map(post, NewReference.class);

        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @GetMapping("/findReferenceById/{id}")
    public ResponseEntity<ReferenceDto> getReferenceById(@PathVariable(value = "id") Long id)
                                                        throws ReferenceNotFoundException {

        Referee referee=referenceService.findRefereeById(id);

        ReferenceDto referenceDto=convertReferenceToDto(referee);

        return new ResponseEntity<>(referenceDto, HttpStatus.OK);
    }

    @GetMapping("/findAllReferences")
    public ResponseEntity<List<ReferenceDto>> getAllReferences(){
        Pageable pageable = PageRequest.of(0, 10);

        return new ResponseEntity<>(referenceService.findAllReferees(pageable)
                .stream()
                .map(this::convertReferenceToDto)
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @PutMapping("/updateReference/{id}")
    public ResponseEntity<UpdateReferee> updateReference(@RequestBody @Valid UpdateReferee updateReferee,
                                                         @PathVariable(value = "id") Long id)
                                                            throws ReferenceNotFoundException {

        Referee referee=modelMapper.map(updateReferee, Referee.class);

        Referee post=referenceService.updateReferee(referee, id);

        UpdateReferee posted=modelMapper.map(post, UpdateReferee.class);

        return new ResponseEntity<>(posted, HttpStatus.OK);
    }

    @DeleteMapping("/deleteReferenceById/{id}")
    public ResponseEntity<?> deleteReferenceById(@PathVariable(value = "id") Long id) throws ReferenceNotFoundException {

        referenceService.deleteRefereeById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAllReferences")
    public ResponseEntity<?> deleteAllReferences(){
        referenceService.deleteAllReferees();

        return ResponseEntity.noContent().build();
    }



































    private ReferenceDto convertReferenceToDto(Referee referee){

        ReferenceDto referenceDto=new ReferenceDto();

        referenceDto.setFirstName(referee.getFirstName());
        referenceDto.setLastName(referee.getLastName());
        referenceDto.setPhone(referee.getPhone());
        referenceDto.setEmail(referee.getEmail());
        referenceDto.setReferenceLetter(referee.getReferenceLetter());
        referenceDto.setAppUserFirstName(referee.getAppUser().getFirstName());
        referenceDto.setAppUserLastName(referee.getAppUser().getLastName());
        referenceDto.setAppUserEmail(referee.getAppUser().getEmail());
        referenceDto.setAppUserPhone(referee.getAppUser().getPhone());

        return referenceDto;
    }

}
