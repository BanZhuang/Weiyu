package com.syw.weiyu.dao.user;

import android.content.Context;
import com.syw.weiyu.AppContext;
import com.syw.weiyu.AppException;
import com.syw.weiyu.bean.Account;
import com.syw.weiyu.bean.User;
import net.tsz.afinal.FinalDb;

/**
 * author: youwei
 * date: 2015-05-19
 * desc: �˻����ݵĴ�ȡ�������û���Ϣ��Token����λ����Ϣ
 */
public class AccountDao {

    /**
     * ��ȡ��ǰ�˻�
     * @return
     * @throws AppException
     */
    public Account get() throws AppException {
        Context ctx = AppContext.getCtx();
        Account account = FinalDb.create(ctx).findAll(Account.class).get(0);
        if (account != null) return account;
        else throw new AppException("�����˻�");
    }

    public void set(Account account) throws AppException {
        FinalDb.create(AppContext.getCtx()).save(account);
    }
}
