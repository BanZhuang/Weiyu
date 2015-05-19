package com.syw.weiyu.util;

/**
 * author: youwei
 * date: 2015-05-19
 * desc:
 */
public class StringUtil {
    /**
     * �жϸ����ַ����Ƿ�հ״��� �հ״���ָ�ɿո��Ʊ������س��������з���ɵ��ַ��� �������ַ���Ϊnull����ַ���������true
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }
}
