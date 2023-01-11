package com.kspo.voc.program.common.stnd;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Slf4j
public enum CodeGeneration {

    DIRCD("DI", 10),
    PROCEDURE_BAS("PB", 10),
    MGMT_PRCD("MP", 10),
    TASK_BAS("TB", 10),
    MGMT_TASK("MT", 10),
    TASK("TK", 10),
    ACTIVITY_BAS("AB", 10),
    MGMT_ACTV("MA", 10),

    ACTIVITY("AT", 10),
    PROCEDURE_MAPPING("PM", 10),
    REGISTRATION("VM", 12),;


    private String preFix;
    private int digit;

    CodeGeneration(String preFix, int digit) {
        this.preFix = preFix;
        this.digit = digit;
    }

    public static String generateCode(String maxCd, CodeGeneration codeType){
        StringBuilder sb = new StringBuilder();

        String prefix = codeType.preFix;
        sb.append(prefix);

        int prefixDigit = prefix.length();

        if(maxCd != null){
            String numStr = maxCd.substring(prefixDigit);
            int realNum = Integer.parseInt(numStr);

            int addNum = realNum + 1;
            String addNumStr = String.valueOf(addNum);

            int addNumStrLength = addNumStr.length();

            for(int i = 0; i < codeType.digit - prefixDigit - addNumStrLength; i++){
                sb.append("0");
            }
            sb.append(addNum);

        } else {
            for(int i = 0; i < codeType.digit - prefixDigit - 1; i++){
                sb.append("0");
            }
            sb.append("1");
        }

        log.debug("last sb = {}", sb);

        return sb.toString();
    }

}
