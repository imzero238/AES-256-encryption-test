## Data Encryption Test

### Base64

- 64개 문자 인코딩/디코딩
- 인코딩 기능이지, 데이터 보안 기능 아님 (보안에 사용하면 취약)

동작 방식
- 각 문자 ASCII로 변환 (이진)
- ASCII로 변환된 이진 숫자를 모두 결합
- 6비트 그룹으로 분할, 마지막 그룹 6비트 채우지 못하면 0으로 채우기
- 각 6비트 그룹을 10진수로 변환
- Base64 인코딩 테이블에 따라 10진수를 문자로 변환
- https://blog.logto.io/ko/all-about-base64