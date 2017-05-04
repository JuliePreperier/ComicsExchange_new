package BDD;

import android.provider.BaseColumns;

/**
 * Created by Julie on 25.04.2017.
 */

public final class Contract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private Contract() {}

    /*Inner class that defines the table contents*/

    // inner class dataTable Authors
    public static class Authors implements BaseColumns{
        public static final String TABLE_NAME = "Authors";
        public static final String COLUMN_NAME_FIRSTNAME = "Firstname";
        public static final String COLUMN_NAME_LASTNAME = "Lastname";
    }
    // method create datatable Authors
    public static final String SQL_CREATE_AUTHORS =
            "CREATE TABLE " + Authors.TABLE_NAME + "( " +
                    Authors._ID + " INTEGER PRIMARY KEY, " +
                    Authors.COLUMN_NAME_FIRSTNAME + " TEXT, " +
                    Authors.COLUMN_NAME_LASTNAME + " TEXT" +
                    " )";

    // Method delete dataTable Authors
    public static final String SQL_DELETE_AUTHORS =
            "DROP TABLE IF EXISTS " + Authors.TABLE_NAME;


    // inner class dataTable Series
    public static class Series implements BaseColumns{
        public static final String TABLE_NAME = "Series";
        public static final String COLUMN_NAME_SERIENAME = "Name";
        public static final String COLUMN_NAME_EDITION_HOUSE = "EditionHouse";
        public static final String COLUMN_NAME_IDAUTHOR = "IdAuthor";
    }

    // method create datatable Series
    public static final String SQL_CREATE_SERIES =
            "CREATE TABLE " + Series.TABLE_NAME + "( " +
                    Series._ID + " INTEGER PRIMARY KEY, " +
                    Series.COLUMN_NAME_SERIENAME + " TEXT, " +
                    Series.COLUMN_NAME_EDITION_HOUSE + " TEXT, " +
                    Series.COLUMN_NAME_IDAUTHOR + " int NOT NULL " +
                    " )";

    // Method delete dataTable Series
    public static final String SQL_DELETE_SERIES =
            "DROP TABLE IF EXISTS " + Series.TABLE_NAME;

    // inner class dataTable Comic
    public static class Comic implements BaseColumns{
        public static final String TABLE_NAME = "Comic";
        public static final String COLUMN_NAME_NUMBER = "Number";
        public static final String COLUMN_NAME_IDAUTHOR = "IdAuthor";
        public static final String COLUMN_NAME_IDSERIE = "IdSerie";
        public static final String COLUMN_NAME_TITRE = "Titre";
        public static final String COLUMN_NAME_LANGUAGE = "Language";
        public static final String COLUMN_NAME_SYNOPSIS = "Synopsis";
        public static final String COLUMN_NAME_PHOTO = "Photo";
    }

    // Method create dataTable Comic
    public static final String SQL_CREATE_COMICS =
            "CREATE TABLE " + Comic.TABLE_NAME + " ( " +
                    Comic._ID + " INTEGER PRIMARY KEY, " + // ligne 0
                    Comic.COLUMN_NAME_IDAUTHOR + " INTEGER NOT NULL, " + // ligne 1
                    Comic.COLUMN_NAME_IDSERIE + " INTEGER NOT NULL, " + // ligne 2
                    Comic.COLUMN_NAME_TITRE + " TEXT, " + // ligne 3
                    Comic.COLUMN_NAME_LANGUAGE + " TEXT, " + // ligne 4
                    Comic.COLUMN_NAME_SYNOPSIS + " TEXT, " + // ligne 5
                    Comic.COLUMN_NAME_PHOTO + " TEXT, " + // ligne 6
                    Comic.COLUMN_NAME_NUMBER + " INTEGER " + // ligne 7
                    " )"; // PHOTO

    // Method delete dataTable Comic
    public static final String SQL_DELETE_COMIC =
            " DROP TABLE IF EXISTS "+ Comic.TABLE_NAME;

    // inner class dataTable Users
    public static class Users implements BaseColumns{
        public static final String TABLE_NAME = "Users";
        public static final String COLUMN_NAME_USERNAME = "Username";
        public static final String COLUMN_NAME_PASSWORD = "Password";
        public static final String COLUMN_NAME_EMAIL = "Email";
    }

    // Method create dataTable Users
    public static final String SQL_CREATE_USERS =
            " CREATE TABLE " + Users.TABLE_NAME + " ( " +
                    Users._ID + " INTEGER PRIMARY KEY, " +
                    Users.COLUMN_NAME_USERNAME + " TEXT, " +
                    Users.COLUMN_NAME_PASSWORD + " TEXT, " +
                    Users.COLUMN_NAME_EMAIL + " TEXT "+
                    " )";

    // Method delete dataTable Users
    public static final String SQL_DELETE_USERS =
            "DROP TABLE IF EXISTS "+ Users.TABLE_NAME;

    // inner class dataTable OwnerBooks
    public static class Ownerbooks implements BaseColumns{
        public static final String TABLE_NAME = "Ownerbooks";
        public static final String COLUMN_NAME_IDUSER = "IdUser";
        public static final String COLUMN_NAME_IDBOOK = "IdBook";
    }

    // Method create dataTable OwnerBooks
    public static final String SQL_CREATE_OWNERBOOKS =
            " CREATE TABLE " + Ownerbooks.TABLE_NAME + " ( " +
                    Ownerbooks._ID + " INTEGER PRIMARY KEY, " +
                    Ownerbooks.COLUMN_NAME_IDUSER + " INTEGER NOT NULL, " +
                    Ownerbooks.COLUMN_NAME_IDBOOK + " INTEGER NOT NULL " +
                    " )";

    public static final String SQL_DELETE_OWNERBOOKS =
            " DROP TABLE IF EXISTS " + Ownerbooks.TABLE_NAME;


}
