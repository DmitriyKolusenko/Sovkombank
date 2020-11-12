package com.sovkombank.example.repository;

import com.sovkombank.example.entity.InvitedNumbers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.util.List;

public interface InvitedNumbersRepository extends JpaRepository<InvitedNumbers, Long> {

    List<InvitedNumbers> findInvitedNumbersByPhoneAndUserid(String phone, long userid);

    List<InvitedNumbers> findInvitedNumbersByUseridAndDate(long userid, Date date);

    @Modifying
    @Query(value = "INSERT INTO INVITED (userid, date, phone, message) values(:userid, :date, :phone, :message)", nativeQuery = true)
    void insertInvitedNumbers(@Param("userid") long userid, @Param("date") Date date, @Param("phone") String phone, @Param("message") String message);

    InvitedNumbers save(InvitedNumbers number);
}
