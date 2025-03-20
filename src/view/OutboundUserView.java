package view;

import diconfig.DiConfig;
import outbound.controller.OutboundController;

import java.util.Scanner;

public class OutboundUserView implements Runnable {
    DiConfig diConfig = new DiConfig();
    OutboundController outboundController = diConfig.getOutboundController();
    Scanner scanner = new Scanner(System.in);


    @Override
    public  void run() {

        while (true) {
            outboundController.printMemberMenu();
            System.out.print("선택 : ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> outboundController.createOutboundRequest();
                case "2" -> outboundController.readOutboundStatus();
                case "0" -> {
                    System.out.println("프로그램 종료");
                    return;
                }
            };
        }
    }
}
