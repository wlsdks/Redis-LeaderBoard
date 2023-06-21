package study.redis.LeaderBoard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.redis.LeaderBoard.service.RankingService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ApiController {

    private final RankingService rankingService;

    /**
     * userId와 score를 설정해 준다.
     */
    @GetMapping("/setScore")
    public Boolean setScore(@RequestParam String userId,
                            @RequestParam int score) {

        return rankingService.setUserScore(userId, score);
    }

    /**
     * userId를 넣어서 랭킹을 조회한다.
     */
    @GetMapping("/getRank")
    public Long getUserRank(@RequestParam String userId) {

        return rankingService.getUserRanking(userId);
    }

    /**
     * limit로 넣어준 값 만큼의 전체랭킹을 조회한다.
     */
    @GetMapping("/getTopRanks")
    public List<String> getTopRanks() {
        return rankingService.getTopRank(3);
    }
}
