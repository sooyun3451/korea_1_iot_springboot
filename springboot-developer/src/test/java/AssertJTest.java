import org.junit.jupiter.api.Test;

// gradle 에서는 기본적으로 src/test/java 폴더를 test 파일의 소스경로로 인식
// : java 폴더 내의 하위 테스트 클래스에는 static import 를 사용하여
// : Assertions 의 메서드를 직접 호출
import static org.assertj.core.api.Assertions.assertThat;

public class AssertJTest {
    @Test
    public void assertJTest() {
        String name1 = "홍길동";
        String name2 = "홍길서";
        String name3 = "홍길남";

        // 모든 변수가 null 이 아닌지 확인
        assertThat(name1).isNotNull();
        assertThat(name2).isNotNull();
        assertThat(name3).isNotNull();

        // name1과 name2가 같은지 확인
        //  assertThat(name1).isEqualTo(name2);

        // name1과 name3가 다른지 확인
        assertThat(name1).isNotEqualTo(name3);
    }
}
