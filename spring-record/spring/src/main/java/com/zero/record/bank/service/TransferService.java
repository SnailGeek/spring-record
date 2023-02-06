package com.zero.record.bank.service;

public interface TransferService {
    void transfer(String fromCardNo, String toCardNo, int money) throws Exception;
}
