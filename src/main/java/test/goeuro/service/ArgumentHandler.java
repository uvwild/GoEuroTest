package test.goeuro.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by uv on 02/12/2015 for GoEuroTest
 */
@Service
interface ArgumentHandler extends CommandLineRunner {
    @Override
    void run(String[] args) throws IOException;
}
