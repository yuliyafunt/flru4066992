package com.db;

import com.entity.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.sql.SQLException;

@Singleton
public class DatabaseManager {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    private static final String DB_URL = "./sqlite.db";

    private final Dao<User, Long> usersDAO;

    public DatabaseManager() {
        try (ConnectionSource source = new JdbcConnectionSource(DB_URL)) {
            TableUtils.createTableIfNotExists(source, User.class);

            this.usersDAO = DaoManager.createDao(source, User.class);

        } catch (SQLException | IOException e) {
            throw new IllegalStateException("Can't initialize database", e);
        }
    }
    // TODO: generic injector or getter?
}
