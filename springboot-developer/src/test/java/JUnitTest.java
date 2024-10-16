import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnitTest {
    // @DisplayName: 테스트 이름을 명시
    @DisplayName("1 + 2는 3이다")
    // @Test: 해당 어노테이션을 붙인 메서드는 테스트를 수행하는 메서드
    @Test
    public void junitTest() {
        int a = 1;
        int b = 2;
        int sum = 3;

        // .assertEquals: JUnit 에서 제공하는 검증 메서드
        // - 첫번째 인자: 기대하는 값
        // - 두번째 인자: "실제로" 검증할 값
        Assertions.assertEquals(sum, a+b);
    }

//    @DisplayName("1 + 3은 4이다")
//    @Test
//    public void junitFailedTest() {
//        int a = 1;
//        int b = 3;
//        int sum = 3;
//
//        Assertions.assertEquals(sum, a + b);
//    }

// 하나의 test 가 실패하면 전부 다 실패로 본다.
// Test Results, JUnitTest, 1+3은 4이다: 엑스표시
// 1 + 2는 3이다: 체크표시
}


