package testapp.android.com.echartslearn;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        Test test = new Test();
        test.color_step(0xfc5107, 0xff6f28);
        Map<Integer, String> numMap = checkNum("abcd123和345.56jia567.23.23jian345and23or345.56");
        Iterator<Map.Entry<Integer, String>> iterable = numMap.entrySet().iterator();
        while (iterable.hasNext()) {
            Map.Entry<Integer, String> entry = iterable.next();
            System.out.println(entry.getKey() + "\t" + entry.getValue());
        }
    }


    //取字符串中的数字
    public static Map<Integer, String> checkNum(String str) {
//        String str = "abcd123和345.56jia567.23.23jian345and23or345.56";
        //先判断有没有整数，如果没有整数那就肯定就没有小数
        Pattern p = Pattern.compile("(\\d+)");
        Matcher m = p.matcher(str);
        String result = "";
        Map<Integer, String> map = new TreeMap();
        if (m.find()) {
            Pattern p2 = Pattern.compile("(\\d+\\.\\d+)");
            m = p2.matcher(str);
            //遍历小数部分
            while (m.find()) {
                result = m.group(1) == null ? "" : m.group(1);
                int i = str.indexOf(result);
                String s = str.substring(i, i + result.length());
                map.put(i, s);
                //排除小数的整数部分和另一个整数相同的情况下，寻找整数位置出现错误的可能，还有就是寻找重复的小数
                // 例子中是排除第二个345.56时第一个345.56产生干扰和寻找整数345的位置时，前面的小数345.56会干扰
            }

            //遍历整数
            Pattern p3 = Pattern.compile("(\\d+)");
            m = p3.matcher(str);
            while (m.find()) {
                result = m.group(1) == null ? "" : m.group(1);
                int i = str.indexOf(result);
                //排除jia567.23.23在第一轮过滤之后留下来的jia.23对整数23产生干扰
                if (String.valueOf(str.charAt(i - 1)).equals(".")) {
                    //将这个字符串删除
                    str = str.substring(0, i - 1) + str.substring(i + result.length());
                    continue;
                }
                String s = str.substring(i, i + result.length());
                map.put(i, s);
            }
        }
        return map;
    }


    void color_step(int startColor, int endColor) {
        int r = (endColor << 8 >>> 24 - startColor << 8 >>> 24);
        int g = endColor << 16 >>> 24 - startColor << 16 >>> 24;
        int b = endColor << 24 >>> 24 - startColor << 24 >>> 24;

        int nSteps = Math.max(Math.abs(r), Math.max(Math.abs(g), Math.abs(b)));
        if (nSteps < 1) nSteps = 1;

        // Calculate the step size for each color
        float rStep = r / (float) nSteps;
        float gStep = g / (float) nSteps;
        float bStep = b / (float) nSteps;

        // Reset the colors to the starting position
        float fr = startColor << 8 >>> 24;
        float fg = startColor << 16 >>> 24;
        float fb = startColor << 24 >>> 24;

        System.out.println("rStep:" + rStep + ";gStep:" + gStep + ":bStep:" + bStep);
        System.out.println("fr:" + fr + ";fg:" + fg + ";fb:" + fb);

        for (int i = 0; i < nSteps; i++) {
            fr += rStep;
            fg += gStep;
            fb += bStep;

//            COLORREF color = RGB((int)(fr + 0.5),(int)(fg + 0.5),(int)(fb + 0.5));
            //color 即为重建颜色
            System.out.println("十六进制输出" + Integer.toHexString((int) (fr + 0.5)) + ";" + Integer.toHexString((int) (fg + 0.5)) + ";" + Integer.toHexString((int) (fb + 0.5)));
        }
    }
}
