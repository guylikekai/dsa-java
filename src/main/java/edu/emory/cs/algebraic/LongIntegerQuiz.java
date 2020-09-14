package edu.emory.cs.algebraic;

import java.util.Arrays;

public class LongIntegerQuiz extends LongInteger {

    public LongIntegerQuiz(LongInteger n) {
        super(n);
    }

    public LongIntegerQuiz(String n) {
        super(n);
    }

    @Override
    public void add(LongInteger n){
        super.add(n);
    }

    @Override
    protected void addDifferentSign (LongInteger n) {

        // sets reference result to larger LongInteger and stores sign of this in "a"
        byte[] result = compareAbs(n) > 0 ? digits : n.digits;
        Sign a = this.sign;

        // sets reference subtract to smaller LongInteger
        LongInteger subtract = compareAbs(n) > 0 ? n : this;

        // performs the addition
        for (int i = 0; i < subtract.digits.length; i++) {
            if (result[i] < subtract.digits[i]) {
                for (int j = i+1; j < result.length; j++) {
                    if (result[j] != 0) {
                        result[j] -= 1;
                        result[i] += 10;
                        break;
                    }
                }
            }
            result[i] -= subtract.digits[i];
        }

        // eliminates unnecessary elements (zeros)
        int lengthModification = 0;
        for (int i = result.length - 1; i >= 0; i--) {
            if (result[i] == 0) lengthModification++;
            else break;
        }
        digits = lengthModification == digits.length ? new byte[1]: Arrays.copyOf(result, result.length - lengthModification);

        // sets sign
        sign = subtract.sign == Sign.POSITIVE ? Sign.NEGATIVE : Sign.POSITIVE;

        // sets sign for edge case when the result is zero
        if (digits.length == 1 && digits[0] == 0) sign = a;

        // sets sign to positive for edge case when the result is zero
        //if (digits.length == 1 && digits[0] == 0) sign = Sign.POSITIVE;
    }
}
