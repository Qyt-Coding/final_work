package TestCase;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;


 

public class Test1 {
    /**
     * 阈值
     */
    public static double THRESHOLD = 0;

    /**
     * 计算两个字符串的夹角余弦值
     * 
     * @param str1
     *            字符串1
     * @param str2
     *            字符串2
     * @return 夹角余弦值
     */
    public static double getSimilarity(String str1, String str2) throws Exception {
        List<String> vector1 = participle(str1);
        List<String> vector2 = participle(str2);
        int size = vector1.size();
        int size2 = vector2.size();
        Map<String, double[]> map = new HashMap<String, double[]>();
        // 计算并集
        String index = null;
        for (int i = 0; i < size; i++) {
            index = vector1.get(i);
            if (index != null) {
                double[] c = map.get(index);
                c = new double[2];
                c[0] = 1; // vector1的语义分数
                c[1] = THRESHOLD;// vector2的语义分数
                map.put(index, c);
            }
        }

        for (int i = 0; i < size2; i++) {
            index = vector2.get(i);
            if (index != null) {
                double[] c = map.get(index);
                if (c != null && c.length == 2) {
                    c[1] = 1; // vector2中也存在，vector2的语义分数=1
                } else {
                    c = new double[2];
                    c[0] = THRESHOLD; // vector1的语义分数
                    c[1] = 1; // vector2的语义分数
                    map.put(index, c);
                }
            }
        }

        Iterator<String> it = map.keySet().iterator();
        double s1 = 0, s2 = 0, sum = 0;
        while (it.hasNext()) {
            double[] c = map.get(it.next());
            sum += c[0] * c[1];
            s1 += c[0] * c[0];
            s2 += c[1] * c[1];
        }
        return sum / Math.sqrt(s1 * s2);
    }

    /**
     * 分词
     * 
     * @param str
     *            字符串
     * @return 分次后的字符集合
     * @throws IOException
     */
    public static List<String> participle(String str) throws IOException {
        List<String> vector = new Vector<String>();// 对输入进行分词
        StringReader reader = new StringReader(str);
        IKSegmenter ik = new IKSegmenter(reader, true);// 当为true时，分词器进行最大词长切分
        Lexeme lexeme = null;
        while ((lexeme = ik.next()) != null) {
            vector.add(lexeme.getLexemeText());
        }
        return vector;
    }

    public static void main(String[] args) {
        double same = 0;
        try {
            same = Test1.getSimilarity("乌干达外交部就此事件可能对中国大使馆造成的负面影响感到抱歉。", "此事件对中国大使馆造成了一定的负面影响，乌干达外交部感到抱歉并公开道歉。");
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        System.out.println("相似度：" + same);
        
        
        System.out.println("Math.sqrt"+Math.sqrt(4));
    }
}
