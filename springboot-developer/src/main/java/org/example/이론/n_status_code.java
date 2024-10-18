package org.example.이론;

public class n_status_code {

    // HTTP 상태 코드(Status Codes)
    // : 클라이언트의 요청에 대한 서버의 응답 상태를 나타내는 3자리 숫자

    // 1xx(정보), 2xx(성공), 3xx(리다이렉션), 4xx(클라이언트 오류)*, 5xx(서버 오류)*

    // 1xx(정보): 서버가 요청의 일부를 받고 계속 처리할 준비가 되었음을 알림

    // 2xx(성공)
    // - 200 Ok: 요청이 성공적으로 처리됨
    // - 201 Created: 요청에 의해 새로운 리소스가 생김 - POST 메서드 사용 시 나타남

    // 3xx(리다이렉션): 요청된 리소스가 다른 위치로 이동했거나 클라이언트가 리소스를 다시 요청해야 함을 나타냄

    // 4xx(클라이언트 오류)
    // - 400 Bad Request: 클라이언트의 요청이 잘못됨
    //      EX) 올바르지 않은 형식의 데이터를 서버로 전송 | 필수 매개변수 누락 등
    // - 401 Unauthorized: 인증이 필요
    //      EX) 클라이언트가 인증되지 않았거나 유효한 인증 자격 증명이 없음을 나타냄
    // - 403 Forbidden: 권한이 없어 접근할 수 없음
    //      EX) 클라이언트가 요청한 리소스에 대한 접근 원한이 존재하지 않음 >> 서버는 요청은 이해했으나 권한이 부족하여 처리를 거부
    // - 404 Not Found: 요청한 리소스를 찾을 수 없음
    //      EX) 서버가 요청된 URI 를 확인했으나, 해당하는 리소스가 존재하지 X

    // 5xx(서버 오류)
    // - 500 Internal Server Error: 서버가 요청을 처리하는 중에 오류가 발생
    // - 503 Service Unavailable: 서버가 일시적으로 과부하 또는 유지보수 중
}
