package com.example.minimovie.service;

import com.example.minimovie.controller.MemberController;
import com.example.minimovie.dto.request.MemberRequestDto;
import com.example.minimovie.dto.response.MemberResponseDto;
import com.example.minimovie.entity.Member;
import com.example.minimovie.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 멤버 생성
    public void createMember(MemberRequestDto createDto) {

        isIdentity(createDto.getIdentity());

        Member newMember = toEntity(createDto);

        memberRepository.save(newMember);
    }

    // 로그인
    public MemberResponseDto loginMember(String memberIdentity, String memberPassword) {
        Optional<Member> member = memberRepository.findMemberByIdentity(memberIdentity);

        isMember(member);

        isPasswordMatch(member, memberPassword);

        return toDto(member.get());
    }

    // 회원 상세조회
    public MemberResponseDto readMemberDetail(String memberIdentity) {
        return toDto(memberRepository.findMemberByIdentity(memberIdentity).get());
    }

    // 모든 회원 조회
    public List<MemberResponseDto> readAllMembers() {

        List<Member> memberList = memberRepository.findAll();

        List<MemberResponseDto> dtoList = new ArrayList<>();

        for (int i = 0; i < memberList.size(); i++) {
            Member m = memberList.get(i);
            MemberResponseDto d = toDto(m);
            dtoList.add(d);
        }

        return dtoList;

//        return memberRepository.findAll().stream().map(this::toDto).toList();
    }

    // 회원 수정
    public void updateMember(MemberRequestDto updateDto) {
        Optional<Member> member = memberRepository.findMemberByIdentity(updateDto.getIdentity());

        isMember(member);

        member.get().updateMember(updateDto);

        memberRepository.save(member.get());
    }

    // 회원 삭제
    public void deleteMember(String memberIdentity) {
        Optional<Member> member = memberRepository.findMemberByIdentity(memberIdentity);

        isMember(member);

        memberRepository.delete(member.get());
    }

    // 해당 멤버 존재 체크
    private void isMember(Optional<Member> member) {
        if (member.isEmpty()) {
            throw new RuntimeException();
        }
    }

    // 회원의 identity 값 중복 체크
    private void isIdentity(String identity) {
        // id 값이 이미 db에 존재하는 값이면 true
        if (memberRepository.findMemberByIdentity(identity).isPresent()) {
            throw new RuntimeException();
        }
    }

    private void isPasswordMatch(Optional<Member> loginMember, String password) {
        if (!loginMember.get().getPassword().equals(password)) {
            throw new RuntimeException();
        }
    }

    // dto -> entity 로 변환
    private Member toEntity(MemberRequestDto dto) {
        return Member.builder()
                .identity(dto.getIdentity())
                .password(dto.getPassword())
                .nickname(dto.getNickname())
                .role(1)
                .build();
    }

    // entity -> dto 로 변환
    private MemberResponseDto toDto(Member member) {
        return MemberResponseDto.builder()
                .identity(member.getIdentity())
                .nickname(member.getNickname())
                .role(member.getRole())
                .build();
    }

}
