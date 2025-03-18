# SQL

- InsertUser에 중복된 아이디 있는지 확인하는 sql 추가하기
- trigger - 회원이 삭제될때 트리거 추가하기 , 업데이트될때도?
- 업데이트 프로시저 - 업데잍트 하는 항목만 디비에 업데이트 될때로 바꾸기. 지금은 전체 정보를 다시 디비에 입력함
    - 사용자가 수정하지 않은 필드는 자바에서 NULL 처리하기.그럼 sql에서 IFNULL 사용해서 업데이트 된 정보만 디비에 다시 넣을 수 있음

# Controller 
- 컨트롤러가 뷰를 업데이트 하는데, 그럼 반환값이 있어야 할까? 
- 
# 기억하기

- 레포에서는 dto 를 반환하는가 vo를 반환하는가?
    - 레포는 디비에서 데이터가져오고, 저장하는 역할
    - 레포는 데이터의 원본을 다룬다
- dto의 역할은 뭔데?
    - 컨트롤러 <-> 서비스 간의 데이터 전달을 위한 객체이다.
    - UI나 api요청에서 사용자가 입력한 데이터를 담아 전달하는 용도로 사용

Controller → Service DTO를 전달 DTO
Service → Repository DTO를 전달 DTO
Repository → DB DTO 값을 DB에 저장 
Repository → Service DB에서 데이터를 조회하여 VO 반환 VO
Service → Controller VO를 DTO로 변환하여 반환 DTO