package view;

import area.repository.AreaRepositoryImp;
import area.service.AreaService;
import area.service.AreaServiceImp;
import inbound.controller.*;
import inbound.repository.*;
import inbound.service.*;
import warehouse.repository.WareHouseRepositoryImp;

import java.util.Scanner;

public class InboundView {

    protected void printAdminMenu() {
        System.out.println("\n=================================");
        System.out.println("  [관리자 전용 기능]");
        System.out.println("1️. 입고 요청 승인 / 취소");
        System.out.println("2️. 대기 중인 입고 요청 조회");
        System.out.println("3️. 특정 사업체의 입고 내역 조회");
        System.out.println("4️. 모든 사업체 조회");
        System.out.println("5️. 특정 사업체의 등록된 제품 목록 조회");
        System.out.println("6️. 제품 등록");
        System.out.println("7️. 재고 변경 이력 조회");
        System.out.println("0️. 종료");
        System.out.println("=================================");
    }

    /**
     * 화주 메뉴 출력
     */
    protected void printClientMenu() {
        System.out.println("\n=================================");
        System.out.println("[화주 전용 기능]");
        System.out.println("1️. 입고 요청");
        System.out.println("2️. 내 사업체의 입고 내역 조회");
        System.out.println("3️. 내 사업체의 등록된 제품 목록 조회");
        System.out.println("4️. 제품 등록");
        System.out.println("0️. 종료");
        System.out.println("=================================");
    }
}