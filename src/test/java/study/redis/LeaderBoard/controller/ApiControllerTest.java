package study.redis.LeaderBoard.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import study.redis.LeaderBoard.service.RankingService;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ApiControllerTest {

    @Autowired RankingService rankingService;

    @Test
    @DisplayName("rank함수가 빠를까 아니면 range함수가 빠를까?")
    void getRanks() {
        //의미없는 호출
        rankingService.getTopRank(1);

        // 1
        Instant before = Instant.now();
        Long userRank = rankingService.getUserRanking("user_100");
        Duration elapsed = Duration.between(before, Instant.now());

        System.out.printf("Rank(%d) - Took %d ms%n", userRank, elapsed.getNano() / 1000000);

        // 2
        before = Instant.now();
        List<String> topRankers = rankingService.getTopRank(10);
        elapsed = Duration.between(before, Instant.now());

        System.out.printf("Rank - Took %d ms%n", elapsed.getNano() / 1000000);
    }


    @Test
    @DisplayName("Redis를 활용한 데이터 추가 진행")
    void insertScore() {
        for (int i = 0; i < 1000000; i++) {
            int score = (int) (Math.random() * 1000000); // 0~999999
            String userId = "user_" + i;

            rankingService.setUserScore(userId, score);
        }
    }

    @Test
    @DisplayName("java의 arrayList를 활용한 테스트 진행")
    void inMemorySortPerformance() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            int score = (int) (Math.random() * 1000000); // 0~999999
            list.add(score);
        }

        // Instant는 특정 시점을 나타내는 값이다.
        Instant before = Instant.now();
        Collections.sort(list); //nlogn이 가장 빠른 정렬이다.
        // before와 현재값이 차이를 측정한다.
        Duration elapsed = Duration.between(before, Instant.now());
        System.out.println((elapsed.getNano() / 1000000) + " ms");
    }

}