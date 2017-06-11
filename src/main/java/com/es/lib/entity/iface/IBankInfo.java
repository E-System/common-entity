/*
 * Copyright (c) E-System - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by E-System team (https://ext-system.com), 2016
 */

package com.es.lib.entity.iface;

/**
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 02.08.16
 */
public interface IBankInfo {

    String getAccount();

    void setAccount(String account);

    String getBank();

    void setBank(String bank);

    String getKaccount();

    void setKaccount(String kaccount);

    String getBik();

    void setBik(String bik);

    String getBankOgrn();

    void setBankOgrn(String bankOgrn);

}
