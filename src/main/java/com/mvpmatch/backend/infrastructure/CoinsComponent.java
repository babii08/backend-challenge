//package com.mvpmatch.backend.infrastructure;
//
//import com.mvpmatch.backend.adapter.database.purchase.CoinsDBO;
//import com.mvpmatch.backend.adapter.database.purchase.CoinsDatabaseRepository;
//import com.mvpmatch.backend.application.domain.CoinsType;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@Slf4j
//public class CoinsComponent implements ApplicationRunner {
//
//    private CoinsDatabaseRepository coinsRepo;
//
//    @Transactional
//    @Override
//    public void run(ApplicationArguments args) {
//        try {
//            for (var coinType : CoinsType.values()) {
//                coinsRepo.save(new CoinsDBO(null, coinType, coinType.getValue()));
//            }
//        } catch (Exception exception) {
//            log.error("Exception occurred when saving coins type into the database");
//            throw exception;
//        }
//    }
//}
