package com.example.group10b_hw03;

/**
 * Ganesh Ramamoorthy, Rakesh Balan
 */
public class CheckAnswerType {

    private String qId;
    private int answer;

    public CheckAnswerType(String qId, int answer) {
        this.qId = qId;
        this.answer = answer;
    }

    public String getqId() {
        return qId;
    }

    public void setqId(String qId) {
        this.qId = qId;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
