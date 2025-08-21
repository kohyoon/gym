package com.project.gym.service;

import com.project.gym.common.PageResult;
import com.project.gym.domain.Member;
import com.project.gym.domain.enums.MemberStatus;
import com.project.gym.dto.member.*;
import com.project.gym.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void registerMember(MemberCreateFormDTO form) {

        // 비밀번호 확인 일치 검사 -
        if(!form.getMemberPassword().equals(form.getConfirmPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 중복 체크
        if(memberMapper.existsByLoginId(form.getMemberLoginId())) {
            throw new IllegalStateException("이미 사용 중인 아이디입니다.");
        }
        if(memberMapper.existsByEmail(form.getEmail())) {
            throw new IllegalStateException("이미 사용 중인 이메일입니다.");
        }
        
        // DTO -> Domain 매핑
        Member member = new Member();
        member.setMemberLoginId(form.getMemberLoginId().trim());
        member.setMemberPassword(passwordEncoder.encode(form.getMemberPassword()));
        member.setMemberName(form.getMemberName().trim());
        member.setPhone(formatPhone(form.getPhone()));
        member.setEmail(form.getEmail().trim());
        member.setGender(form.getGender());
        member.setBirthDate(form.getBirthDate());

        member.setStatus(MemberStatus.NORMAL);

        memberMapper.insertMember(member);
    }

    // 전화번호 하이픈 처리
    private String formatPhone(String phone) {
        if(phone == null) return null;
        String digits = phone.replaceAll("[^0-9]", ""); // 숫자만
        if(digits.length() == 11 && digits.startsWith("010")) {
            return digits.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
        }
        return phone;
    }

    @Override
    public boolean existsByLoginId(String memberLoginId) {
        if(memberLoginId == null) return false;
        return memberMapper.existsByLoginId(memberLoginId.trim());
    }

    @Override
    public boolean existsByEmail(String email) {
        if(email == null) return false;
        return memberMapper.existsByEmail(email.trim());
    }

    @Override
    public PageResult<MemberListDTO> getMemberPage(MemberSearchCondition condition) {
        int total = memberMapper.countMembers(condition);
        List<MemberListDTO> rows = (total > 0)
                ? memberMapper.selectMemberList(condition)
                : java.util.Collections.emptyList();
        return new PageResult<>(rows, total, condition.getPage(), condition.getSize());
    }

    @Override
    public MemberDetailResponseDTO getMemberDetail(Long memberId) {
        return memberMapper.selectMemberDetail(memberId);
    }

    @Override
    public void updateMember(MemberUpdateFormDTO form) {
        Member original = memberMapper.selectMemberById(form.getMemberId());
        if(original == null) {
            throw new IllegalArgumentException("회원이 존재하지 않습니다.");
        }

        // 현재 비밀번호 일치 여부 검증 (해시 비교)
        boolean matches = passwordEncoder.matches(form.getCurrentPassword(), original.getMemberPassword());
        if (!matches) {
            throw new SecurityException("현재 비밀번호 불일치");
        }

        Member toUpdate = new Member();
        toUpdate.setMemberId(form.getMemberId());
        toUpdate.setMemberName(form.getMemberName().trim());
        toUpdate.setPhone(formatPhone(form.getPhone()));
        toUpdate.setEmail(form.getEmail().trim());
        toUpdate.setGender(form.getGender());
        toUpdate.setBirthDate(form.getBirthDate());

        memberMapper.updateMemberSelective(toUpdate);
    }

    @Override
    public boolean existsOtherUserByEmail(Long selfId, String email) {
        return memberMapper.existsOtherByEmail(selfId, email);
    }

    @Override
    public Member getById(Long memberId) {
        return memberMapper.selectMemberById(memberId);
    }

    @Override
    public void withdrawMember(Long memberId) {
        memberMapper.updateWithdrawStatus(memberId);
    }

    @Override
    public void changeMemberPassword(MemberPasswordDTO dto) {

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(dto.getNewPassword());
        dto.setNewPassword(encodedPassword);

        memberMapper.updateMemberPassword(dto);
    }

}