package entity;

import java.util.Random;

public class Member {

    private String memberCode;
    private String name;
    private int points;

    private static final char[] ALLOWED_CHARS = {
        'A', 'B', 'C', 'D', 'E', 'F',
        '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };
    private static final int CODE_LENGTH = 6;
    private static final Random random = new Random();

    // Construct
    public Member(String name) {
        this.name = name;
        this.points = 0;
        this.memberCode = generateMemberCode();
    }

    public Member(String memberCode, String name, int points) {
        this.memberCode = memberCode;
        this.name = name;
        this.points = points;
    }

    private String generateMemberCode() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            sb.append(ALLOWED_CHARS[random.nextInt(ALLOWED_CHARS.length)]);
        }
        return sb.toString();
    }

    // Get
    public String getMemberCode() { return memberCode; }
    public String getName() { return name; }
    public int getPoints() { return points; }

    // Bebas pajak & poin ganda jika kode member mengandung 'A'
    public boolean isTaxFree() { return memberCode.contains("A"); }
    public boolean isDoublePoints() { return memberCode.contains("A"); }

    // Set
    public void setName(String name) { this.name = name; }

    // Poin
    public void addPoints(int pts) {
        if (pts > 0) this.points += pts;
    }

    public void deductPoints(int pts) {
        if (pts > 0 && pts <= this.points) this.points -= pts;
    }

    // 1 poin per 10 IDR; digandakan jika isDoublePoints
    public int calculateEarnedPoints(double totalIDR) {
        int basePoints = (int) (totalIDR / 10);
        return isDoublePoints() ? basePoints * 2 : basePoints;
    }

    @Override
    public String toString() {
        return String.format("Member[%s] %s - Poin: %d%s",
                memberCode, name, points, isTaxFree() ? " [BEBAS PAJAK & POIN GANDA]" : "");
    }
}
