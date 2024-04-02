package sample.cafekiosk.unit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;
import sample.cafekiosk.unit.beverage.Latte;
import sample.cafekiosk.unit.order.Order;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;

/**
 * 단위테스트
 *
 * - 작은 코드 단위를 독립적으로 검증하는 테스트 (여기서 "작은" 은 Class 혹은 Class 내 Method 를 의미하고, "독립적" 은 네트워크같은 외부에 의존하지않는 것을 의미한다.)
 *
 * JUNIT
 *      - 단위테스트를 위한 테스트 프레임워크
 * ASSERTJ
 *      - 테스트 코드 작성을 원활하게 돕는 테스트 라이브러리.
 *      - 풍부한 API, 메서드 체이닝 지원.
 *      - 주로 JUNIT 위에 얹어서 씀.
 *
 * TDD (프로덕션 코드보다 테스트 코드를 먼저 작성하여 테스트가 구현 과정을 주도하도록 하는방법론)
 *      1. RED (실패하는 테스트 작성)
 *      2. GREEN (엉망이어도 일단 구현)
 *      3. REFACTOR (성공한 코드를 리팩토링)
 *
 * TDD 의 장점
 *      - 피드백 (내가 작성한 코드에 대해 아주 빠르게 피드백을 받을 수 있다.)
 *
 * 선 기능 구현 후, 테스트 작성 (단점)
 *      - 테스트 자체의 누락 가능성
 *      - 특정 테스트 케이스만 검증할 가능성 (해피케이스)
 *      - 잘못된 구현을 다소 늦게 발견할 가능성
 *
 * 선 테스트 작성, 후 기능 구현
 *      - 복잡도가 낮은(유연하며 유지보수가 쉬운), 테스트 가능한 코드로 구현할 수 있게 한다.
 *      - 쉽게 발견하기 어려운 Edge 케이스를 놓치지 않게 해준다.
 *      - 구현에 대한 빠른 피드백을 받을 수 있다.
 *      - 과감한 리팩토링이 가능해진다.
 *
 *  DisplayName 을 섬세하게
 *      - 명사의 나열보다 문장으로 (A 이면 B 이다. A 이면 B 가 아니고 C 다)
 *      - ~테스트 로 끝나는것은 지양하기
 *      - 테스트 행위에 대한 결과까지 기술하기 (음료를 1개 추가할 수 있다(x) --> 음료 1개 추가하면 주문 목록에 담긴다(O))
 *      - 도메인 용어를 사용하여 한층 추상화된 내용을 담기 (특정 시간에 이전에 주문을 생성하면 실패한다(x) --> 영업 시작 시간 이전에는 주문을 생성할 수 없다.)
 *        (도메인 용어는 메서드 자체의 관점보다 도메인 정책 관점을 의미)
 *      - 테스트의 현상을 중점으로 기술하지 말것 (~하면 실패한다, 성공한다. 이런거 하지말자)
 *
 *  BDD 스타일로 작성하기
 *      - Behavior Driven Development.  TDD 에서 파생된 개발 방법
 *      - 함수 단위의 테스트에 집중하기보다, 시나리오에 기반한 테스트케이스(TC) 자체에 집중하여 테스트한다.
 *      - 개발자가 아닌 사람이 봐도 이해할 수 있을 정도의 추상화 수준(레벨)을 권장
 *      - Given / When / Then 패턴
 *      - Given : 시나리오 진행에 필요한 모든 준비 과정 (객체, 값, 상태 등)
 *      - When : 시나리오 행동 진행
 *      - Then : 시나리오 진행에 대한 결과 명시, 검증
 *      - 어떤 환경에서 (Given)
 *        어떤 행동을 진행했을 때 (When)
 *        어떤 상태 변화가 일어난다 (Then)
 *        --> DisplayName 에 명확하게 작성할 수 있다.
 */
class CafeKioskTest {

    @Test
    public void add_manual_test() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">>> 담긴 음료 수 : " + cafeKiosk.getBeverages().size());
        System.out.println(">>> 담긴 음료 : " + cafeKiosk.getBeverages().get(0).getName());
    }

    @DisplayName("음료 1개 추가 테스트")
    @Test
    public void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        assertThat(cafeKiosk.getBeverages()).hasSize(1);
        assertThat(cafeKiosk.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    public void addSeveralBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano, 2);

        assertThat(cafeKiosk.getBeverages().get(0)).isEqualTo(americano);
        assertThat(cafeKiosk.getBeverages().get(1)).isEqualTo(americano);
    }

    @Test
    public void addZeroBeverages() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("음료는 1잔 이상 주문하실 수 있습니다");
    }

    @Test
    public void remove() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();

        cafeKiosk.add(americano);
        assertThat(cafeKiosk.getBeverages()).hasSize(1);

        cafeKiosk.remove(americano);
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @Test
    public void clear() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();

        cafeKiosk.add(americano);
        cafeKiosk.add(latte);
        assertThat(cafeKiosk.getBeverages()).hasSize(2);

        cafeKiosk.clear();
        assertThat(cafeKiosk.getBeverages()).isEmpty();
    }

    @DisplayName("주문 목록에 담긴 상품들의 총 금액을 계산할 수 있다.")
    @Test
    public void calculateTotalPrice() {
        // given
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        Latte latte = new Latte();
        cafeKiosk.add(americano);
        cafeKiosk.add(latte);

        // when
        int totalPrice = cafeKiosk.calculateTotalPrice();

        // then
        assertThat(totalPrice).isEqualTo(8500);
    }

    @Disabled
    @Test
    @DisplayName("""
        현재시간을 기준으로 테스트가 진행되기때문에, 테스트를 진행하는시점마다 결과값이 달라질 수 있다.
        이러한 경우에는 어떻게 해결할까..
        
        --> createOrder() 안에서 실행되는 LocalDateTime 을 외부에서 주입해준다.
    """)
    public void createOrder() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder();

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    public void createOrderWithCurrentTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        Order order = cafeKiosk.createOrder(LocalDateTime.of(2024, 3, 19, 10, 0));

        assertThat(order.getBeverages()).hasSize(1);
        assertThat(order.getBeverages().get(0).getName()).isEqualTo("아메리카노");
    }

    @Test
    public void createOrderWithOutsideTime() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        Americano americano = new Americano();
        cafeKiosk.add(americano);

        assertThatThrownBy(() -> cafeKiosk.createOrder(LocalDateTime.of(2024, 3, 19, 9, 59)))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("주문 시간이 아닙니다. 관리자에게 문의하세요.");
    }

}
