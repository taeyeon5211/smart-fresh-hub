package view;

import diconfig.DiConfig;
import outbound.controller.OutboundController;

import java.util.Scanner;

public class OutboundAdminView implements Runnable {
    DiConfig diConfig = new DiConfig();
    OutboundController outboundController = diConfig.getOutboundController();
    Scanner scanner = new Scanner(System.in);


    @Override
    public void run() {

        while (true) {
            outboundController.printAdminMenu();
            System.out.print("선택 : ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> outboundController.readAllOutboundRequest();
                case "2" -> outboundController.readOutboundRequest();
                case "3" -> outboundController.updateOutboundStatus();
                case "0" -> {
                    System.out.println("프로그램 종료");
                    return;
                }
            }
        }
    }
}
