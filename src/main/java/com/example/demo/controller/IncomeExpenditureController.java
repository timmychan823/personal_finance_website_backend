package com.example.demo.controller;

import com.example.demo.dto.ExpenditureDTO;
import com.example.demo.model.Expenditure;
import com.example.demo.service.ExpenditureService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
// @RequestMapping("/")
@Validated
@RequestMapping("/expenditure")
public class IncomeExpenditureController {
    @Resource
    ExpenditureService expenditureService;

    // @GetMapping("/getReceiver")
    // What is the use of this
    // @GetMapping("/search")
    // @ResponseStatus(HttpStatus.OK)
    // public List<String> getExpenditures(@RequestParam String receiver){
    // return expenditureService.findReceiver(receiver);
    // }

    // @PostMapping("/saveExpenditure")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String saveExpenditure(@Valid @RequestBody Expenditure expenditure) {
        expenditureService.saveExpenditure(expenditure);
        log.info("Expenditure Added: " + expenditure);
        return "Upload Success! Expenditure Added: " + expenditure;
    }

    // @PutMapping("/updateExpenditure")
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public String updateExpenditure(@Valid @RequestBody Expenditure expenditure) throws SQLException {
        expenditureService.updateOneExpenditure(expenditure);
        log.info("Expenditure Updated: " + expenditure);
        return "Update Success! Expenditure Updated: " + expenditure;
    }

    @Transactional
    // @DeleteMapping("/deleteExpenditureByID")
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteExpenditureByID(@RequestParam Integer transactionID) {
        expenditureService.deleteExpenditureByTransactionID(transactionID);
        return ResponseEntity.ok().body("Deleted transactionID: " + transactionID);
    }

    // @PostMapping("/testing")
    // @ResponseStatus(HttpStatus.CREATED)
    // public String testing(@Valid @RequestBody ExpenditureDTO expenditureDTO) {
    // log.info("Expenditure Added: " + expenditureDTO);
    // return "Upload Success! Expenditure Added: " + expenditureDTO;
    // }

    // @GetMapping("/getAllExpenditure")
    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Page<Expenditure>> getExpenditures(@RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "20") int pageSize) {
        return ResponseEntity.ok().body(expenditureService.findAllExpenditure(pageNo, pageSize));
    }

    // @GetMapping("/getTotalExpenditureByReceiver")
    @GetMapping("/search/total")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getTotalExpenditureByReceiver(@RequestParam String receiver) {
        return expenditureService.calTotalExpenditureByReceiver(receiver);
    }

    // @GetMapping("/getAllExpenditureByReceiver")
    // can merge with @GetMapping("/search")
    // @ResponseStatus(HttpStatus.OK)
    // public Page<Expenditure> getAllExpenditureByReceiver(@RequestParam String
    // receiver,@RequestParam(defaultValue = "0") int
    // pageNo,@RequestParam(defaultValue = "10") int pageSize){
    // return
    // expenditureService.findExpenditureByReceiver(receiver,pageNo,pageSize);
    // }

    // @GetMapping("/getTotalExpenditure")
    // can merge with @GetMapping("/search/total")
    // @ResponseStatus(HttpStatus.OK)
    // public List<Map<String,Object>> getTotalExpenditure(){
    // return expenditureService.calTotalExpenditure();
    // }

    // @GetMapping("/findExpendituresWithFilteringConditions")
    // can merge with @GetMapping("/search")
    // @ResponseStatus(HttpStatus.OK)
    // public Page<Expenditure>
    // findExpendituresWithFilteringConditions(@RequestParam String
    // filteringConditions,@RequestParam(defaultValue = "0") int
    // pageNo,@RequestParam(defaultValue = "10") int pageSize){
    // return
    // expenditureService.findExpenditureWithFilteringConditions(filteringConditions,pageNo,pageSize);
    // }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.GONE)
    public String sqlExceptionHandler(SQLException ex) {
        return "Cannot perform the database operation, " + ex.getMessage();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        StringBuilder strBuilder = new StringBuilder();
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName;
            try {
                fieldName = ((FieldError) error).getField();
            } catch (ClassCastException ex) {
                fieldName = error.getObjectName();
            }
            String message = error.getDefaultMessage();
            strBuilder.append(String.format("%s: %s%n", fieldName, message));
        });
        return new ResponseEntity<>(strBuilder.substring(0, strBuilder.length() - 1), HttpStatus.BAD_REQUEST);
    }

}
