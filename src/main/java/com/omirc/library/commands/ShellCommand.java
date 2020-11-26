package com.omirc.library.commands;

import com.omirc.library.controller.ThymeleafController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.*;
import java.nio.charset.StandardCharsets;

import static org.springframework.util.StringUtils.isEmpty;

@ShellComponent
public class ShellCommand {

    private final Logger logger = LoggerFactory.getLogger(ShellCommand.class);


    @PersistenceContext
    private EntityManager entityManager;

    @ShellMethod("insert test data")
    @Transactional
    public void insert() throws IOException {

        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("insert.sql");
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream,
                     StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                try {
                    if (!isEmpty(line)) {
                        entityManager.createNativeQuery(line).executeUpdate();
                        logger.info("Ok. The following record was inserted:", line);
                    }
                } catch (Exception exception) {
                    logger.warn("Cannot insert the following record:", line, exception);
                }
            }
        }
    }
}


