package cn.niudehua.springbootdemo;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 类名称：Permutations
 * ***********************
 * <p>
 * 类描述：排列组合
 *
 * @author deng on 2021/2/4 10:30
 */
public class Permutations {
    public static final List<String> ruleList = new ArrayList<String>() {
        private static final long serialVersionUID = -4644128482122536523L;

        {
            add("r1");
            add("r2");
            add("r3");
            add("r4");
            add("r5");
        }
    };

    //    输入 ["r1","r2","r3","r4"] 返回 ["r1,r2","r1,r3","r1,r4","r2,r3","r2,r4","r3,r4","r1,r2,r3".....]
    public static void perm(List<String> s, int m, List<String> temp, Set<List<String>> resultList) {
        if (m == 0) {
            List<String> list = new ArrayList<>();
            for (String value : temp) {
                list.add(value + " ");
            }
            resultList.add(list);
            return;
        }

        if (!s.isEmpty()) {
            // 选择当前元素
            temp.add(s.get(0));
            perm(s.subList(1, s.size()), m - 1, temp, resultList);
            temp.remove(temp.size() - 1);
            // 不选当前元素
            perm(s.subList(1, s.size()), m, temp, resultList);
        }
    }

    public static void main(String[] args) {
        List<String> temp = new ArrayList<>();
        Set<List<String>> resultList = new LinkedHashSet<>();
        for (int i = 2; i <= ruleList.size(); i++) {
            perm(ruleList, i, temp, resultList);
        }
        System.out.println("-------------");
        System.out.println(resultList.size());
        System.out.println(resultList);
    }
}