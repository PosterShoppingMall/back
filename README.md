# back

# Read Me (BE)

# 🚀 ThreeSixNine Poster Shopping Mall

### ⚙️ 개발 환경

- IntelliJ IDEA Community Edition
- amazon corretto open jdk 11
- gradle
- Spring Boot
- Spring Data Jpa
- Tomcat 9
- mysql 8
- Spring Security/JWT
- AWS EC2 / S3 / RDS

## 🛎️ 요구 사항 명세서

[쿠팡_실장님 프로젝트_BE](https://www.notion.so/_-_BE-db31f01b481d45abb0ed1a917b2629aa?pvs=21)

## 🖼️ ERD

[ERD](https://www.notion.so/ERD-9e8a3a82520a445f891a599f90fb7d23?pvs=21)

### ✅ 주요 기능

## 👩🏻‍💻 User

Spring Security/JWT

- 회원 가입
    - Spring Security/JWT를 이용한 회원 가입
    - 회원 가입 시 중복 이메일은 회원 가입 불가
- 로그인
    - Spring Security/JWT를 이용한 로그인
    - 탈퇴 한 회원은 탈퇴 후 로그인 불가
- 로그아웃
    - 토큰을 넣어서 해당하는 회원 로그아웃
    - email로 logout시 DB에서 token을 만료
- 회원탈퇴
    - 탈퇴 시 정보 보관을 위해 DB에는 남겨두고 토큰 삭제

## 📧  Product

- 메인페이지
    - PageRequest를 활용하여 카테고리 별 최신 상품 6개씩 묶어 Map으로 return
- 상품 등록
    - AWS S3을 이용하여 이미지 업로드 기능 활용하여 상품 이미지 등록, 상품 상세내역 등록
    - DB에 S3에 업로드 된 이미지 객체 URL 을 imagePath로 저장
- 상품 수정
    - pathvariable로 productId에 해당하는 상품의 상세페이지 조회하여 해당 상품 수정
    - S3에 상품등록 된 이미지 삭제 후 수정된 이미지를 업로드 후
        
        DB에 S3에 업로드 된 이미지 객체 URL 을 imagePath로 저장
        
    - 상품 수정 시 재고 수량 조절, 판매 상태 여부 등록 가능
- 상품 상세페이지 조회
    - pathvariable로 productId에 해당하는 상품의 상세페이지 조회
- 상품 리스트 조회
    - 테이블 조인을 통해 재고 현황을 제공하고, 판매상태가 SELL 인 제품만 리스트로 생성
    - Pageable의 size, page, Sort.by()를 활용한 상품 정렬 후 카테고리별 상품 리스트 조회
- 베스트 카테고리 리스트 조회
    - 테이블 조인을 통해 상품을 판매순으로 정렬, 재고 현황을 제공하고, 판매상태가 SELL 인 제품만 리스트로 생성
    - Pageable의 size, page 를 활용한 상품 정렬 후 베스트 상품 리스트 조회
- 상품 검색
    - @Query  LIKE %:keyword% 를 활용하여 keyword가 제품 이름에 포함된 상품 리스트 생성
    - Pageable의 size, page, Sort.by()를 활용한 상품 정렬 후 검색 상품 리스트 조회

## 🛒 Cart

- 로그인한 유저 장바구니에 주문할 상품 선택 후 담기
    - 주문할 상품을 선택 , 수량 선택 해서 장바구니에 저장
    - 장바구니에 담겨진 상품과 동일한 상품을 담을땐 수량이 추가되어 저장
- 장바구니 수량 변경
    - 장바구니에 주문할 상품을 담은 후 구매할 수량으로 변경 가능
- 장바구니 상품 삭제
    - 장바구니에 담은 상품 중 원하지 않는 상품은 장바구니에서 삭제

## 💶 Order

- 장바구니에 담긴 장바구니 목록을 선택하여 주문
    - 주문 시 유저 주소 정보를 받아와서 배송지 작성폼 생성
    - 주문 시 주문번호와 주문 상세 리스트를 생성하여 주문날짜와 주문 상품 리스트를 저장
    - 결제 완료 시 배송지 저장 및 주문 상품은 장바구니에서 삭제
    - 재고 수량 상품 구매 만큼 변경, 상품 판매 수량도 상품 구매 만큼 변경

## 🏡 MyPage

- 유저 정보 조회
    - 회원 가입한 유저의 정보를 조회
    - Token을 통하여 본인만 조회할 수 있도록 구현
- 유저 정보 수정
    - 회원 가입한 유저의 정보를 수정
    - Token을 통하여 본인만 수정할 수 있도록 구현
- 장바구니 조회
    - 장바구니에 담긴 물건들의 목록을 조회
    - Token을 통하여 본인만 수정할 수 있도록 구현
- 구매 이력 조회
    - 구매한 물건들의 목록을 조회
    - Token을 통하여 본인만 수정할 수 있도록 구현
    - Page를 통하여 가져오는 정보를 수정 가능
