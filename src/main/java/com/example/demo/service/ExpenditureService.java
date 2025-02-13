package com.example.demo.service;

import com.example.demo.dao.ExpenditureRepository;
import com.example.demo.model.Expenditure;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class ExpenditureService {
    @Resource
    private ExpenditureRepository expenditureRepository;

    public Page<Expenditure> findAllExpenditure(int pageNo, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize);
        return expenditureRepository.findAll(pageRequest);
    }

    public List<String> findReceiver(String receiver){
        return expenditureRepository.findReceiver('%'+receiver+'%');
    }

    public void saveExpenditure(Expenditure expenditure){
        expenditureRepository.save(expenditure);
    }

    public void updateOneExpenditure(Expenditure expenditure) throws SQLException {
        expenditureRepository.findByTransactionID(expenditure.getTransactionID()).orElseThrow(SQLException::new);
        expenditureRepository.save(expenditure);
    }
    public void deleteExpenditureByTransactionID(Long transactionID){
        expenditureRepository.deleteByTransactionID(transactionID);
    }

    public List<Map<String,Object>> calTotalExpenditure(){
        return expenditureRepository.calculateSum();
    }

    public List<Map<String,Object>> calTotalExpenditureByReceiver(String receiver){
        return expenditureRepository.calculateSumByReceiver(receiver);
    }

    public Page<Expenditure> findExpenditureByReceiver(String receiver,int pageNo, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize);
        return expenditureRepository.findByReceiverContaining(receiver, pageRequest);
    }

    public Page<Expenditure> findExpenditureWithFilteringConditions(String filteringConditions, int pageNo, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize);
        return expenditureRepository.findExpendituresWithFilteringConditions(filteringConditions, pageRequest);
    }

}
