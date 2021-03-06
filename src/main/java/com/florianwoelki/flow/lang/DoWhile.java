package com.florianwoelki.flow.lang;

import com.florianwoelki.flow.FlowLang;
import com.florianwoelki.flow.exception.InvalidCodeException;

/**
 * Created by Florian Woelki on 25.06.17.
 */
public class DoWhile extends ConditionalBlock {

    public DoWhile(Block superBlock, String aVal, String bVal, CompareOperation compareOp) {
        super(superBlock, aVal, bVal, compareOp);
    }

    @Override
    public void runAfterParse() throws InvalidCodeException {
        if(compareOp == CompareOperation.EQUALS) {
            int a, b;

            do {
                try {
                    a = Integer.parseInt(FlowLang.implode(aVal, this));
                    b = Integer.parseInt(FlowLang.implode(bVal, this));
                } catch(Exception e) {
                    throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers");
                }

                doBlocks();
            }
            while(a == b);
        } else if(compareOp == CompareOperation.NOTEQUALS) {
            int a, b;

            do {
                try {
                    a = Integer.parseInt(FlowLang.implode(aVal, this));
                    b = Integer.parseInt(FlowLang.implode(bVal, this));
                } catch(Exception e) {
                    throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers");
                }

                doBlocks();
            }
            while(a != b);
        } else if(compareOp == CompareOperation.GREATERTHAN) {
            int a, b;

            do {
                try {
                    a = Integer.parseInt(FlowLang.implode(aVal, this));
                    b = Integer.parseInt(FlowLang.implode(bVal, this));
                } catch(Exception e) {
                    throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers.");
                }

                doBlocks();
            }
            while(a > b);
        } else if(compareOp == CompareOperation.LESSTHAN) {
            int a, b;

            do {
                try {
                    a = Integer.parseInt(FlowLang.implode(aVal, this));
                    b = Integer.parseInt(FlowLang.implode(bVal, this));
                } catch(Exception e) {
                    throw new InvalidCodeException("Attempted to use " + compareOp.name().toLowerCase() + " on non-integers.");
                }

                doBlocks();
            }
            while(a < b);
        }
    }

    @Override
    public String toString() {
        return "DoWhile aVal=" + aVal + " bVal=" + bVal + " compareOp=" + compareOp.name();
    }

}
