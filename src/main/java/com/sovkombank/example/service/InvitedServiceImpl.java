package com.sovkombank.example.service;

import com.sovkombank.example.constants.ErrorMessages;
import com.sovkombank.example.entity.InvitedNumbers;
import com.sovkombank.example.entity.Users;
import com.sovkombank.example.repository.InvitedNumbersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class InvitedServiceImpl implements InvitedService {

    @Autowired
    InvitedNumbersRepository invitedNumbersRepository;

    @Override
    public void invite(List<InvitedNumbers> numbers, Users user) {
        checkPhoneNumbersCount(numbers);
        checkPhoneNumbersPerDay(numbers, user);
        checkPhoneNumbersFormat(numbers);
        checkPhoneNumbersToDuplicate(numbers, user);
        checkPhoneNumbersMessageLength(numbers);
        addNumbersIntoDB(numbers, user);
    }

    private void checkPhoneNumbersFormat(List<InvitedNumbers> numbers) {
        String regex = "^7[0-9]{10}";
        Pattern pattern = Pattern.compile(regex);
        for (InvitedNumbers number: numbers) {
            Matcher matcher = pattern.matcher(number.getPhone());
            if (!matcher.matches()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessages.error_400);
            }
        }
    }

    private void checkPhoneNumbersCount(List<InvitedNumbers> numbers) {
        if (numbers.size() < 1) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ErrorMessages.error_401);
        }
        if (numbers.size() > 16) {
            throw new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, ErrorMessages.error_402);
        }
    }

    private void checkPhoneNumbersPerDay(List<InvitedNumbers> numbers, Users user) {
        Date date = new Date(System.currentTimeMillis());
        long countInvitedNumbers = invitedNumbersRepository.findInvitedNumbersByUseridAndDate(user.getUserId(), date).size();
        if (countInvitedNumbers + numbers.size() > 128)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, ErrorMessages.error_403);
    }

    private void checkPhoneNumbersToDuplicate(List<InvitedNumbers> numbers, Users user) {
        Set<String> phoneSet = new HashSet<>();
        for (InvitedNumbers number: numbers) {
            phoneSet.add(number.getPhone());
            long countNumbers = invitedNumbersRepository.findInvitedNumbersByPhoneAndUserid(number.getPhone(), user.getUserId()).size();
            if (countNumbers > 0)
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.error_404);
        }
        if (phoneSet.size() < numbers.size())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ErrorMessages.error_404);

    }

    private void checkPhoneNumbersMessageLength(List<InvitedNumbers> numbers) {
        for (InvitedNumbers number: numbers) {
            if (number.getMessage().length() < 1)
                throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, ErrorMessages.error_405);
            if (number.getMessage().length() > 128)
                throw new ResponseStatusException(HttpStatus.PROXY_AUTHENTICATION_REQUIRED, ErrorMessages.error_407);
        }
    }

    private void checkPhoneNumbersMessageEncoding(List<InvitedNumbers> numbers) throws Exception {
        /***
         * https://gist.github.com/faisalman/4674323 по данной ссылке описано, какие символы кодируются в
         * 7-bit GSM кодировке и соответствие им символов в unicode. Впринципе, не составит труда переводить
         * символы юникода в GSM кодировку. И обратно. Также можно поставить в соответствие латинского алфавита
         * и кирилицы, для автоматического преобразования символов. Далее можно написать регулярку, которая будет
         * отслеживать наличие символов кроме описанных выше в сообщении и вызывать эксепшн, если таковые будут
         * встречаться в сообщении.
         */
    }

    private void addNumbersIntoDB(List<InvitedNumbers> numbers, Users user) {
        Date date = new Date(System.currentTimeMillis());
        for (InvitedNumbers number: numbers) {
            number.setDate(date);
            number.setUserid(user.getUserId());
            invitedNumbersRepository.save(number);
        }
    }
}
