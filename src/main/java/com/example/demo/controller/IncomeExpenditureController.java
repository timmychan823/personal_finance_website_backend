package com.example.demo.controller;

import com.example.demo.model.Expenditure;
import com.example.demo.service.ExpenditureService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<String> getReceiver(@RequestParam String receiver){
        return expenditureService.findReceiver(receiver);
    }

    @GetMapping("/getAllExpenditure")
    public ResponseEntity<Page<Expenditure>> getAllExpenditure(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize){
        return ResponseEntity.ok().body(expenditureService.findAllExpenditure(pageNo,pageSize));
    }

    @Transactional
    @ResponseBody
    @DeleteMapping("/deleteExpenditureByID")
    public ResponseEntity<String> deleteExpenditureByID(@RequestParam Long transactionID){
        expenditureService.deleteExpenditureByTransactionID(transactionID);
        return ResponseEntity.ok().body("Deleted transactionID: "+transactionID);
    }

    @GetMapping("/getTotalExpenditureByReceiver")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, Object>> getTotalExpenditureByReceiver(@RequestParam String receiver){
        return expenditureService.calTotalExpenditureByReceiver(receiver);
    }

    @GetMapping("/getAllExpenditureByReceiver")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Page<Expenditure> getAllExpenditureByReceiver(@RequestParam String receiver,@RequestParam(defaultValue = "0") int pageNo,@RequestParam(defaultValue = "10") int pageSize){
        return expenditureService.findExpenditureByReceiver(receiver,pageNo,pageSize);
    }

    @GetMapping("/getTotalExpenditure")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String,Object>> getTotalExpenditure(){
        return expenditureService.calTotalExpenditure();
    }

    @RequestMapping(value = "/saveExpenditure", method = { RequestMethod.PUT, RequestMethod.POST })
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public String saveExpenditure(@RequestBody Expenditure expenditure){
        expenditureService.saveExpenditure(expenditure);
        log.info("Expenditure Added: "+String.valueOf(expenditure));
        return "Upload Success! Expenditure Added: "+String.valueOf(expenditure);
    }

    @GetMapping("/findExpendituresWithFilteringConditions")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Page<Expenditure> findExpendituresWithFilteringConditions(@RequestParam String filteringConditions,@RequestParam(defaultValue = "0") int pageNo,@RequestParam(defaultValue = "10") int pageSize){
        return expenditureService.findExpenditureWithFilteringConditions(filteringConditions,pageNo,pageSize);
    }


}
