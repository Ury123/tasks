package com.ury;

import com.ury.service.MainService;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MainService mainService = new MainService();
        mainService.run();
    }
}