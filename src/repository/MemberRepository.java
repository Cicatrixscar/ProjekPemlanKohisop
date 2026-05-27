package repository;

import entity.Member;
import java.util.LinkedList;

public class MemberRepository {

    private LinkedList<Member> members;

    public MemberRepository() {
        this.members = new LinkedList<>();
    }

    public Member addMember(String name) {
        Member newMember = new Member(name);
        members.addLast(newMember);
        return newMember;
    }

    public Member findByCode(String code) {
        if (code == null || code.trim().isEmpty()) return null;
        for (Member member : members) {
            if (member.getMemberCode().equalsIgnoreCase(code.trim())) {
                return member;
            }
        }
        return null;
    }

    public Member findByName(String name) {
        if (name == null || name.trim().isEmpty()) return null;
        String searchName = name.trim().toLowerCase();
        for (Member member : members) {
            if (member.getName().toLowerCase().contains(searchName)) {
                return member;
            }
        }
        return null;
    }

    public boolean isValidCode(String code) {
        return findByCode(code) != null;
    }

    public int getTotalMembers() {
        return members.size();
    }

    public LinkedList<Member> getAllMembers() {
        return members;
    }

    public void displayAllMembers() {
        System.out.println("\n[ DATABASE MEMBER KOHISOP ]");
        System.out.println("-".repeat(50));
        if (members.isEmpty()) {
            System.out.println("   Belum ada member terdaftar.");
        } else {
            int no = 1;
            for (Member m : members) {
                System.out.printf("   %d. %s%n", no++, m);
            }
        }
        System.out.println("-".repeat(50));
    }
}
