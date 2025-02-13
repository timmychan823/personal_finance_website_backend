package com.example.demo.controller;

import com.example.demo.model.Expenditure;
import com.example.demo.service.ExpenditureService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.convert.PeriodUnit;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/")
public class IncomeExpenditureController {
    @Resource
    ExpenditureService expenditureService;

    @GetMapping("/getReceiver")
    @ResponseStatus(HttpStatus.OK)
    public List<String> getReceiver(@RequestParam String receiver){
        return expenditureService.findReceiver(receiver);
    }

    @GetMapping("/getAllExpenditure")
    public ResponseEntity<Page<Expenditure>> getAllExpenditure(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize){
        return ResponseEntity.ok().body(expenditureService.findAllExpenditure(pageNo,pageSize));
    }

    @Transactional
    @DeleteMapping("/deleteExpenditureByID")
    public ResponseEntity<String> deleteExpenditureByID(@RequestParam Long transactionID){
        expenditureService.deleteExpenditureByTransactionID(transactionID);
        return ResponseEntity.ok().body("Deleted transactionID: "+transactionID);
    }

    @GetMapping("/getTotalExpenditureByReceiver")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getTotalExpenditureByReceiver(@RequestParam String receiver){
        return expenditureService.calTotalExpenditureByReceiver(receiver);
    }

    @GetMapping("/getAllExpenditureByReceiver")
    @ResponseStatus(HttpStatus.OK)
    public Page<Expenditure> getAllExpenditureByReceiver(@RequestParam String receiver,@RequestParam(defaultValue = "0") int pageNo,@RequestParam(defaultValue = "10") int pageSize){
        return expenditureService.findExpenditureByReceiver(receiver,pageNo,pageSize);
    }

    @GetMapping("/getTotalExpenditure")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String,Object>> getTotalExpenditure(){
        return expenditureService.calTotalExpenditure();
    }

    @PostMapping("/saveExpenditure")
    @ResponseStatus(HttpStatus.OK)
    public String saveExpenditure(@RequestBody Expenditure expenditure){
        expenditureService.saveExpenditure(expenditure);
        log.info("Expenditure Added: "+expenditure);
        return "Upload Success! Expenditure Added: "+expenditure;
    }

    @PutMapping("/updateExpenditure")
    @ResponseStatus(HttpStatus.OK)
    public String updateExpenditure(@RequestBody Expenditure expenditure) throws SQLException{
        expenditureService.updateOneExpenditure(expenditure);
        log.info("Expenditure Updated: "+expenditure);
        return "Update Success! Expenditure Updated: "+expenditure;
    }


    @GetMapping("/findExpendituresWithFilteringConditions")
    @ResponseStatus(HttpStatus.OK)
    public Page<Expenditure> findExpendituresWithFilteringConditions(@RequestParam String filteringConditions,@RequestParam(defaultValue = "0") int pageNo,@RequestParam(defaultValue = "10") int pageSize){
        return expenditureService.findExpenditureWithFilteringConditions(filteringConditions,pageNo,pageSize);
    }

    @ExceptionHandler(SQLException.class)
    @ResponseStatus(HttpStatus.GONE)
    public String sqlExceptionHandler(){
        return "Cannot perform the database operation";
    }


}
