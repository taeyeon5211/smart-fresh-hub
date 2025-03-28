# Smart Fresh Hub - [Team Notion](https://www.notion.so/CHILL-STOCK-1bd501101bba803c872efed3a280a7b9)
Smart Fresh Hub는 Java 기반의 물류 및 창고 관리 애플리케이션입니다. 이 프로젝트는 입고, 출고, 창고 관리, 사용자 인증 등 다양한 기능을 제공하며, 모듈화된 구조를 통해 유지보수와 확장이 용이하도록 설계되었습니다.   

## 프로젝트 구조
  ```plaintext
smart-fresh-hub-dev/
└── smart-fresh-hub-dev/
    ├── doc/                    # 개발 문서
    ├── sql/                    # DB 스크립트
    └── src/                    # 소스 코드 (패키지 구조)
        ├── Main.java         # 애플리케이션 진입점 (default package)
        ├── area              # 창고 구역 관리 관련 모듈
        │   ├── common/       # 공통 코드 (유틸 등)
        │   ├── controller/   # Area 관련 컨트롤러 패키지
        │   ├── dto/          # Area 관련 데이터 전송 객체
        │   ├── repository/   # Area 관련 데이터 접근 객체
        │   ├── service/      # Area 관련 비즈니스 로직 처리
        │   └── vo/           # Area 관련 값 객체
        ├── auth                # 인증 관련 모듈 (단일 패키지)
        ├── diconfig            # DI 설정 모듈
        ├── inbound             # 입고 관리 관련 모듈
        │   ├── controller/   # Inbound 관련 컨트롤러
        │   ├── dto/          # Inbound 관련 데이터 전송 객체
        │   ├── repository/   # Inbound 관련 데이터 접근 객체
        │   ├── service/      # Inbound 관련 비즈니스 로직 처리
        │   └── vo/           # Inbound 관련 값 객체
        ├── login               # 로그인 관련 모듈
        │   ├── controller/   # Login 관련 컨트롤러
        │   ├── dto/          # Login 관련 데이터 전송 객체
        │   ├── repository/   # Login 관련 데이터 접근 객체
        │   └── service/      # Login 관련 서비스
        ├── outbound            # 출고 관리 관련 모듈
        │   ├── common/       # 공통 코드 (유틸 등)
        │   ├── controller/   # Outbound 관련 컨트롤러
        │   ├── dto/          # Outbound 관련 데이터 전송 객체
        │   ├── repository/   # Outbound 관련 데이터 접근 객체
        │   ├── service/      # Outbound 관련 비즈니스 로직 처리
        │   └── vo/           # Outbound 관련 값 객체
        ├── user                # 사용자 및 관리자 관련 모듈
        │   ├── controller/   # User 관련 컨트롤러
        │   ├── dto/          # User 관련 데이터 전송 객체
        │   ├── repository/   # User 관련 데이터 접근 객체
        │   ├── service/      # User 관련 비즈니스 로직 처리
        │   └── vo/           # User 관련 값 객체
        ├── view                # 뷰(View) 관련 모듈 (콘솔 기반 UI 등)
        └── warehouse           # 창고 관리 관련 모듈
            ├── common/       # 공통 코드 (유틸 등)
            ├── controller/   # Warehouse 관련 컨트롤러
            ├── dto/          # Warehouse 관련 데이터 전송 객체
            ├── repository/   # Warehouse 관련 데이터 접근 객체
            ├── service/      # Warehouse 관련 비즈니스 로직 처리
            └── vo/           # Warehouse 관련 값 객체
```

- **doc/**
  - `commit-convention.md`: 커밋 메시지 규칙 안내

- **sql/**
  - 데이터베이스 스키마, 테이블, 제약조건, 트리거, 데이터, 프로시저 등 초기화 및 관리 스크립트
  - erd : [https://www.erdcloud.com/d/4N6KNTWyntqcYu4AL](https://www.erdcloud.com/d/4N6KNTWyntqcYu4AL)

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
  - **view/**: 콘솔 기반의 입출고 및 창고 관리 인터페이스 제공
  - **warehouse/**: 창고 관리 (컨트롤러, 서비스, 레포지토리, DTO, VO, 공통 상수)

- **기타**
  - `.gitignore`: Git 관리 제외 파일 목록
  - `pull_request_template.md`: 풀 리퀘스트(PR) 템플릿
  - `smart-fresh-hub.iml`: IntelliJ IDEA 프로젝트 파일

## 기술스택

- **언어**: Java 11 이상
- **아키텍처**: MVC 패턴, 의존성 주입(DI)
- **데이터베이스**: MySQL
- **빌드 도구**: (필요 시 Maven 또는 Gradle 도입 검토)

## 기능

- **입고 관리**: 제품 입고 기록 관리 및 예외 처리
- **출고 관리**: 제품 출고 및 관련 기록 관리
- **창고 관리**: 창고 내 공간 관리 및 제품 배치 최적화
- **사용자 인증 및 관리**: 로그인 및 관리자 기능 지원
- **의존성 주입**: DI 설정을 통한 모듈 간 결합도 최소화

## 설치 및 실행 방법

- **프로젝트 클론**
   ```bash
   git clone https://github.com/your-username/smart-fresh-hub-dev.git
