package com.zero.record.bank.service.impl;


import com.zero.record.bank.dao.AccountDao;
import com.zero.record.bank.dao.impl.JdbcAccountDaoImpl;
import com.zero.record.bank.domain.Account;
import com.zero.record.bank.service.TransferService;

public class TransferServiceImpl implements TransferService {
    private AccountDao accountDao = new JdbcAccountDaoImpl();

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
        final Account from = accountDao.queryAccountByCardNo(fromCardNo);
        final Account to = accountDao.queryAccountByCardNo(toCardNo);

        from.setMoney(from.getMoney() - money);
        to.setMoney(to.getMoney() + money);

        accountDao.updateAccountByCardNo(from);
        accountDao.updateAccountByCardNo(to);
    }
}

