package kg.megacom;

import kg.megacom.dao.DbHelperRep;
import kg.megacom.dao.UserRep;
import kg.megacom.dao.impl.DbHelperRepImpl;
import kg.megacom.models.User;
import kg.megacom.models.enums.UserStatus;
import kg.megacom.service.UserService;
import kg.megacom.service.impl.Operation;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Operation operation = new Operation();

        operation.firstStep();
        operation.secondStep();
    }
}