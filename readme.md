## 프로메테우스 메트릭 조회 유틸

본 API 는 프로메테우스를 통해 수집된 메트릭 데이터를 날짜를 기준으로 통계하여 볼 수 있도록 개발된 유틸성 API 입니다.
테스트는 개발서버 (http://sweet-api-dev.sweettracker.net:8158) 에서 할 수 있습니다
<br />


--- 

## Request Info

- Http Method : GET
- URI : /metrics
- Request Query Parameter
    - job (required): 프로메테우스에 연동한 Job 이름 (chameleon, delivery-web...)
    - targetDate (required): 조회할 날짜 (yyyyMMdd)

<br >

----

## Response Info

- httpStatus(Number): 응답 상태 코드
- message(String): 응답 상태 메시지
- data(Object): 응답 데이터
    - instanceMetrics (Array) 인스턴스별 메트릭 데이터
        - name (String) : 인스턴스 이름
        - cpu (Object) : CPU 사용률
            - avg (String) : 평균 사용률
            - max (String) : 최대 사용률
            - min (String) : 최소 사용률
        - heap (Object) : Heap 메모리 사용률
            - avg (String) : 평균 사용률
            - max (String) : 최대 사용률
            - min (String) : 최소 사용률
        - nonHeap (Object) : NonHeap 메모리 사용률
            - avg (String) : 평균 사용률
            - max (String) : 최대 사용률
            - min (String) : 최소 사용률
        - hikariConnection (Object) : DB 커넥션 수
            - avg (String) : 평균 커넥션 수
            - max (String) : 최대 커넥션 수
            - min (String) : 최소 커넥션 수
    - errorLogCnt (Number) : 에러 로그 수
    - apiCalls (Array) : API 호출 통계
        - method (String) : API Http Method
        - uri (String) : API URI
        - totalCnt (Number) : 전체 호출 수
        - successRate: 호출 성공률 (status 200 / totalCnt)
        - statusCnt (Object) : 상태 코드별 호출 수
            - status(String) : 상태 코드
            - cnt(Number) : 호출 수