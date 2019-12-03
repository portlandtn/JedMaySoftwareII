/*
 * Copyright (C) 2019 Jedidiah May.
 *
 * This program is free software and part of an academic course of study
 * with Western Governor's University. This program is stored on my
 * personal git accounts so that I can collaborate from multiple computers
 * easily. If you find this, feel free to use it for general concept and
 * as a guidepost for your own coursework.
 *
 * This program is distributed in the hope that it will be useful,
 * but please do not copy any of the code verbatim without first
 * understanding how it works. If you're a student, I wish you the best
 * and hope this is of value to you. If you're not a student, I hope you
 * enjoy irregardless.
 *
 * Look for other projects on my github account at <https://github.com/portlandtn/>.
 */
package DAO;

import com.mysql.jdbc.Connection;
import javafx.collections.ObservableList;

/**
 *
 * @author Jedidiah May
 * @param <T>
 */
public abstract class DAO <T extends I_SQL_CRUD>{
    
    protected final Connection conn;
    
    public DAO(Connection conn){
        super();
        this.conn = conn;
    }
    
    public abstract ObservableList<T> query();
    public abstract void update(T dto);
    public abstract void insert(T dto);
    public abstract void remove(int id);
}
