package pl.przydan.library.model;

import java.math.BigDecimal;

public class Fine {

    private BigDecimal dailyStake;
    private long days;

    public Fine(BigDecimal dailyStake, long days) {
        this.dailyStake = dailyStake;
        this.days = days;
    }

    public BigDecimal getAmount() {
        return dailyStake.multiply(BigDecimal.valueOf(days));
    }
}
