package kg.megacom.dao;

import kg.megacom.dao.impl.DbHelperRepImpl;

import java.sql.Connection;

public interface DbHelperRep {
    DbHelperRep INSTANCE = new DbHelperRepImpl();

    Connection connect();
}
