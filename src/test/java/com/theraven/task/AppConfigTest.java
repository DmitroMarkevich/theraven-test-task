package com.theraven.task;

import com.theraven.task.config.AppConfig;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppConfigTest {

    @Test
    void testModelMapperBeanCreation() {
        AppConfig appConfig = new AppConfig();
        ModelMapper modelMapper = appConfig.modelMapper();
        ModelMapper expectedModelMapper = new ModelMapper();
        assertEquals(expectedModelMapper.getConfiguration(), modelMapper.getConfiguration());
    }
}