# Smart Fresh Hub

Smart Fresh Hub는 Java 기반의 물류 및 창고 관리 애플리케이션입니다. 이 프로젝트는 입고, 출고, 창고 관리, 사용자 인증 등 다양한 기능을 제공하며, 모듈화된 구조를 통해 유지보수와 확장이 용이하도록 설계되었습니다.

## 프로젝트 구조

- **doc/**
  - `commit-convention.md`: 커밋 메시지 규칙 안내
  - `meeting-log-0311.md`: 회의 기록

- **sql/**
  - 데이터베이스 스키마, 테이블, 제약조건, 트리거, 데이터, 프로시저 등 초기화 및 관리 스크립트

- **src/**
  - **Main.java**: 애플리케이션 진입점
  - **area/**: 창고 내 공간 및 제품 사용 공간 관리를 위한 컨트롤러, 서비스, 레포지토리, DTO, VO
  - **auth/**: 사용자 인증 관련 기능
  - **diconfig/**: 의존성 주입(DI) 설정
  - **inbound/**: 입고 관리 (컨트롤러, 서비스, 레포지토리, DTO, VO 및 예외 처리)
  - **login/**: 로그인 기능 (컨트롤러, DTO, 레포지토리, 서비스)
  - **object/**: 객체 입출력 관련 유틸리티
  - **outbound/**: 출고 관리 (컨트롤러, 서비스, 레포지토리, DTO, VO 및 관련 SQL 스크립트)
  - **user/**: 관리자 및 사용자 관리 (컨트롤러, DTO, 레포지토리, 서비스, VO)
    - `Todo.md`: 향후 개선 사항 기록
  - **view/**: 콘솔 기반의 입출고 및 창고 관리 인터페이스 제공
  - **warehouse/**: 창고 관리 (컨트롤러, 서비스, 레포지토리, DTO, VO, 공통 상수)

- **기타**
  - `.gitignore`: Git 관리 제외 파일 목록
  - `pull_request_template.md`: 풀 리퀘스트(PR) 템플릿
  - `smart-fresh-hub.iml`: IntelliJ IDEA 프로젝트 파일

## 기능

- **입고 관리**: 제품 입고 기록 관리 및 예외 처리
- **출고 관리**: 제품 출고 및 관련 기록 관리
- **창고 관리**: 창고 내 공간 관리 및 제품 배치 최적화
- **사용자 인증 및 관리**: 로그인 및 관리자 기능 지원
- **의존성 주입**: DI 설정을 통한 모듈 간 결합도 최소화

## 설치 및 실행 방법

1. **프로젝트 클론**
   ```bash
   git clone https://github.com/your-username/smart-fresh-hub-dev.git
