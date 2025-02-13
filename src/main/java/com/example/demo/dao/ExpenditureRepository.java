package com.example.demo.dao;

import com.example.demo.model.Expenditure;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ExpenditureRepository extends JpaRepository<Expenditure,Long> {
    void deleteByTransactionID(Long transactionID);
    Optional<Expenditure> findByTransactionID(Long transactionID);
    Page<Expenditure> findByReceiverContaining(String receiver, Pageable pageRequest);

    @Query(value = "SELECT DISTINCT receiver FROM Expenditures where receiver LIKE ?1 ORDER BY receiver ASC LIMIT 10", nativeQuery = true)
    List<String> findReceiver(String receiver);

    @Query(value = "SELECT SUM(cost) AS totalExpenditure FROM Expenditures", nativeQuery = true)
    List<Map<String,Object>> calculateSum();

    @Query(value = "SELECT SUM(cost) AS totalExpenditure FROM Expenditures where Receiver = ?1", nativeQuery = true)
    List<Map<String,Object>> calculateSumByReceiver(String receiver);

    @Query(value = "SELECT * FROM Expenditures ?1", nativeQuery = true) //this won't work
    Page<Expenditure> findExpendituresWithFilteringConditions(String filteringConditions, Pageable pageRequest);

}
