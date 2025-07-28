package com.project.gym.scheduler;

import com.project.gym.service.MembershipService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MembershipStatusScheduler {

    private final MembershipService membershipService;

    // 매일 새벽 1시에 종료일이 지난 회원권을 자동으로 종료 처리
    @Scheduled(cron = "0 0 1 * * *") // 매일 01:00에 실행
    public void autoExpireMemberships() {
        membershipService.markExpiredMemberships();
        System.out.println("[스케줄러] 종료된 회원권 자동 종료 처리 완료");
    }

}
